package subscriptions;

import java.util.ArrayList;

public class availableSubscriptions {
    /**
     * Class that stores the available subscriptions.
     *
     * Attributes:
     * listOfSubscriptions: ArrayList<Subscription> Stores all te current subscriptions.
     */

    private ArrayList<subscription> listOfSubscriptions = new ArrayList<>();


    public availableSubscriptions(){
        basicSubscriptions();
    }

    private void basicSubscriptions(){
        listOfSubscriptions.add(new subscription("netflix", 12.99));
        listOfSubscriptions.add(new subscription("spotify", 9.50));
        listOfSubscriptions.add(new subscription("RogersTv", 35.00));
        listOfSubscriptions.add(new subscription("FidoMobile", 44.99));
    }

    public ArrayList<subscription> getListOfSubscriptions(){
        return listOfSubscriptions;
    }

    public void addSubscription (String name, double price){
        boolean added = false;

        for (subscription sub: listOfSubscriptions){
            if (name.equals(sub.getName())){
                added = true;
            }
        }

        if (added){
            System.out.println("Already added.");
        } else {
            listOfSubscriptions.add(new subscription(name, price));
            System.out.println("Added: "+name);
        }

    }
}
