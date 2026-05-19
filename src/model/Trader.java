package model;

public class Trader {
    public int traderId;
    public String name;
    public String email;


    public Trader(int traderId, String name, String email) {
        this.traderId = traderId;
        this.name = name;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Trader ID: " + traderId + ", Name: " + name + ", Email: " + email;
    }
}