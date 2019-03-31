package interfaces;

import atm.ATM;
import atm.User;

import java.io.*;
import java.util.Scanner;

public class SubscriptionInterface implements Serializable {
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
