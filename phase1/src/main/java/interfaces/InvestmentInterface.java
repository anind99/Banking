package interfaces;

import atm.*;
import account.*;
import java.util.*;

public class InvestmentInterface {
    private final ATM atm;
    private Scanner scanner = new Scanner(System.in);

    public InvestmentInterface(ATM atm) {
        this.atm = atm;
    }

    public void displayInvestmentMenu(User user) {
        boolean validselection = false;
        boolean goBack = false;
        printOptions();
        String option = scanner.next();

        while(!goBack) {
            switch (option) {
                case "1":
                    buyStocks(user);
                case "2":
                    sellStocks(user);
                case "3":
                    buyMutualFunds(user);
                case "4":
                    sellMutualFunds(user);
                case "5":
                    System.out.println(atm.getBroker().getStockBroker().stocksToString(user));
                case "6":
                    System.out.println(atm.getBroker().getMutualFundsBroker().toString(user));
                case "7":
                    goBack = true;
                default:
                    System.out.println("There is no option " + option + ". Pick a number from 1 to 7.");
                    break;

            }
        }
    }

    private void printOptions() {
        System.out.println("Select an option:");
        System.out.println("1. Buy Stocks");
        System.out.println("2. Sell Stocks");
        System.out.println("3. Buy Mutual Funds");
        System.out.println("4. Sell Mutual Funds");
        System.out.println("5. View your Stocks investments");
        System.out.println("6. View your Mutual Funds investments");
        System.out.println("7. Go Back");
        System.out.println("Enter the number: ");
    }

    private void buyStocks(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Stock symbol: ");
        String symbol = scanner.next();
        System.out.println("Enter number of shares: ");

        int shares;
        try {
            shares = Integer.valueOf(scanner.next());
        } catch (Exception e){
            shares = -1;
        }

        if (shares != -1) {
            atm.getBroker().getStockBroker().buyStocks(symbol, shares, findStockAccount(user), user.getInvestments());
        }
        else {
            System.out.println("Please enter integer greater than 0");
        }
    }

    private void sellStocks(User user) {
        viewUserStocks(user);
        System.out.println("What would you like to sell?");

        System.out.println("Enter Stock symbol: ");
        String sym = scanner.next();
        System.out.println("Enter number of shares: ");
        int shares;
        try {
            shares = Integer.getInteger(scanner.next());
        } catch (Exception e){
            shares = -1;
        }

        if (shares != -1) {
            atm.getBroker().getStockBroker().sellStocks(findStockAccount(user), sym, shares, user.getInvestments());
        }
        else {
            System.out.println("Please enter integer greater than 0.");
        }
    }

    private void buyMutualFunds(User user) {
        MutualFund fundToBuy = listFunds();
        System.out.println("Enter the amount you would like to invest: ");
        String amount = scanner.next();

        atm.getBroker().getMutualFundsBroker().buyMutualFunds(user, fundToBuy, Double.valueOf(amount));
    }

    private void sellMutualFunds(User user) {
        viewUserMutualFunds(user);
        System.out.println("Enter the fund you would like to sell: ");
        String name = scanner.next();
        MutualFund fundToSell = findMutualFund(user, name);

        while (fundToSell == null) {
            System.out.println("The fund you entered is invalid. Please enter a valid fund name.");
            name = scanner.next();
            fundToSell = findMutualFund(user, name);
        }

        System.out.println("Enter the amount you would like to sell: ");
        String amount = scanner.next();

        atm.getBroker().getMutualFundsBroker().sellMutualFunds(user, fundToSell, Double.valueOf(amount));
    }

    private MutualFund listFunds() {
        System.out.println("Select the type of fund you would like to invest in:");
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

    private void viewUserStocks(User user) {
        System.out.println("Here are your current stocks:");
        for (Stock stock : user.getInvestments().getStockPortfolio()) {
            System.out.println(stock);
        }
    }

    private void viewUserMutualFunds(User user) {
        HashMap<MutualFund, ArrayList<Double>> mutualFundsPortfolio = user.getInvestments().getMutualFundPortfolio();

        for (Map.Entry<MutualFund, ArrayList<Double>> entry : mutualFundsPortfolio.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

    }

    private MutualFund findMutualFund(User user, String name) {
        HashMap<MutualFund, ArrayList<Double>> mutualFundsPortfolio = user.getInvestments().getMutualFundPortfolio();

        for (Map.Entry<MutualFund, ArrayList<Double>> entry : mutualFundsPortfolio.entrySet()) {
            if (entry.getKey().getName().equals(name)) {
                return entry.getKey();
            }
        }

        return null;
    }

    private Asset findStockAccount(User user) {
        for (Account account : user.getAccounts()) {
            if (account.getType().equals("stock")) {
                return (Asset)account;
            }
        }
        return null;
    }
}
