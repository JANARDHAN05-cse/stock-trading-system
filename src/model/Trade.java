package model;

public class Trade {
    public int tradeId;
    public int buyOrderId;
    public int sellOrderId;
    public int stockId;
    public int quantity;
    public double tradePrice;
    public long tradedAt;

    public Trade(int tradeId, int buyOrderId, int sellOrderId, int stockId,
                 int quantity, double tradePrice, long tradedAt) {
        this.tradeId = tradeId;
        this.buyOrderId = buyOrderId;
        this.sellOrderId = sellOrderId;
        this.stockId = stockId;
        this.quantity = quantity;
        this.tradePrice = tradePrice;
        this.tradedAt = tradedAt;
    }

    @Override
    public String toString() {
        return "Trade ID: " + tradeId +
                ", BuyOrder: " + buyOrderId +
                ", SellOrder: " + sellOrderId +
                ", Stock ID: " + stockId +
                ", Qty: " + quantity +
                ", Price: " + tradePrice;
    }
}