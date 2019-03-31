package subscriptions;

import java.io.*;
import java.util.ArrayList;

public class AvailableSubscriptions implements Serializable {
    /**
     * Class that stores the available subscriptions.
     *
     * Attributes:
     * listOfSubscriptions: ArrayList<Subscription> Stores all te current subscriptions.
     */

    private ArrayList<Subscription> listOfSubscriptions = new ArrayList<>();


    public AvailableSubscriptions(){
        basicSubscriptions();
    }

    private void basicSubscriptions(){
        listOfSubscriptions.add(new Subscription("netflix", 12.99));
        listOfSubscriptions.add(new Subscription("spotify", 9.50));
        listOfSubscriptions.add(new Subscription("RogersTv", 35.00));
        listOfSubscriptions.add(new Subscription("FidoMobile", 44.99));
    }

    public ArrayList<Subscription> getListOfSubscriptions(){
        return listOfSubscriptions;
    }

    public void addSubscription (String name, double price){
        boolean added = false;

        for (Subscription sub: listOfSubscriptions){
            if (name.equals(sub.getName())){
                added = true;
            }
        }

        if (added){
            System.out.println("Already added.");
        } else {
            listOfSubscriptions.add(new Subscription(name, price));
            System.out.println("Added: "+name);
        }

    }
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
