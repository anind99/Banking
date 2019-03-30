package interfaces;

import account.*;
import bankmanager.*;
import atm.*;

import java.util.Scanner;

public class BrokerInterface {
    private final ATM atm;

    public BrokerInterface(ATM atm) {
        this.atm = atm;
    }

    private Scanner scanner = new Scanner(System.in);

    void displayBrokerMenu(Broker broker){
        System.out.println("Choose one of the following options:");
        System.out.println("1. View User Stock account Info");
        System.out.println("2. View/Edit Stock Info");
        System.out.println("3. Log Off");
        String option = scanner.next();
        switch (option){
            case "1": {
                brokerViewUser(broker);
            }
            case "2": {
                brokerViewStock(broker);
            }
            case "3": {
                break;
            }
            default: {
                System.out.println("There is no option \"" + option + "\". Please try again.");
            }
        }
        scanner.close();
    }

    void brokerViewStock(Broker broker){
        Scanner scanner = new Scanner(System.in);
        System.out.println("type the symbol of the stock. For list of all symbols type \"*\", To go back press \"/\"");
        String symbol = scanner.next();
    }

    void brokerViewUser(Broker broker){

    }

    void userStockBroker(ATM atm, BankManager Bm, User user, Asset Ac){
        boolean loop = true;
        Broker Br = new Broker(atm, Bm);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose option: ");
        usbHelper();

        while (loop){
            String option = scanner.next();
            if (option.equals("0")){

            } else if (option.equals("1")){
                userBuyStock(atm, Bm, user, Br,  Ac);
            } else if (option.equals("2")){
                userSellStock(atm, Bm, user, Br,  Ac);
            } else if (option.equals("3")){
                Br.stockBroker.viewUserStocks(user);
            } else if (option.equals("4")){
                System.out.println(Br.getStockBroker().getTotalStockWorth(user));
            }
        }
    }

    void userBuyStock(ATM atm, BankManager Bm, User user, Broker Br, Asset Ac){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Stock symbol");
        String sym = scanner.next();
        System.out.println("Enter number of shares");
        int shares;
        try {
            shares = Integer.getInteger(scanner.next());
        } catch (Exception e){
            shares = -1;
        }
        if (shares != -1) {
            Br.getStockBroker().buyStocks(sym, shares, Ac, user.getInvestments());
        }
        else {
            System.out.println("Enter integer greater than 0.");
        }
    }

    void userSellStock(ATM atm, BankManager Bm, User user, Broker Br, Asset Ac){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Stock symbol");
        String sym = scanner.next();
        System.out.println("Enter number of shares");
        int shares;
        try {
            shares = Integer.getInteger(scanner.next());
        } catch (Exception e){
            shares = -1;
        }
        if (shares != -1) {
            Br.getStockBroker().sellStocks(Ac, sym, shares, user.getInvestments());
        }
        else {
            System.out.println("Enter integer greater than 0");
        }
    }


    private void usbHelper(){
        System.out.println("0: Exit");
        System.out.println("1: Buy Stocks");
        System.out.println("1: Sell Stocks");
        System.out.println("3: View all your stocks and shares");
        System.out.println("4: View Total Money in Stocks");
    }
}
