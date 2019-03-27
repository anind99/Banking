package atm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


public class Broker {
    private final StockBroker stockBroker = new StockBroker();
    private final MutualFundsBroker mutualFundsBroker = new MutualFundsBroker();

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

