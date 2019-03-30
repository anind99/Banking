package interfaces;

import account.*;
import bankmanager.*;
import atm.*;

import java.util.Scanner;

public class BrokerInterface {
    private final ATM atm;

    public BrokerInterface(ATM atm) {
        this.atm = atm;
    }

    private Scanner scanner = new Scanner(System.in);

    void displayBrokerMenu(){
        System.out.println("Select an option:");
        System.out.println("1. Buy Funds");
        System.out.println("3. Log Out");
        String option = scanner.next();
        switch (option){
            case "1": {
                buyFunds();
            }
            case "2": {
                buyFunds();
            }
            case "3":{
                sellFunds();
            }
            case "4": {
                break;
            }
            default: {
                System.out.println("There is no option \"" + option + "\". Please try again.");
            }
        }
        scanner.close();
    }

    private void buyFunds() {
        MutualFund fundToBuy = listFunds();

        System.out.println("Enter the stock symbol: ");
        String symbol = scanner.next();
        Stock stock = atm.getBroker().getStockBroker().fetchStock(symbol);

        System.out.println("Enter the amount of shares: ");
        String shares = scanner.next();

        atm.getBroker().getMutualFundsBroker().buyStocksFund(fundToBuy, stock, Integer.valueOf(shares));
    }

    private void sellFunds() {
        MutualFund fundToSell = listFunds();

        System.out.println("Enter the stock symbol: ");
        String symbol = scanner.next();
        Stock stock = atm.getBroker().getStockBroker().fetchStock(symbol);

        System.out.println("Enter the amount of shares: ");
        String shares = scanner.next();

        atm.getBroker().getMutualFundsBroker().sellStocksFund(fundToSell, stock, Integer.valueOf(shares));
    }

    private MutualFund listFunds() {
        System.out.println("Select the type of fund:");
        System.out.println("1. Low Risk Fund");
        System.out.println("2. Medium Risk Fund");
        System.out.println("3. High Risk Fund");
        System.out.println("4. Enter the number: ");

        String option = scanner.next();
        boolean validSelection = false;

        while (!validSelection) {
            switch (option) {
                case "1":
                    return atm.getBroker().getMutualFundsBroker().getLowRiskFund();
                case "2":
                    return atm.getBroker().getMutualFundsBroker().getMediumRiskFund();
                case "3":
                    return atm.getBroker().getMutualFundsBroker().getHighRiskFund();
                default:
                    System.out.println("There is no option " + option + ". Pick a number from 1 to 3.");
                    break;
            }
        }

        return null;
    }

}
