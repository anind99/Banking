package interfaces;

import atm.*;
import account.*;
import bankmanager.*;

import java.util.*;

public class InvestmentInterface {
    private final ATM atm;
    private Scanner scanner = new Scanner(System.in);

    public InvestmentInterface(ATM atm) {
        this.atm = atm;
    }

    public void displayInvestmentMenu() {
        boolean validselection = false;
        boolean goBack = false;
        printOptions();
        String option = scanner.next();

        while(!goBack) {
            switch (option) {
                case "1":

                case "2":

                case "3":

                case "4":

                case "5":
                    goBack = true;
                default:
                    System.out.println("There is no option " + option + ". Pick a number from 1 to 5.");
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
        System.out.println("5. Go Back");
        System.out.println("Enter the number: ");
    }
}
