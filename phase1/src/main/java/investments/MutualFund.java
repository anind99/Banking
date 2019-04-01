package investments;

import atm.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/***
 * A class for a mutual fund object.
 *
 */
public class MutualFund implements Serializable {
    /***
     * The risk level of the mutual fund: 1 is low risk, 2 is medium risk and 3 is a high risk fund
     */
    public int risk;
    /***
     * The name of the mutual fund
     */
    public String name;
    /***
     * The stocks in that are in this mutual fund
     */
    public ArrayList<Stock> stocks;
    /***
     * A hash map of users that have invested into this fund as keys and an array list of doubles in the following
     * format: {amount user invested, percentage of the fund the user owns through this investment}
     */
    public HashMap<User, ArrayList<Double>> investors = new HashMap<>();

    /***
     * Constructor for MutualFund.
     *
     * @param risk the risk level of the mutual fund
     * @param name the name of the mutual fund
     * @param stocks the stocks that are in this mutual fund
     */
    public MutualFund(int risk, String name, ArrayList<Stock> stocks){
        this.risk = risk;
        this.name = name;
        this.stocks = stocks;
    }

    /***
     * Getter for the value of the mutual fund.
     *
     * @return the value of the mutual fund
     */
    public double getValue(){
        double total = 0.0;
        for (Stock stock : stocks) {
            total += stock.getValue();
        }return total;
    }

    /***
     * Returns the name of the mutual fund.
     *
     * @return the name of the mutual fund.
     */
    public String getName(){return name;}

    /***
     * Returns an array list of stocks in this mutual fund.
     *
     * @return the array list of stocks in this mutual fund.
     */
    public ArrayList<Stock> getStocks(){
        return stocks;
    }

    /***
     * Returns the
     *
     * @return
     */
    public HashMap<User, ArrayList<Double>> getInvestors(){
        return investors;
    }

    public void setInvestors(User user, ArrayList<Double> investment){
        investors.put(user, investment);
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
