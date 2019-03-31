package interfaces;

import atm.ATM;
import atm.User;

import java.util.Scanner;

public class SubscriptionInteface {
    private final ATM atm;
    private Scanner scanner = new Scanner(System.in);

    public SubscriptionInteface(ATM a){
        this.atm = a;
    }

    public void displaySubscriptionMenu(User user) {
        printChoices();
        String choice = "";

        while (!choice.equals("5")) {

            choice = scanner.next();

            if (choice.equals("1")) {
                addSubscription(user);
            } else if (choice.equals("2")) {
                removeSubscription(user);
            } else if (choice.equals("3")) {
                atm.getSubscriber().showUserSubscriptions(user);
            } else if (choice.equals("4")) {
                atm.getSubscriber().showAllSubscriptions();
            } else {
                System.out.println("Enter integer from 1 to 5");
            }
        }
    }

    private void printChoices(){

        System.out.println("Select Subscription Option: ");
        System.out.println("1: Add subscription ");
        System.out.println("2: Remove subscription ");
        System.out.println("3: View current subscriptions");
        System.out.println("4: View available subscriptions");
        System.out.println("5: Exit");

    }

    private void addSubscription(User user){
        System.out.println("Enter name of subscription.");
        String name = scanner.next();
        atm.getSubscriber().addSubscription(user, name);
    }

    private void removeSubscription(User user){
        System.out.println("Enter name of subscription.");
        String name = scanner.next();
        atm.getSubscriber().removeSubscription(user, name);
    }


}
