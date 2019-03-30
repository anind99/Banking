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

    void displayBrokerMenu(Broker broker){
        System.out.println("Choose one of the following options:");
        System.out.println("1. View User Stock account Info");
        System.out.println("2. View/Edit Stock Info");
        System.out.println("3. Log Off");
        String option = scanner.next();
        switch (option){
            case "1": {
                brokerViewUser(broker);
            }
            case "2": {
                brokerViewStock(broker);
            }
            case "3": {
                break;
            }
            default: {
                System.out.println("There is no option \"" + option + "\". Please try again.");
            }
        }
        scanner.close();
    }

    void brokerViewStock(Broker broker){
        Scanner scanner = new Scanner(System.in);
        System.out.println("type the symbol of the stock. For list of all symbols type \"*\", To go back press \"/\"");
        String symbol = scanner.next();
    }

    void brokerViewUser(Broker broker){

    }
}
