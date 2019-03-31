package investments;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class containing the stock porfolio and the mutual funds portfolio for a certain user. The stock porfolio will
 * hold all the stocks a user has invested in while the mutual funds portfolio will hold all the mutual funds
 * a user has invested in.
 *
 */
public class InvestmentPortfolio {

    /** An array list of stocks the user has invested in. */
    private ArrayList <Stock> stockPortfolio = new ArrayList<>();

    /** A hash map that stores the mutual fund the user has invested in as a key and stores an array list containing
     * two values: [amount user invested in the mutual fund, the percentage of the fund the user owns through this
     * investment]. */
    private HashMap<MutualFund , ArrayList<Double>> mutualFundsPortfolio = new HashMap<>();
    // maps name of the fund: [amount user invested, %owned of the fund through this investment]

    /***
     * Returns the stock portfolio of the user.
     *
     * @return the stock portfolio
     */
    public ArrayList <Stock> getStockPortfolio(){
        return stockPortfolio;
    }

    /***
     * Adds a mutual fund to the mutual funds portfolio to signal that the user has invested into this fund.
     *
     * @param fund the mutual fund the user has invested in
     * @param value an array list containing the amount the user invested in the mutual fund and the percentage of the
     *              the user owns through this investment, in this order
     */
    public void setMutualFundsPortfolio(MutualFund fund, ArrayList<Double> value){
        mutualFundsPortfolio.put(fund, value);
    }

    /***
     * Returns the mutual funds porfolio of the user.
     *
     * @return the hash map of mutual funds the user has invested in as the key and an array list containing two values:
     * [amount user invested in the mutual fund, the percentage of the fund the user owns through this investment]
     */
    public HashMap<MutualFund, ArrayList<Double>> getMutualFundPortfolio(){
        return mutualFundsPortfolio;
    }
}
