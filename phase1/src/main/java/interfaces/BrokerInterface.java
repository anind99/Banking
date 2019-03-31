package interfaces;

import atm.*;
import investments.MutualFund;

import java.io.*;
import java.util.Scanner;

public class BrokerInterface implements Serializable {
    private final ATM atm;

    public BrokerInterface(ATM atm) {
        this.atm = atm;
    }

    private Scanner scanner = new Scanner(System.in);

    void displayBrokerMenu(){
        System.out.println("Select an option:");
        System.out.println("1. Buy Funds");
        System.out.println("2. Sell Funds");
        System.out.println("3. Log Out");
        String option = scanner.next();

        boolean logout = false;

        while (!logout) {
            switch (option){
                case "1": {
                    buyFunds();
                    break;
                }
                case "2": {
                    sellFunds();
                    break;
                }
                case "3":{
                    logout = true;
                    break;
                }
                default: {
                    System.out.println("There is no option \"" + option + "\". Please try again.");
                    break;
                }
            }
        }
    }

    private void buyFunds() {
        MutualFund fundToBuy = listFunds();

        System.out.println("Enter the stock symbol: ");
        String symbol = scanner.next();

        System.out.println("Enter the amount of shares: ");
        String shares = scanner.next();

        atm.getBroker().getMutualFundsBroker().buyStocksFund(fundToBuy, symbol, Integer.valueOf(shares));
    }

    private void sellFunds() {
        MutualFund fundToSell = listFunds();

        System.out.println("Enter the stock symbol: ");
        String symbol = scanner.next();

        System.out.println("Enter the amount of shares: ");
        String shares = scanner.next();

        atm.getBroker().getMutualFundsBroker().sellStocksFund(fundToSell, symbol, Integer.valueOf(shares));
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

    private void writeObject(ObjectOutputStream oos) throws IOException {
        try {
            oos.defaultWriteObject();
        } catch (IOException e){
            System.out.println("BrokerInterface writeObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        try{
            ois.defaultReadObject();
        } catch (Exception e){
            System.out.println("BrokerInterface readObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("BrokerInterface readObjectNoData, this should never happen!");
        System.exit(-1);
    }

}
