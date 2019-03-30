package atm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import account.Account;
import bankmanager.*;


public class Broker {
    private ATM atm;
    public StockBroker stockBroker;
    public MutualFundsBroker mutualFundsBroker;

    public Broker(ATM atm, BankManager bm) {
        this.atm = atm;
        this.stockBroker = new StockBroker(atm);
        this.mutualFundsBroker = new MutualFundsBroker(atm);
        bm.createUser("broker", "password");

    }

    public StockBroker getStockBroker() {
        return stockBroker;
    }

    public MutualFundsBroker getMutualFundsBroker() {
        return mutualFundsBroker;
    }
}

