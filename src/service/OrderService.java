package service;

import model.Order;
import model.Stock;
import model.Trader;
import util.DataStore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class OrderService {
    private final PortfolioService portfolioService;
    private final TradeService tradeService = new TradeService();

    public OrderService(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    public void placeOrder(Scanner sc) {
        System.out.print("Enter trader ID: ");
        int traderId = readInt(sc);

        Trader trader = DataStore.traderMap.get(traderId);
        if (trader == null) {
            System.out.println("Trader not found.");
            return;
        }

        System.out.print("Enter stock ID: ");
        int stockId = readInt(sc);

        Stock stock = DataStore.stockMap.get(stockId);
        if (stock == null) {
            System.out.println("Stock not found.");
            return;
        }

        System.out.print("Enter order type (BUY/SELL): ");
        String type = sc.nextLine().trim().toUpperCase();

        System.out.print("Enter order mode (LIMIT/MARKET): ");
        String mode = sc.nextLine().trim().toUpperCase();

        System.out.print("Enter quantity: ");
        int quantity = readInt(sc);

        double price = 0.0;
        if (mode.equals("LIMIT")) {
            System.out.print("Enter price: ");
            price = readDouble(sc);
        }

        if (!type.equals("BUY") && !type.equals("SELL")) {
            System.out.println("Invalid order type.");
            return;
        }

        if (!mode.equals("LIMIT") && !mode.equals("MARKET")) {
            System.out.println("Invalid order mode.");
            return;
        }

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        if (type.equals("SELL")) {
            int available = portfolioService.getAvailableHolding(traderId, stockId);
            if (available < quantity) {
                System.out.println("Sell order rejected. Not enough available holdings.");
                return;
            }
        }

        Order order = new Order(
                DataStore.orderIdCounter++,
                traderId,
                stockId,
                type,
                mode,
                quantity,
                price,
                System.currentTimeMillis()
        );

        DataStore.orders.add(order);
        System.out.println("Order placed successfully!");
        matchOrders(order);
        System.out.println(order);
    }

    private void matchOrders(Order newOrder) {
        List<Order> candidates = new ArrayList<>();

        for (Order order : DataStore.orders) {
            if (order.orderId != newOrder.orderId &&
                    order.stockId == newOrder.stockId &&
                    !order.type.equals(newOrder.type) &&
                    order.remainingQuantity > 0 &&
                    (order.status.equals("OPEN") || order.status.equals("PARTIAL"))) {
                candidates.add(order);
            }
        }

        if (newOrder.type.equals("BUY")) {
            candidates.sort(Comparator
                    .comparingDouble((Order o) -> o.orderMode.equals("MARKET") ? -1 : o.price)
                    .thenComparingLong(o -> o.createdAt));
        } else {
            candidates.sort(Comparator
                    .comparingDouble((Order o) -> o.orderMode.equals("MARKET") ? Double.MAX_VALUE : o.price)
                    .reversed()
                    .thenComparingLong(o -> o.createdAt));
        }

        for (Order opposite : candidates) {
            if (newOrder.remainingQuantity <= 0) break;
            if (opposite.remainingQuantity <= 0) continue;

            if (!canTrade(newOrder, opposite)) continue;

            int tradedQty = Math.min(newOrder.remainingQuantity, opposite.remainingQuantity);
            double tradePrice = getTradePrice(newOrder, opposite);

            if (newOrder.type.equals("BUY")) {
                portfolioService.addHolding(newOrder.traderId, newOrder.stockId, tradedQty, tradePrice);
                boolean ok = portfolioService.reduceHolding(opposite.traderId, opposite.stockId, tradedQty);
                if (!ok) continue;

                tradeService.recordTrade(newOrder.orderId, opposite.orderId, newOrder.stockId, tradedQty, tradePrice);
            } else {
                portfolioService.addHolding(opposite.traderId, opposite.stockId, tradedQty, tradePrice);
                boolean ok = portfolioService.reduceHolding(newOrder.traderId, newOrder.stockId, tradedQty);
                if (!ok) continue;

                tradeService.recordTrade(opposite.orderId, newOrder.orderId, newOrder.stockId, tradedQty, tradePrice);
            }

            newOrder.remainingQuantity -= tradedQty;
            opposite.remainingQuantity -= tradedQty;

            if (opposite.remainingQuantity == 0) opposite.status = "COMPLETED";
            else opposite.status = "PARTIAL";

            if (newOrder.remainingQuantity == 0) newOrder.status = "COMPLETED";
            else newOrder.status = "PARTIAL";

            DataStore.stockMap.get(newOrder.stockId).price = tradePrice;
        }

        if (newOrder.remainingQuantity > 0 && newOrder.status.equals("OPEN")) {
            newOrder.status = "PARTIAL";
        }
    }

    private boolean canTrade(Order a, Order b) {
        if (a.type.equals("BUY")) {
            if (a.orderMode.equals("MARKET") || b.orderMode.equals("MARKET")) return true;
            return a.price >= b.price;
        } else {
            if (a.orderMode.equals("MARKET") || b.orderMode.equals("MARKET")) return true;
            return b.price >= a.price;
        }
    }

    private double getTradePrice(Order newOrder, Order opposite) {
        Stock stock = DataStore.stockMap.get(newOrder.stockId);

        if (newOrder.orderMode.equals("MARKET") && opposite.orderMode.equals("MARKET")) {
            return stock.price;
        }

        if (opposite.orderMode.equals("LIMIT")) {
            return opposite.price;
        }

        return newOrder.price;
    }

    public void viewOrders() {
        if (DataStore.orders.isEmpty()) {
            System.out.println("No orders found.");
            return;
        }

        System.out.println("=== Orders ===");
        for (Order order : DataStore.orders) {
            System.out.println(order);
        }
    }

    public void viewTrades() {
        tradeService.viewTrades();
    }

    private int readInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            sc.next();
        }
        int value = sc.nextInt();
        sc.nextLine();
        return value;
    }

    private double readDouble(Scanner sc) {
        while (!sc.hasNextDouble()) {
            System.out.print("Enter a valid number: ");
            sc.next();
        }
        double value = sc.nextDouble();
        sc.nextLine();
        return value;
    }
}