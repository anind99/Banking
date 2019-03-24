package atm;

import java.util.Scanner;

public class BrokerInterface {

    void displayBrokerInterface(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose one of the following options:");
        System.out.println("1. View User Account Info");
        System.out.println("2. View/Edit Stock Info");
        System.out.println("3. Update Stock Info");
        System.out.println("4. Log Off");
        String option = scanner.next();
        switch (option){
            case "1": {
                brokerViewUser();
            }
            case "2": {
                brokerViewStock();
            }
            case "3":{
                brokerUpdateStock();
            }
            case "4": {
                break;
            }
            default: {
                System.out.println("There is no option \"" + option + "\". Please try again.");
            }
        }
    }
}
