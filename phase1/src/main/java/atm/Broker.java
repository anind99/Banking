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
    private final StockBroker stockBroker = new StockBroker(atm);

    public Broker() {
        bm.createUser("broker", "password");
    }

    void buyStocks(String symbol, int shares, Account account, InvestmentPortfolio investmentPortfolio) {
        stockBroker.buyStocks(symbol, shares, account, investmentPortfolio);
    }

    void sellStocks() {

    }

    void updateStocks() {

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

