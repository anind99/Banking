package broker;

import atm.*;
import bankmanager.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import broker.MutualFundsBroker;
import broker.StockBroker;
import account.*;

/***
 * Broker is a class that creates a new Broker for the ATM. A broker will be able to trade stocks and mutual funds
 * and will be able to buy and sell into the mutual funds.
 *
 */
public class Broker implements Serializable {
    public ATM atm;
    public StockBroker stockBroker;
    public MutualFundsBroker mutualFundsBroker;
    JSONObject json;

    public Broker(ATM atm, BankManager bm) {
        this.atm = atm;
        this.stockBroker = new StockBroker(atm, this);
        this.mutualFundsBroker = new MutualFundsBroker(atm, this);
        this.json = loadJSONFromText();
        bm.createUser("broker", "password");

    }

    public StockBroker getStockBroker() {
        return stockBroker;
    }

    public MutualFundsBroker getMutualFundsBroker() {
        return mutualFundsBroker;
    }

    private JSONObject loadJSONFromText(){
        JSONObject json = null;
        try {
            File file = new File(System.getProperty("user.dir") + "/phase1/src/main/Text Files/stocklist.txt");
            FileInputStream is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is);
            Reader rd = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            json = new JSONObject(sb.toString());
            return json;
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound! Why don't you have stocklist.txt?");
            System.exit(-1);
        } catch (IOException e){
            System.out.println("IOException! This shouldn't happen");
            System.exit(-1);
        }
        return json;
    }

    public boolean checkIfStockIsValid(String symbol){
        return json.has(symbol);
    }

    public String companyNameFromSymbol(String symbol){
        return json.getString(symbol);
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        try {
            oos.defaultWriteObject();
        } catch (IOException e){
            System.out.println("Broker writeObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        try{
            ois.defaultReadObject();
        } catch (Exception e){
            System.out.println("Broker readObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("Broker readObjectNoData, this should never happen!");
        System.exit(-1);
    }
}