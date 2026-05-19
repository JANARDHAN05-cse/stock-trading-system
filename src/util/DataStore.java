package util;

/**
 * @author JANARDHAN
 **/

import model.Order;
import model.Portfolio;
import model.Stock;
import model.Trade;
import model.Trader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStore {
    public static final List<Trader> traders = new ArrayList<>();
    public static final List<Stock> stocks = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();
    public static final List<Trade> trades = new ArrayList<>();
    public static final List<Portfolio> portfolios = new ArrayList<>();

    public static final Map<Integer, Trader> traderMap = new HashMap<>();
    public static final Map<Integer, Stock> stockMap = new HashMap<>();
    public static final Map<String, Portfolio> portfolioMap = new HashMap<>();

    public static int traderIdCounter = 1;
    public static int stockIdCounter = 1;
    public static int orderIdCounter = 1;
    public static int tradeIdCounter = 1;
    public static int portfolioIdCounter = 1;

    public static String portfolioKey(int traderId, int stockId) {
        return traderId + "_" + stockId;
    }
}