package interfaces;

import atm.*;
import account.*;
import investments.*;

import java.io.*;
import java.util.*;

public class InvestmentInterface implements Serializable {
    private final ATM atm;
    transient Scanner scanner = new Scanner(System.in);

    public InvestmentInterface(ATM atm) {
        this.atm = atm;
    }

    public void displayInvestmentMenu(User user) {
        boolean goBack = false;
        printOptions();
        scanner = new Scanner(System.in);

        while(!goBack) {
            String option = scanner.next();
            switch (option) {
                case "1":
                    buyStocks(user);
                    break;
                case "2":
                    sellStocks(user);
                    break;
                case "3":
                    buyMutualFunds(user);
                    break;
                case "4":
                    sellMutualFunds(user);
                    break;
                case "5":
                    System.out.println(atm.getBroker().getStockBroker().stocksToString(user));
                    break;
                case "6":
                    System.out.println(atm.getBroker().getMutualFundsBroker().toString(user));
                    break;
                case "7":
                    System.out.println(atm.getBroker().getStockBroker().getTotalStockWorth(user));
                    break;
                case "8":
                    goBack = true;
                    break;
                default:
                    System.out.println("There is no option " + option + ". Pick a number from 1 to 8.");
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
        System.out.println("5. View your Stocks investmentPortfolio");
        System.out.println("6. View total money in stocks");
        System.out.println("7. View your Mutual Funds investmentPortfolio");
        System.out.println("8. Go Back");
        System.out.println("Enter the number: ");
    }

    private void buyStocks(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Stock symbol: ");
        String symbol = scanner.next();

        while (!atm.getBroker().checkIfStockIsValid(symbol)) {
            System.out.println("Stock symbol is not valid. Please enter again: ");
            symbol = scanner.next();
        }

        System.out.println("Enter number of shares: ");

        int shares;
        try {
            shares = Integer.valueOf(scanner.next());
        } catch (Exception e){
            shares = -1;
        }

        if (shares != -1) {
            atm.getBroker().getStockBroker().buyStocks(symbol, shares, findStockAccount(user), user.getInvestmentPortfolio());
        }
        else {
            System.out.println("Please enter integer greater than 0");
        }
    }

    private void sellStocks(User user) {
        atm.getBroker().getStockBroker().viewUserStocks(user);
        System.out.println("What would you like to sell?");

        System.out.println("Enter Stock symbol: ");
        scanner = new Scanner(System.in);
        String sym = scanner.next();

        // Makes the user re-enter the symbol if they do not have this stock.
        while (!atm.getBroker().getStockBroker().checkIfUserHasStock(user, sym)) {
            System.out.println("Stock symbol is not valid. Please enter again: ");
            sym = scanner.next();
        }

        System.out.println("Enter number of shares: ");
        int shares;
        try {
            shares = Integer.getInteger(scanner.next());
        } catch (Exception e){
            shares = -1;
        }

        if (shares != -1) {
            atm.getBroker().getStockBroker().sellStocks(findStockAccount(user), sym, shares, user.getInvestmentPortfolio());
        }
        else {
            System.out.println("Please enter integer greater than 0.");
        }
    }

    private void buyMutualFunds(User user) {
        MutualFund fundToBuy = listFunds();
        System.out.println("Enter the amount you would like to invest: ");
        scanner = new Scanner(System.in);
        String amount = scanner.next();

        atm.getBroker().getMutualFundsBroker().buyMutualFunds(user, fundToBuy, Double.valueOf(amount));
    }

    private void sellMutualFunds(User user) {
        viewUserMutualFunds(user);
        System.out.println("Enter the fund you would like to sell: ");
        scanner = new Scanner(System.in);
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
        scanner = new Scanner(System.in);
        boolean validSelection = false;

        while (!validSelection) {
            String option = scanner.next();
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

    private void viewUserMutualFunds(User user) {
        HashMap<MutualFund, ArrayList<Double>> mutualFundsPortfolio = user.getInvestmentPortfolio().getMutualFundPortfolio();

        for (Map.Entry<MutualFund, ArrayList<Double>> entry : mutualFundsPortfolio.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

    }

    private MutualFund findMutualFund(User user, String name) {
        HashMap<MutualFund, ArrayList<Double>> mutualFundsPortfolio = user.getInvestmentPortfolio().getMutualFundPortfolio();

        for (Map.Entry<MutualFund, ArrayList<Double>> entry : mutualFundsPortfolio.entrySet()) {
            if (entry.getKey().getName().equals(name)) {
                return entry.getKey();
            }
        }

        return null;
    }

    private Asset findStockAccount(User user) {
        for (Account account : user.getAccounts()) {
            if (account.getType().equalsIgnoreCase("stock")) {
                return (Asset)account;
            }
        }
        return null;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        try {
            oos.defaultWriteObject();
        } catch (IOException e){
            System.out.println("InvestmentInterface writeObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        try{
            ois.defaultReadObject();
        } catch (Exception e){
            System.out.println("InvestmentInterface readObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("InvestmentInterface readObjectNoData, this should never happen!");
        System.exit(-1);
    }
}
