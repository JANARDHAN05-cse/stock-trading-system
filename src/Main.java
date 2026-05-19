/**
 * @author JANARDHAN
 **/


import service.OrderService;
import service.PortfolioService;
import service.StockService;
import service.TraderService;
import util.Menu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        TraderService traderService = new TraderService();
        StockService stockService = new StockService();
        PortfolioService portfolioService = new PortfolioService();
        OrderService orderService = new OrderService(portfolioService);
        portfolioService.addHolding(2, 1, 50, 100);

        while (true) {
            Menu.showMenu();
            System.out.print("Enter choice: ");
            int choice = readInt(sc);

            switch (choice) {
                case 1 -> traderService.addTrader(sc);
                case 2 -> stockService.addStock(sc);
                case 3 -> stockService.updateStockPrice(sc);
                case 4 -> orderService.placeOrder(sc);
                case 5 -> traderService.viewTraders();
                case 6 -> stockService.viewStocks();
                case 7 -> orderService.viewOrders();
                case 8 -> orderService.viewTrades();
                case 9 -> portfolioService.viewPortfolios();
                case 0 -> {
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
            System.out.println();
        }
    }

    private static int readInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            sc.next();
        }
        int value = sc.nextInt();
        sc.nextLine();
        return value;
    }
}