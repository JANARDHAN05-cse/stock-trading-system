package service;

import model.Trader;
import util.DataStore;

import java.util.Scanner;

public class TraderService {
    public void addTrader(Scanner sc) {
        System.out.print("Enter trader name: ");
        String name = sc.nextLine();
        System.out.print("Enter trader email: ");
        String email = sc.nextLine();
        Trader trader = new Trader(DataStore.traderIdCounter++, name, email);
        DataStore.traders.add(trader);
        DataStore.traderMap.put(trader.traderId, trader);

        System.out.println("Trader added successfully.. !!!");
        System.out.println(trader);
    }
    public void viewTraders() {
        if (DataStore.traders.isEmpty()) {
            System.out.println("No trader found.");
            return;
        }
        System.out.println("  ---  Traders  --- ");
        for (Trader trader : DataStore.traders) {
            System.out.println(trader);
        }
    }
}