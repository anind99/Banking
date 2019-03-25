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
        File file1 = new File(System.getProperty("user.dir") + "/phase1/src/main/Text Files/stocks1.txt");

        try {
            FileReader fr = new FileReader(file1);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String name = line.split(",")[0];
            Float price = Float.parseFloat(line.split(",")[1]);
            int rating = Integer.parseInt(line.split(",")[2]);

        } catch (Exception e){
            System.out.println("error");
        }
    }
    private void ldhelper(String line){
    }

    protected void buystock(User user, int portfolio, int amount) {

    }

    protected void sellstock(User user, int portfolio, int amount) {
    }


}

