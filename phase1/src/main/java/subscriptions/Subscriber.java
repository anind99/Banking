package subscriptions;

import account.Account;
import account.CreditCard;
import atm.ATM;
import atm.User;

import java.io.*;
import java.util.Calendar;
import java.util.Scanner;

public class Subscriber implements Serializable {
    /**
     * Class that handles the user's subscribing functions
     *
     * attributes:
     * ATM atm: Performs functions for the specified atm.
     *
     * Methods:
     *
     * addSubscription: adds a new Subscription of the given name to the user's subscriptions (ArrayList)
     * checkCredit: User can make a Subscription if they have a credit card.
     * createSubscription: creates a new Subscription not currently in the atm Subscription list
     * hasSubscriptionAtm: Checks if the atm has a Subscription in it's list
     * hasSubscriptionUser: Checks if the user has a Subscription in their list
     * updateAllSubscriptions: To be called in the atm upon restart,
     * updates all Subscription from every user once a month.
     * showAllSubscriptions: Prints all the current subscriptions in the atm.
     * showUserSubscriptions: Prints all the subscriptions the user has.
     * removeSubscription: Removes given Subscription from user's list.
     */

    private ATM atm;

    public Subscriber(ATM a){
        this.atm = a;
    }


    /**
     * addSubscription: Adds a new subscription to the user subscriptions ArrayList.
     * Note a subscription can only be added to a user if they have a CreditCard Account.
     * @param user: user that adds a subscription.
     * @param name: name of the subscription.
     */

    public void addSubscription(User user, String name){
        CreditCard hasCredit = checkCredit(user);

        Subscription s;
        boolean userHas = false;
        if (hasCredit != null){
            s = hasSubscriptionUser(name, user);
            userHas = true;
            if (s == null){
                s = hasSubscriptionAtm(name);
                userHas = false;
            }
            if (userHas){
                System.out.println("User already subscribed to: "+name);
            } else if (s != null){
                user.addSubscription(s);
                hasCredit.removeMoney(s.getCost());
            } else {
                s = createSubscription(name);
                user.addSubscription(s);
                hasCredit.removeMoney(s.getCost());
            }
        } else {
            System.out.println("User must create a credit card before making a Subscription.");
        }
    }

    /**
     * checkCredit: Checks if user has a credit card account.
     * @param user: User Object.
     * @return returns a CreditCard object from the user's accounts.
     */

    private CreditCard checkCredit(User user){
        for (Account ac: user.getAccounts()){
            if (ac instanceof CreditCard){
                return (CreditCard)ac;

            }
        }
        return null;
    }

    /**
     * createSubscription: Creates a new Subscription.
     * @param name: name attribute of Subscription object.
     * @return : Subscription object that is newly created.
     */

    private Subscription createSubscription(String name){
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter cost of Subscription");

        double cost = -1.00;

        while (cost == -1.00) {

            try {
                cost = Double.parseDouble(sc.next());
                if (cost <= 0) {
                    cost = -1.00;
                    System.out.println("Enter double greater than 0");
                } else {
                    atm.getSubscriptions().addSubscription(name, cost);
                    return new Subscription(name, cost);
                }

            } catch (Exception e) {
                System.out.println("Enter double greater than 0");
            }
        }

        return new Subscription(name, cost);

    }

    /**
     * hasSubscriptionAtm: Checks if ATM has a subscription.
     * @param name : Name of subscription to be queried.
     * @return : Subscription object
     */


    private Subscription hasSubscriptionAtm(String name){
        for (Subscription sub: atm.getSubscriptions().getListOfSubscriptions()){
            if (sub.getName().equalsIgnoreCase(name)){
                return sub;
            }
        }
        return null;
    }

    /**
     * hasSubscriptionUser: Checks if user has a certain subscription.
     * @param name : name of subscription.
     * @param user : User object
     * @return : Subscription object
     */

    private Subscription hasSubscriptionUser(String name, User user){
        for (Subscription sub: user.getSubscriptions()){
            if (sub.getName().equalsIgnoreCase(name)){
                return sub;
            }
        }
        return null;
    }

    /**
     * updateAllSubscriptions: On the 1st of every month, all the users are charged for their subscriptions.
     */

    public void updateAllSubscriptions(){
        Calendar date = atm.getDate();
        int day = date.get(Calendar.DAY_OF_MONTH);
        if (day == 1){
            for (User user: this.atm.getListOfUsers()){
                CreditCard userCred = checkCredit(user);
                if (userCred != null){
                    for (Subscription sub: user.getSubscriptions()){
                        double m = userCred.getBalance();
                        userCred.removeMoney(sub.getCost());
                        if (userCred.getBalance() == m){
                            user.removeSubsciption(sub.getName());
                            System.out.println("Not enough funds for Subscription: "+sub.getName());
                        }
                    }
                } else {
                    user.removeAllSubscriptions();
                    System.out.println("No Credit card found, all subscriptions cancelled from user: "+user.getUsername());
                }
            }
        }
    }

    /**
     * Prints all the subscriptions currently in the atm.
     */

    public void showAllSubscriptions(){
        for (Subscription sub: atm.getSubscriptions().getListOfSubscriptions()){
            System.out.println("Subscription: "+sub.getName()+" Cost: "+sub.getCost());
        }
    }

    /**
     * Prints all the subscriptions of a user.
     * @param user : User object.
     */

    public void showUserSubscriptions(User user){
        for (Subscription sub: user.getSubscriptions()){
            System.out.println("Subscription: "+sub.getName()+" Cost: "+sub.getCost());
        }
    }

    /**
     * removes a subscription from a User Object
     * @param user : User Object
     * @param name : name of subscription.
     */

    public void removeSubscription(User user, String name){
        user.removeSubsciption(name);
    }

    // Serialization.

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        ois.defaultReadObject();
    }

    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("readObjectNoData, this should never happen!");
        System.exit(-1);
    }

}
