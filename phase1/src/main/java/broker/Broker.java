package broker;

import atm.*;
import bankmanager.*;
import account.*;

/***
 * Broker is a class that creates a new Broker for the ATM. A broker will be able to trade stocks and mutual funds
 * and will be able to buy and sell into the mutual funds.
 *
 */
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

    public boolean checkIfStockIsValid(String symbol){

    }
}

