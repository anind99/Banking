package investments;

import atm.*;
import bankmanager.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;


public class Broker {
    private ATM atm;
    public StockBroker stockBroker;
    public MutualFundsBroker mutualFundsBroker;
    JSONObject json;

    public Broker(ATM atm, BankManager bm) {
        this.atm = atm;
        this.stockBroker = new StockBroker(atm);
        this.mutualFundsBroker = new MutualFundsBroker(atm);
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

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
}

