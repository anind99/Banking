package interfaces;

import atm.*;
import investments.MutualFund;

import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class BrokerInterface implements Serializable {
    private final ATM atm;
    transient Scanner scanner;

    public BrokerInterface(ATM atm) {
        this.atm = atm;
    }


    void displayBrokerMenu(){
        String option;
        boolean logout = false;
        scanner = new Scanner(System.in);

        while (!logout) {
            System.out.println("Select an option:");
            System.out.println("1. Buy Funds");
            System.out.println("2. Sell Funds");
            System.out.println("3. Log Out");
            option = scanner.next();
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
                default:
                    System.out.println("There is no option \"" + option + "\". Please try again.");
                    break;
            }
        }
    }

    private void buyFunds() {
        MutualFund fundToBuy = listFunds();

        System.out.println("Enter the stock symbol: ");
        Scanner scanner = new Scanner(System.in);
        String symbol = scanner.next();

        System.out.println("Enter the amount of shares: ");
        String shares = scanner.next();

        if (checkIfValid(shares)){
        atm.getBroker().getMutualFundsBroker().buyStocksFund(fundToBuy, symbol, Integer.valueOf(shares));}
        else{System.out.println("Not a valid input, please try again");}
    }

    private void sellFunds() {
        MutualFund fundToSell = listFunds();

        System.out.println("Enter the stock symbol: ");
        scanner = new Scanner(System.in);
        String symbol = scanner.next();

        System.out.println("Enter the amount of shares: ");
        String shares = scanner.next();

        if (checkIfValid(shares)){
        atm.getBroker().getMutualFundsBroker().sellStocksFund(fundToSell, symbol, Integer.valueOf(shares));}
        else{System.out.println("Not a valid input, please try again");}
    }

    private boolean checkIfValid(String shares){
       StringBuilder s = new StringBuilder(shares);
       boolean valid = true;

       for (int i = 0; i < s.length(); i++){
           if(!Character.isDigit(s.charAt(i))){valid = false;}
       }return valid;
    }

    private MutualFund listFunds() {
        System.out.println("Select the type of fund:");
        System.out.println("1. Low Risk Fund");
        System.out.println("2. Medium Risk Fund");
        System.out.println("3. High Risk Fund");
        System.out.println("Enter the number: ");

        scanner = new Scanner(System.in);
        String option;
        boolean validSelection = false;

        while (!validSelection) {
            option = scanner.next();
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
