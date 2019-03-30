package atm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import account.Account;
import bankmanager.*;


public class Broker {
    private ATM atm = new ATM();
    private BankManager bm = new BankManager(atm);
    StockBroker stockBroker;

    Broker(ATM Atm, BankManager Bm) {
        bm.createUser("broker", "password");
        this.atm = Atm;
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

