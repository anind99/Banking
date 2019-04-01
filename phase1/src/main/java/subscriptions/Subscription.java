package subscriptions;

import java.io.*;

public class Subscription implements Serializable {
    /**
     * Suscription class.
     *
     * Attributes:
     * name: Name of Subscription (String)
     * cost: Cost of Subscription (double)
     */
    private double cost;
    private String name;

    public Subscription(String n, double c){
        this.name = n;
        this.cost = c;
    }

    public String getName(){
        return this.name;
    }

    public double getCost(){
        return this.cost;
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
