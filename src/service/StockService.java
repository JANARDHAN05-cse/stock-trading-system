package service;

import model.Stock;
import util.DataStore;

import java.util.Scanner;

public class StockService {

    public void addStock(Scanner sc) {
        System.out.print("Enter stock name: ");
        String stockName = sc.nextLine();
        System.out.print("Enter stock price: ");
        double price = readDouble(sc);
        Stock stock = new Stock(DataStore.stockIdCounter++, stockName, price);
        DataStore.stocks.add(stock);
        DataStore.stockMap.put(stock.stockId, stock);

        System.out.println("Stock added successfully!");
        System.out.println(stock);
    }

    public void updateStockPrice(Scanner sc) {
        System.out.print("Enter stock ID: ");
        int stockId = readInt(sc);

        Stock stock = DataStore.stockMap.get(stockId);
        if (stock == null) {
            System.out.println("Stock not found.");
            return;
        }

        System.out.print("Enter new price: ");
        double newPrice = readDouble(sc);

        stock.price = newPrice;
        System.out.println("Stock price updated successfully!");
        System.out.println(stock);
    }

    public void viewStocks() {
        if (DataStore.stocks.isEmpty()) {
            System.out.println("No stocks found.");
            return;
        }

        System.out.println("=== Stocks ===");
        for (Stock stock : DataStore.stocks) {
            System.out.println(stock);
        }
    }

    public Stock findStockById(int stockId) {
        return DataStore.stockMap.get(stockId);
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