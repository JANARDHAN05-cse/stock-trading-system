package model;

public class Portfolio {
    public int portfolioId;
    public int traderId;
    public int stockId;
    public int quantityHeld;
    public double avgBuyPrice;

    public Portfolio(int portfolioId, int traderId, int stockId, int quantityHeld, double avgBuyPrice) {
        this.portfolioId = portfolioId;
        this.traderId = traderId;
        this.stockId = stockId;
        this.quantityHeld = quantityHeld;
        this.avgBuyPrice = avgBuyPrice;
    }

    @Override
    public String toString() {
        return "Portfolio ID: " + portfolioId +
                ", Trader ID: " + traderId +
                ", Stock ID: " + stockId +
                ", Qty Held: " + quantityHeld +
                ", Avg Buy Price: " + avgBuyPrice;
    }
}