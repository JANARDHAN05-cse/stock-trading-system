package service;

import model.Order;
import model.Portfolio;
import util.DataStore;

public class PortfolioService {
    public int getHolding(int traderId, int stockId) {
        String key = DataStore.portfolioKey(traderId, stockId);
        Portfolio portfolio = DataStore.portfolioMap.get(key);
        return (portfolio == null) ? 0 : portfolio.quantityHeld;
    }
    public int getAvailableHolding(int traderId, int stockId) {
        int holding = getHolding(traderId, stockId);
        int reservedSellQty = 0;
        for (Order order : DataStore.orders) {
            if (order.traderId == traderId &&
                    order.stockId == stockId &&
                    order.type.equals("SELL") &&
                    !order.status.equals("COMPLETED") &&
                    !order.status.equals("REJECTED")) {
                reservedSellQty += order.remainingQuantity;
            }
        }
        return holding - reservedSellQty;
    }

    public void addHolding(int traderId, int stockId, int quantity, double buyPrice) {
        String key = DataStore.portfolioKey(traderId, stockId);
        Portfolio portfolio = DataStore.portfolioMap.get(key);

        if (portfolio == null) {
            portfolio = new Portfolio(DataStore.portfolioIdCounter++, traderId, stockId, quantity, buyPrice);
            DataStore.portfolioMap.put(key, portfolio);
            DataStore.portfolios.add(portfolio);
        } else {
            int oldQty = portfolio.quantityHeld;
            double oldValue = portfolio.avgBuyPrice * oldQty;
            double newValue = buyPrice * quantity;

            portfolio.quantityHeld = oldQty + quantity;
            portfolio.avgBuyPrice = (oldValue + newValue) / portfolio.quantityHeld;
        }
    }

    public boolean reduceHolding(int traderId, int stockId, int quantity) {
        String key = DataStore.portfolioKey(traderId, stockId);
        Portfolio portfolio = DataStore.portfolioMap.get(key);

        if (portfolio == null || portfolio.quantityHeld < quantity) {
            return false;
        }

        portfolio.quantityHeld -= quantity;

        if (portfolio.quantityHeld == 0) {
            DataStore.portfolioMap.remove(key);
            DataStore.portfolios.remove(portfolio);
        }
        return true;
    }

    public void viewPortfolios() {
        if (DataStore.portfolios.isEmpty()) {
            System.out.println("No portfolio records found.");
            return;
        }

        System.out.println(" --- Portfolios --- ");
        for (Portfolio p : DataStore.portfolios) {
            System.out.println(p);
        }
    }
}