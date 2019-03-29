package atm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import account.Account;
import bankmanager.*;


public class Broker {
    private final ATM atm = new ATM();
    private final BankManager bm = new BankManager(atm);
    private  StockBroker stockBroker;

    public Broker(ATM Atm, BankManager Bm) {
        bm.createUser("broker", "password");
        this.stockBroker = new StockBroker(atm);
    }


    void calculatePercentageIncreaseStocks() {

    }

    void refillFunds() {

    }

    void calculateBrokerFree() {

    }

    void sellMutualFunds() {

    }

    void buyMutualFunds() {

    }

    void calculatePercentageIncreaseMF() {

    }

    void updateMutualFunds() {

    }


}

