package interfaces;

import atm.ATM;
import atm.User;

import java.io.*;
import java.util.Scanner;

public class SubscriptionInterface implements Serializable {
    private final ATM atm;
    transient Scanner scanner = new Scanner(System.in);

    public SubscriptionInterface(ATM a){
        this.atm = a;
    }

    public void displaySubscriptionMenu(User user) {

        boolean goBack = false;
        printChoices();
        while (!goBack) {
            String choice = scanner.next();
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
                case "5":
                    goBack = true;
                default:
                    System.out.println("Choice not valid. Please enter an integer from 1 to 5.");
                    break;

            }
            System.out.println("Enter choice: ");
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
    private void writeObject(ObjectOutputStream oos) throws IOException {
        try {
            oos.defaultWriteObject();
        } catch (IOException e){
            System.out.println("SI writeObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        try{
            ois.defaultReadObject();
        } catch (Exception e){
            System.out.println("SI readObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("SI readObjectNoData, this should never happen!");
        System.exit(-1);
    }



}
