package model;

public class Stock {
    public int stockId;
    public String stockName;
    public double price;

    public Stock(int stockId, String stockName, double price) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Stock ID: " + stockId + ", Name: " + stockName + ", Price: " + price;
    }
}