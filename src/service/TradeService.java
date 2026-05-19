package service;

import model.Trade;
import util.DataStore;

public class TradeService {

    public void recordTrade(int buyOrderId, int sellOrderId, int stockId, int quantity, double tradePrice) {
        Trade trade = new Trade(
                DataStore.tradeIdCounter++,
                buyOrderId,
                sellOrderId,
                stockId,
                quantity,
                tradePrice,
                System.currentTimeMillis()
        );
        DataStore.trades.add(trade);
    }

    public void viewTrades() {
        if (DataStore.trades.isEmpty()) {
            System.out.println("No trades found.");
            return;
        }

        System.out.println("  --- Trades ---   ");
        for (Trade trade : DataStore.trades) {
            System.out.println(trade);
        }
    }
}