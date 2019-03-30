package account;

import java.io.*;
import atm.*;

public class ReadAndWrite {
    private int depositNum = 0;
    private ATM atm;

    public ReadAndWrite(ATM atm){
        this.atm = atm;
    }

    public Double depositReader() {
        Double amount;
        try {
            File file = new File(System.getProperty("user.dir") + "/phase1/src/main/Text Files/deposits.txt");
            FileInputStream is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader r = new BufferedReader(isr);
            String line = r.readLine();
            String firstLine = line;

            int count = 0;
            while (line != null && count < depositNum) {
                count += 1;
                line = r.readLine();
            } amount = depositReaderHelper(line, count, firstLine);
            r.close();
            return amount;
        } catch (IOException e) {
            System.err.println("Problem reading the file deposits.txt");
            return 0.0;
        }
    }

    private double depositReaderHelper(String line, int count, String firstLine){
        double amount;
        if (line != null && count >= depositNum){depositNum += 1;
        }else{line = firstLine;
            depositNum = 1;}

        if (line.contains(".")){
            amount = Double.parseDouble(line);
            System.out.println("\nYou have deposited a cheque for $" + amount);
        }else{ amount = (double) ((Character.getNumericValue(line.charAt(0))) * 5 +
                Character.getNumericValue(line.charAt(1)) * 10 +
                Character.getNumericValue(line.charAt(2)) * 20 +
                Character.getNumericValue(line.charAt(3)) * 50);

            atm.getBills().addBills(0, Character.getNumericValue(line.charAt(0)));
            atm.getBills().addBills(1, Character.getNumericValue(line.charAt(1)));
            atm.getBills().addBills(2, Character.getNumericValue(line.charAt(2)));
            atm.getBills().addBills(3, Character.getNumericValue(line.charAt(3)));
            System.out.println("\nYou have deposited $" + amount + " in cash");
        }return amount;
    }

    // Adds information of the paid bill to the text file.
    public boolean payBillWriting(double amount, String receiver, int accountNum) {
        try {
            File file = new File(System.getProperty("user.dir") + "/phase1/src/main/Text Files/outgoing.txt");
            FileOutputStream is = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            Writer w = new BufferedWriter(osw);
            w.write(accountNum + " payed " + amount + " to " + receiver);
            w.close();
            return true;
        } catch (IOException e) {
            System.err.println("Problem writing to the file outgoing.txt");
            return false;
        }
    }
}
