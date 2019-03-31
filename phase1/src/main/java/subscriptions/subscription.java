package subscriptions;

public class subscription {
    /**
     * Suscription class.
     *
     * Attributes:
     * name: Name of subscription (String)
     * cost: Cost of subscription (double)
     */
    private double cost;
    private String name;

    public subscription (String n, double c){
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
