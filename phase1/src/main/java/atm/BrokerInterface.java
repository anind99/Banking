package atm;

import java.util.Scanner;

public class BrokerInterface extends Interface {

    public BrokerInterface(ATM atm) {
        super(atm);
    }
    private UserInterface usrInterface = new UserInterface(atm);

    private Scanner scanner = new Scanner(System.in);

    void displayBrokerOrUserChoice(Broker broker) {
        System.out.println("Would you like to sign in as:");
        System.out.println("1. Broker");
        System.out.println("2. User");
        String option = scanner.next();
        switch (option) {
            case "1": {
                displayBrokerInterface(broker);
            }
            case "2": {
                usrInterface.displayUserMenu(atm.getUser("broker"));
            }
        }
    }

    void displayBrokerInterface(Broker broker){
        System.out.println("Choose one of the following options:");
        System.out.println("1. View User Stock Account Info");
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
        System.out.println("Type the symbol of the stock. For list of all symbols type \"*\", To go back press \"/\"");
        String symbol = scanner.next();
    }

    void brokerViewUser(Broker broker){

    }
}
