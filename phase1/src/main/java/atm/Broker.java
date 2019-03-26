package atm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


public class Broker {


    public ArrayList<Stock> listofstocks1;
    public ArrayList<Stock> listofstocks2;
    public ArrayList<Stock> listofstocks3;
    public ArrayList<Stock> listofstocks4;
    public ArrayList<Stock> listofstocks5;

    protected ArrayList<Object[]> buyrequests;
    protected ArrayList<Object[]> Sellrequests;
    public ATM atm;



    protected void addstock(String name, double currentPrice, String sym, int risk, int type ){
        Stock n = new Stock(name, sym, currentPrice, risk);
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



    protected void buystock(StockAccount acc, StockPortfolio port, double amount, int risk, int type){

        if (type == 1){
            stockbuyhelper(acc, listofstocks1,port, amount, risk);
        } else if (type == 2){
            stockbuyhelper(acc, listofstocks2,port, amount, risk);
        } else if (type == 3){
            stockbuyhelper(acc, listofstocks3,port, amount, risk);
        } else if (type == 4){
            stockbuyhelper(acc, listofstocks4,port, amount, risk);
        } else if (type == 5){
            stockbuyhelper(acc, listofstocks5,port, amount, risk);
        }

    }

    private void stockbuyhelper(StockAccount acc, ArrayList<Stock> list, StockPortfolio stockp, double amount, int risk){
        for (Stock s:list){
            int shares = 0;
            if (!s.updated) {
                s.currentPrice = fetchprice(s);
                s.updated = true;
            }

            while  (s.currentPrice <= amount && s.risk <= risk && shares <= 3){
                    Stock bought = new Stock(s.name, s.symbol, s.currentPrice, s.risk);
                    bought.buyPrice = s.currentPrice;
                    amount -= s.currentPrice;
                    acc.removeMoney(s.currentPrice);
                    shares += 1;

            }
        }
    }

    private double fetchprice(Stock s){
        Scanner sc =  new Scanner(System.in);
        System.out.println("Enter the current price of stock: "+s.name+" Which is of the symbol: "+s.symbol );
        return Double.parseDouble(sc.next());
    }

    protected void sellstock(StockAccount acc, StockPortfolio port, double amount, int risk, int type) {
        if (type == 1){
            stocksellhelper(acc, listofstocks1,port, amount, risk);
        } else if (type == 2){
            stocksellhelper(acc, listofstocks2,port, amount, risk);
        } else if (type == 3){
            stocksellhelper(acc, listofstocks3,port, amount, risk);
        } else if (type == 4){
            stocksellhelper(acc, listofstocks4,port, amount, risk);
        } else if (type == 5){
            stocksellhelper(acc, listofstocks5,port, amount, risk);
        }

    }

    private void stocksellhelper(StockAccount Acc, ArrayList<Stock> list, StockPortfolio stockp, double amount, int risk){

        for(Stock s: list){
           ArrayList<Stock> sell = stockinarr(s, stockp.port);
            if (sell.size() != 0){
                if (!s.updated) {
                    s.currentPrice = fetchprice(s);
                    s.updated = true;
                }
               for (Stock selling:sell){
                   if (amount - selling.currentPrice >= 0 && s.currentPrice > selling.buyPrice + 2){
                       stockp.port.remove(selling);
                       Acc.addMoney(s.currentPrice);
                       Acc.profitmade += (s.currentPrice - selling.buyPrice);

                   }
               }
            }
        }

    }

    private ArrayList<Stock> stockinarr(Stock a, ArrayList<Stock> b){
        ArrayList<Stock> arrstocks = new ArrayList<>();
        for(Stock s: b){
            if (s.name.equalsIgnoreCase(a.name)) {
                arrstocks.add(s);
            }
        }

        return arrstocks;
    }

    protected void Shutdownstocks(ArrayList<Stock> stocklist){
        for (Stock s: stocklist){
            s.updated = false;
        }
    }

    protected void shutallstocks(){
        Shutdownstocks(listofstocks1);
        Shutdownstocks(listofstocks2);
        Shutdownstocks(listofstocks3);
        Shutdownstocks(listofstocks4);
        Shutdownstocks(listofstocks5);
    }

    protected void viewStocks(ArrayList<Stock> list, boolean broker){
        for (Stock s: list){
            if (!s.updated) {
                s.currentPrice = fetchprice(s);
                s.updated = true;
            }
            if(broker){
                System.out.println("Stockname: "+s.name+" Stock Symbol: " +s.symbol+
                        "Current Price"+s.currentPrice + " Risk: "+s.risk);}
            else{
                System.out.println("Stockname: "+s.name+" Stock Symbol: " +s.symbol+
                        " Current Price"+s.currentPrice+" Buy Price"+ s.buyPrice );
            }

        }
    }

    protected void getstock(int type, String name){
        if (type == 1){
           getstockhelper(listofstocks1, name);
        } else if (type == 2){
            getstockhelper(listofstocks2, name);
        } else if (type == 3){
            getstockhelper(listofstocks3, name);
        } else if (type == 4){
            getstockhelper(listofstocks4, name);
        } else if (type == 5){
            getstockhelper(listofstocks5, name);
        }
    }

    private void getstockhelper(ArrayList<Stock> list, String name){
        boolean print = false;
        for (Stock s: list){
            if(s.name.equalsIgnoreCase(name) && !print){
                if (!s.updated) {
                    s.currentPrice = fetchprice(s);
                    s.updated = true;
                }
                System.out.println("Stockname: "+s.name+" Stock Symbol: " +s.symbol+
                        "Current Price"+s.currentPrice + " Risk: "+s.risk);
                print = true;
            }
        }
        if (!print){
            System.out.println("No Stock of name: " +name);
        }
    }

    protected void userbuyrequest(User user, int amount, int risk){
        Object[] req = new Object[3];
        req[0] = user;
        req[1] = amount;
        req[2] = risk;
        buyrequests.add(req);
    }

    protected void usersellrequest(User user, int amount){
    //user sell request
        Object[] req = new Object[2];
        req[0] = user;
        req[1] = amount;
        Sellrequests.add(req);
    }
    protected void addstockaccount(User user){
        boolean added = false;
        for (Account a: user.getAccounts()){
            if (a.type.equalsIgnoreCase("Stock"))
                added = true;
        }
        if (!added){
            int accNum = atm.getBM().acct_counter;
            atm.getBM().acct_counter += 1;
            StockAccount Stocks =  new StockAccount(accNum, atm);
            user.accounts.add(Stocks);
        }
    }

    protected void completeallrequests(){

    }


}

