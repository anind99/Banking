package atm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import bankmanager.*;


public class Broker {
    private final ATM atm = new ATM();
    private final UserManager um = new UserManager(atm);
    private final StockBroker stockBroker = new StockBroker();

    public Broker() {
        UserManager um = new UserManager(atm);
        um.createUser("broker", "password");
    }

    void buyStocks(User user, String symbol, int shares) {
        stockBroker.buyStocks(user, symbol, shares);
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

