package model;

public class Order {
    public int orderId;
    public int traderId;
    public int stockId;
    public String type;
    public String orderMode;
    public int quantity;
    public int remainingQuantity;
    public double price;
    public String status;
    public long createdAt;

    public Order(int orderId, int traderId, int stockId, String type, String orderMode,
                 int quantity, double price, long createdAt) {
        this.orderId = orderId;
        this.traderId = traderId;
        this.stockId = stockId;
        this.type = type;
        this.orderMode = orderMode;
        this.quantity = quantity;
        this.remainingQuantity = quantity;
        this.price = price;
        this.status = "OPEN";
        this.createdAt = createdAt;
    }
    @Override
    public String toString() {
        return "Order ID: " + orderId +
                ", Trader ID: " + traderId +
                ", Stock ID: " + stockId +
                ", Type: " + type +
                ", Mode: " + orderMode +
                ", Qty: " + quantity +
                ", Remaining: " + remainingQuantity +
                ", Price: " + price +
                ", Status: " + status;
    }
}