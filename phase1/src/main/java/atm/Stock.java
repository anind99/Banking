package atm;
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

    public void setNumShares(int shares){
        numShares += shares;
    }


    void updatePrice(Calendar date){
        URL url = null;
        try {
            JSONObject jsonobject = readJsonFromUrl("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY" +
                    "&symbol=" + symbol + "&outputsize=full&apikey=07UY8RAZ5L3DZ0VT");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar yesterday = (Calendar) date.clone();
            yesterday.add(Calendar.DATE, -1);
            String todayString = sdf.format(date.getTime());
            String yesterdayString = sdf.format(yesterday.getTime());
            if (!jsonobject.getJSONObject("Time Series (Daily)").has(todayString)){
                System.out.println("updatePrice in Stock.java failed: Cannot find dateString in JSONObject. " +
                        "Is your ATM time ahead in the future of actual time?");
                System.exit(-1);
            }

            if (jsonobject.has("Error Message")){
                System.out.println("Error from AlphaVantage. Is your stock symbol correct?");
                System.exit(-1);
            }
            String todayPriceAsString = jsonobject.getJSONObject("Time Series (Daily)")
                    .getJSONObject(todayString).getString("1. open");
            String yesterdayPriceAsString = jsonobject.getJSONObject("Time Series (Daily)")
                    .getJSONObject(yesterdayString).getString("1. open");

            this.currentPrice = Double.valueOf(todayPriceAsString);
            this.yesterdayPrice = Double.valueOf(yesterdayPriceAsString);


        } catch (MalformedURLException e){
            System.out.println(e.getMessage());
            System.out.println("updatePrice failed, malformed URL. This should never happen!");


            System.exit(-1);
        } catch (IOException e){
            System.out.println(e.getMessage());
            System.out.println("updatePrice failed, IOException in url.openStream. This should never happen!");
            System.out.println("Do you have the appropriate certificate installed on your JVM?");
            System.out.println("Check the readme file for steps on how to install the certificate on your machine. ");

            System.exit(-1);

        }

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
            //dfdf
        }
    }
}

