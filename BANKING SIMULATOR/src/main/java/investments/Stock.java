package investments;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;


public class Stock implements Serializable {
    /**
     * Stock Object:
     *
     * Attributes:
     *
     * name: String name of stock
     * symbol: String symbol of stock
     * currentPrice: double current price of the stock
     * numshares: int number of shares of stock
     * updated: boolean is true when stock is updated with current value.
     */
    private String name;
    private String symbol;
    private double currentPrice;

    private int numShares;
    private boolean updated;


    // risk might also be obsolete but im leaving it here for now for legacy reasons.
    // Risk is an int from 1 to 5 that indicates the volatility of a stock.
    // 1: +/- 0.2% daily change
    // 2: +/- 0.5% daily change.
    // 3: +/- 1% daily change.
    // 4: +/- 2% daily change.
    // 5: +/- 5% daily change.

    public Stock(String n, String s, double p){
        this.name = n;
        this.symbol = s;
        this.currentPrice = p;
        this.numShares = 0;
        this.updated = false;
    }

    /**
     * This Static is for connecting to https websites without certificates.
     */

    //Source: https://stackoverflow.com/questions/18576069/how-to-save-the-file-from-https-url-in-java
    static {
        final TrustManager[] trustAllCertificates = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null; // Not relevant.
                    }
                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        // Do nothing. Just allow them all.
                    }
                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        // Do nothing. Just allow them all.
                    }
                }
        };

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCertificates, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (GeneralSecurityException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Getter function for value.
     * @return : returns currentPrice attribute.
     */

    public double getValue(){
        return currentPrice;
    }

    /**
     * Getter function for shares.
     * @return : returns num shares  attribute.
     */

    public int getNumShares(){return numShares;}

    /**
     * Getter function for symbol.
     * @return : returns symbol  attribute.
     */

    public String getSymbol(){return symbol;}

    /**
     * Getter function for name.
     * @return : returns name  attribute.
     */

    public String getName() {return name;}

    /**
     * Setter function for name of the name.
     */

    public void setName(String name){this.name = name;}

    /**
     * Sets name attribute.
     */

    public void setNumShares(int shares){this.numShares = shares;}

    /**
     * sets num shares attribute.
     */

    public void increaseNumShares(int shares){numShares += shares;}

    /**
     * Increasing function for num shares.
     */

    public void decreaseNumShares(int shares){numShares -= shares;}

    /**
     * Updates the currentPrice of the stock to the latest price available from the server.
     */

    public void updateStock(Calendar date){

        // because of issues with the API we are using, we can only fetch historical stock data from 2019 March
        // thus we are making every date go back to 2019 March of the date that was intended
        // essentially, the data loops every month with data from March 2019.
        String url;

        boolean condition = false;
        int counter = 0;
        Calendar thisDate = (Calendar) date.clone();
        while (!condition){
            try{
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String todayString = sdf.format(thisDate.getTime());
                url = "https://www.quandl.com/api/v3/datasets/WIKI/" + symbol + ".json?api_key=Hr_Vc1vsvQMfwf7xeK4S" +
                        "&start_date=" + todayString + "&end_date=" + todayString;
                JSONObject jsonobject = readJsonFromUrl(url);
                if (jsonobject.has("dataset")){
                    if (jsonobject.getJSONObject("dataset").has("data")){
                        if (jsonobject.getJSONObject("dataset").getJSONArray("data").length() != 0){
                            this.currentPrice = jsonobject.getJSONObject("dataset")
                                    .getJSONArray("data").getJSONArray(0).getDouble(1);
                            condition = true;
                        }
                        else {
                            //date is incorrect. maybe the date is a holiday where stocks dont trade?
                            //here we use yesterday's date instead.
                            thisDate.add(Calendar.DATE, -1);
                        }
                    }
                }
                if (jsonobject.has("quandl_error")){
                    System.out.println("Bad stock symbol, try again?");
                }
            } catch (IOException e){
                System.out.println("IOException in Stock.java. Did you pick a non-existing symbol?");
                System.exit(-1);
            }
        }
    }

    /**
     * Helper function that reads from the url stream
     * @param rd: Reader
     * @return : returns string for JSON reader that is used as input for the JSON constructor
     * @throws IOException : IO exception
     */

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    /**
     * Produces a JSON object from the given url.
     * @param url : url to be searched from.
     * @return : JSONObject that is produced from the url.
     * @throws IOException : IO exception
     * @throws JSONException : JSON exception
     */

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
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

    /**
     * @return : returns a string containing all stock information.
     */

    public String toString() {
        return this.name + " (" + this.symbol + "):\n" + this.numShares + " shares\n" + "total value of shares: "
                + (this.numShares * this.currentPrice) + "\n";
    }

    /**
     * Used in serialization to restore the Stock state after it reboots.
     */

    private void writeObject(ObjectOutputStream oos) throws IOException {
        try {
            oos.defaultWriteObject();
        } catch (IOException e){
            System.out.println("Stock writeObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    /**
     * Used in serialization to store the stock state when the system is closed.
     */
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        try{
            ois.defaultReadObject();
        } catch (Exception e){
            System.out.println("Stock readObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("Stock readObjectNoData, this should never happen!");
        System.exit(-1);
    }
}

