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


public class Stock {
    String name;
    String symbol;
    double currentPrice;
    double yesterdayPrice;
    int numShares;
    boolean updated;
    double totalSpent;

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

    public double getValue(){
        return currentPrice;
    }

    public int getNumShares(){return numShares;}

    public String getSymbol(){return symbol;}

    public String getName() {return name;}

    public void setName(String name){this.name = name;}

    public void setNumShares(int shares){this.numShares = shares;}

    public void increaseNumShares(int shares){numShares += shares;}

    public void decreaseNumShares(int shares){numShares -= shares;}



    public boolean updateStock(Calendar date){

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
                System.out.println(url);
                System.out.println(symbol);
                System.out.println(todayString);
                JSONObject jsonobject = readJsonFromUrl(url);
                System.out.println(jsonobject);
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
                    return false;
                }
            } catch (IOException e){
                System.out.println("IOException in Stock.java. Did you pick a non-existing symbol?");
                System.exit(-1);
            }
        }

//        try {
//
//
//
//            JSONObject jsonobject = readJsonFromUrl("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY" +
//                    "&symbol=" + symbol + "&outputsize=full&apikey=07UY8RAZ5L3DZ0VT");
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Calendar yesterday = (Calendar) date.clone();
//
//
//            yesterday.add(Calendar.DATE, -1);
//            String todayString = sdf.format(date.getTime());
//            String yesterdayString = sdf.format(yesterday.getTime());
//
//            url = "https://www.quandl.com/api/v3/datasets/WIKI/" + symbol + "/data.json?&start_date=" + todayString
//                    + "&end_date=" + todayString + "&api_key=Hr_Vc1vsvQMfwf7xeK4S";
//
//
//
//            System.out.println(jsonobject);
//
//            if (!jsonobject.getJSONObject("Time Series (Daily)").has(todayString)){
//                System.out.println("updateStock in Stock.java failed: Cannot find dateString in JSONObject. " +
//                        "Is your ATM time ahead in the future of actual time?");
//                System.exit(-1);
//            }
//
//            if (jsonobject.has("Error Message")){
//                System.out.println("Error from AlphaVantage. Is your stock symbol correct?");
//                System.exit(-1);
//            }
//            String todayPriceAsString = jsonobject.getJSONObject("Time Series (Daily)")
//                    .getJSONObject(todayString).getString("1. open");
//            String yesterdayPriceAsString = jsonobject.getJSONObject("Time Series (Daily)")
//                    .getJSONObject(yesterdayString).getString("1. open");
//
//            this.currentPrice = Double.valueOf(todayPriceAsString);
//            this.yesterdayPrice = Double.valueOf(yesterdayPriceAsString);
//
//
//        } catch (MalformedURLException e){
//            System.out.println(e.getMessage());
//            System.out.println("updateStock failed, malformed URL. This should never happen!");
//
//
//            System.exit(-1);
//        } catch (IOException e){
//            System.out.println(e.getMessage());
//            System.out.println("updateStock failed, IOException in url.openStream. This should never happen!");
//            System.out.println("Do you have the appropriate certificate installed on your JVM?");
//            System.out.println("Check the readme file for steps on how to install the certificate on your machine. ");
//            System.exit(-1);
//
//        }

        return true;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
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

    public String toString() {
        return this.name + " (" + this.symbol + "):\n" + this.numShares + " shares\n" + "total value: "
                + (this.numShares * this.currentPrice) + "\n";
    }
}
