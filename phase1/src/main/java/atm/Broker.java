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
    private BankManager bm;
    StockBroker stockBroker;

    Broker(ATM atm, BankManager Bm) {
        this.atm = atm;
        this.bm = Bm;
        this.stockBroker = new StockBroker(atm);
        bm.createUser("broker", "password");

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

