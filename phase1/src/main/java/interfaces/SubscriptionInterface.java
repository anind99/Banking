package interfaces;

import atm.ATM;
import atm.User;

import java.util.Scanner;

public class SubscriptionInterface {
    private final ATM atm;
    private Scanner scanner = new Scanner(System.in);

    public SubscriptionInterface(ATM a){
        this.atm = a;
    }

    public void displaySubscriptionMenu(User user) {
        printChoices();
        String choice = "";

        while (!choice.equals("5")) {

            choice = scanner.next();

            switch(choice) {
                    case "1":
                    addSubscription(user);
                    break;
                    case "2":
                    removeSubscription(user);
                    break;
                    case "3":
                    atm.getSubscriber().showUserSubscriptions(user);
                    break;
                     case "4":
                    atm.getSubscriber().showAllSubscriptions();
                    break;
                    default:
                    System.out.println("Enter integer from 1 to 5");
                    break;

            }
        }
    }

    private void printChoices(){

        System.out.println("Select Subscription Option: ");
        System.out.println("1: Add Subscription ");
        System.out.println("2: Remove Subscription ");
        System.out.println("3: View current subscriptions");
        System.out.println("4: View available subscriptions");
        System.out.println("5: Exit");

    }

    private void addSubscription(User user){
        System.out.println("Enter name of Subscription.");
        String name = scanner.next();
        atm.getSubscriber().addSubscription(user, name);
    }

    private void removeSubscription(User user){
        System.out.println("Enter name of Subscription.");
        String name = scanner.next();
        atm.getSubscriber().removeSubscription(user, name);
    }


}
