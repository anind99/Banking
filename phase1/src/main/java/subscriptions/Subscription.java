package subscriptions;

public class Subscription {
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

}
