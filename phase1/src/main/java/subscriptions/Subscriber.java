package subscriptions;

import account.Account;
import account.CreditCard;
import atm.ATM;
import atm.User;

import java.util.Calendar;
import java.util.Scanner;

public class Subscriber {
    /**
     * Class that handles the user's subscribing functions
     *
     * Methods:
     *
     * addSubscription: adds a new subscription of the given name to the user's subscriptions (ArrayList)
     * checkCredit: User can make a subscription if they have a credit card.
     * createSubscription: creates a new subscription not currently in the atm subscription list
     * hasSubscriptionAtm: Checks if the atm has a subscription in it's list
     * hasSubscriptionUser: Checks if the user has a subscription in their list
     * updateAllSubscriptions: To be called in the atm upon restart,
     * updates all subscription from every user once a month.
     * showAllSubscriptions: Prints all the current subscriptions in the atm.
     * showUserSubscriptions: Prints all the subscriptions the user has.
     * removeSubscription: Removes given subscription from user's list.
     */

    private ATM atm;

    public Subscriber(ATM a){
        this.atm = a;
    }



    public void addSubscription(User user, String name){
        CreditCard hascredit = checkCredit(user);

        subscription s;
        boolean userHas = false;
        if (hascredit != null){
            s = hasSubscriptionUser(name, user);
            userHas = true;
            if (s == null){
                s = hasSubscriptionAtm(name, this.atm);
                userHas = false;
            }
            if (userHas){
                System.out.println("User already subscribed to: "+name);
            } else if (s != null){
                user.addSubscription(s);
                hascredit.removeMoney(s.getCost());
            } else {
                s = createSubscription(name);
                user.addSubscription(s);
                hascredit.removeMoney(s.getCost());
            }
        } else {
            System.out.println("User must create a credit card before making a subscription.");
        }
    }

    private CreditCard checkCredit(User user){
        for (Account ac: user.getAccounts()){
            if (ac instanceof CreditCard){
                return (CreditCard)ac;

            }
        }
        return null;
    }

    private subscription createSubscription(String name){
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter cost of subscription");

        double cost = -1.00;

        while (cost == -1.00) {

            try {
                cost = Double.parseDouble(sc.next());
                if (cost <= 0) {
                    cost = -1.00;
                    System.out.println("Enter double greater than 0");
                } else {
                    return new subscription(name, cost);
                }

            } catch (Exception e) {
                System.out.println("Enter double greater than 0");
            }
        }

        return new subscription(name, cost);

    }


    private subscription hasSubscriptionAtm(String name, ATM atm){
        for (subscription sub: atm.getSubscriptions().getListOfSubscriptions()){
            if (sub.getName().equalsIgnoreCase(name)){
                return sub;
            }
        }
        return null;
    }

    private subscription hasSubscriptionUser(String name, User user){
        for (subscription sub: user.getSubscriptions()){
            if (sub.getName().equalsIgnoreCase(name)){
                return sub;
            }
        }
        return null;
    }

    public void updateAllSubscriptions(){
        Calendar date = atm.getDate();
        int day = date.get(Calendar.DAY_OF_MONTH);
        if (day == 1){
            for (User user: this.atm.getListOfUsers()){
                CreditCard userCred = checkCredit(user);
                if (userCred != null){
                    for (subscription sub: user.getSubscriptions()){
                        double m = userCred.getBalance();
                        userCred.removeMoney(sub.getCost());
                        if (userCred.getBalance() == m){
                            user.removeSubsciption(sub.getName());
                            System.out.println("Not enough funds for subscription: "+sub.getName());
                        }
                    }
                } else {
                    user.removeAllSubscriptions();
                    System.out.println("No Credit card found, all Subscriptions cancelled from user: "+user.getUsername());
                }
            }
        }
    }

    public void showAllSubscriptions(){
        for (subscription sub: atm.getSubscriptions().getListOfSubscriptions()){
            System.out.println("Subscription: "+sub.getName()+" Cost: "+sub.getCost());
        }
    }

    public void showUserSubscriptions(User user){
        for (subscription sub: user.getSubscriptions()){
            System.out.println("Subscription: "+sub.getName()+" Cost: "+sub.getCost());
        }
    }

    public void removeSubscription(User user, String name){
        user.removeSubsciption(name);
    }



}
