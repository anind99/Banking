package atm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class Broker {


    public ArrayList<Stock> listofstocks1;
    public ArrayList<Stock> listofstocks2;
    public ArrayList<Stock> listofstocks3;
    public ArrayList<Stock> listofstocks4;
    public ArrayList<Stock> listofstocks5;


    private void loadstocks() {
        File dir = new File(System.getProperty("user.dir") + "/phase1/src/main/Text Files/stocks");
        File[] directoryListing = dir.listFiles();

        for (File child: directoryListing) {
            try {
                FileReader fr = new FileReader(child);
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                int type = Integer.parseInt(line);
                while (line != null) {
                    line = br.readLine();
                    ldstock(line, type);
                }

            } catch (Exception e) {
                System.out.println("done reading");
            }
        }
    }


    private void  ldstock(String line, int type){
        String name = line.split(",")[0];
        String symbol = line.split(",")[1];
        Float price = Float.parseFloat(line.split(",")[2]);
        int risk = Integer.parseInt(line.split(",")[3]);
        Stock n =  new Stock(name, symbol, price, risk);
        if (type == 1){
            listofstocks1.add(n);
        } else if (type == 2){
            listofstocks2.add(n);
        } else if (type == 3){
            listofstocks3.add(n);
        } else if (type == 4){
            listofstocks4.add(n);
        } else if (type == 5){
            listofstocks5.add(n);
        } else {System.out.println("invalid type");}
    }

    protected void buystock(StockPortfolio port, double amount, int risk, int type){

        if (type == 1){
            stockbuyhelper(listofstocks1,port, amount, risk);
        } else if (type == 2){
            stockbuyhelper(listofstocks2,port, amount, risk);
        } else if (type == 3){
            stockbuyhelper(listofstocks3,port, amount, risk);
        } else if (type == 4){
            stockbuyhelper(listofstocks4,port, amount, risk);
        } else if (type == 5){
            stockbuyhelper(listofstocks5,port, amount, risk);
        }


    }

    private void stockbuyhelper(ArrayList<Stock> list, StockPortfolio port, double amount, int risk){
        for (Stock s:list){
            int shares = 0;
            while  (s.currentPrice <= amount && s.risk <= risk && shares <= 3){
                if ( port.stocks.containsKey(s.name)){
                    port.stocks.get(s.name)[0] += 1;
                    port.stocks.get(s.name)[1] += s.currentPrice;
                    amount -= s.currentPrice;
                    shares += 1;
                }
                else{
                    Double[] arr = new Double[2];
                    arr [0] = 1.0;
                    arr[1] = s.currentPrice;
                    port.stocks.put(s.name,arr);
                }

            }
        }
    }

    protected void sellstock(User user, int portfolio, int amount) {
    }


}

