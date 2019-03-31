package interfaces;

import account.*;
import atm.*;
import bankmanager.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class BankManagerInterface extends GeneralInterface implements Serializable{

    public BankManagerInterface(ATM atm) {
        super(atm);
    }

    public void displayManagerMenu(BankManager bm){

        boolean loggedOut = false;

        while (!loggedOut){
            printOptions();
            scanner = new Scanner(System.in);
            String option = scanner.next();
            switch (option) {
                case "0": {
                    setDate();
                    break;
                }
                case "1": {
                    createUser();
                    break;
                }
                case "2": {
                    creatingAccount();
                    break;
                }
                case "3": {
                    checkAlerts();
                    break;
                }
                case "4": {
                    restockMachine(bm);
                    break;
                }
                case "5": {
                    undoTransaction();
                    break;
                }
                case "6": {
                    loggedOut = true;
                    break;
                }
                case "7":{
                    shutDownSystem();
                    break;
                }
                default: {
                    System.out.println("There is no option " + option + ". Pick a number from 1 to 7.");
                    break;
                }
            }
        }
    }

    private void printOptions() {
        System.out.println("Select an option:");
        System.out.println("0. Set Date");
        System.out.println("1. Create User");
        System.out.println("2. Create account");
        System.out.println("3. Check Alerts");
        System.out.println("4. Restock Machine");
        System.out.println("5. Undo transaction");
        System.out.println("6. Logout");
        System.out.println("7. Turn Off System");
    }

    private void setDate() {
        boolean condition = false;
        String year = null, month = null, day = null;
        scanner = new Scanner(System.in);
        while(!condition){
            condition = true;
            System.out.println("Setting date:");
            System.out.println("Year:");
            year = scanner.next();
            System.out.println("Month:");
            month = scanner.next();
            System.out.println("Day:");
            day = scanner.next();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.parse(year + "-" + month + "-" + day);
            } catch (ParseException e){
                System.out.println("Parse failed. Is the date provided well formed?");
                System.out.println("Try again.");
                condition = false;
            }
        }
        atm.setDate(year + "-" + month + "-" + day);
        System.out.println("Date set, but note that time sensitive operations might not execute immediately " +
                "(such as addSavingsInterest, which happens after the system boots). Also note that the " +
                "date will still increment another day if you restart the system via the manager");
    }


    /***
     * Creates a new user. All account types will be automatically opened for each user.
     *
     */
    private void createUser(){
        System.out.println("Type the username for the new user");
        scanner = new Scanner(System.in);

        String username = scanner.next();

        User user = findUser(username);

        while (user != null) {
            System.out.println("Username is already taken. Please enter a new username:");
            username = scanner.next();
            user = findUser(username);
        }

        System.out.println("Type the password for the new user");
        String password = scanner.next();
        atm.getBM().createUser(username, password);
    }

    private void creatingAccount() {
        scanner = new Scanner(System.in);
        User user = null;
        boolean created = false;
        int count = 0;
        int count2 = 0;
        while (user == null) {
            if (count != 0) {
                System.out.println("Type in the username of the user that would like to create an account: ");
            }
            String username = scanner.next();
            for (User parameter : atm.getListOfUsers()) {
                if (parameter.getUsername().equals(username)) {
                    user = parameter;
                    createAccount(user);
                    created = true;
                    break;
                }
            }
            if (count2 != 0 && !created) {
                System.out.println("The username is not valid, please try again.");
            }
            count += 1;
            count2 += 1;
        }
    }

    private void checkAlerts(){
        try {System.out.println(System.getProperty("user.dir"));
            File file = new File(System.getProperty("user.dir") + "/phase1/src/main/Text Files/alerts.txt");
            FileInputStream is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader r = new BufferedReader(isr);
            String line = "Alerts:";
            System.out.println(line);

            while ((line = r.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Problem reading the file alerts.txt");
        }
    }

    private void restockMachine(BankManager bm){
        System.out.println("Select what type of bill to restock.");
        System.out.println("1. Five dollars, 2. Ten dollars, 3. Twenty dollars, 4. Fifty dollars");
        scanner = new Scanner(System.in);
        String dollarType = scanner.next();
        switch (dollarType) {
            case "1":
                bm.restock(1);
                break;
            case "2":
                bm.restock(2);
                break;
            case "3":
                bm.restock(3);
                break;
            case "4":
                bm.restock(4);
                break;
            default:
                System.out.println("There is no option " + dollarType + ". Pick a number from 1 to 4 or quit.");
                break;
        }
    }

    private void undoTransaction(){
        System.out.println("Type in the name of the user that would like to undo their last transaction: ");
        scanner = new Scanner(System.in);
        String username = scanner.next();
        User user = findUser(username);

        while (user == null) {
            System.out.println("The username you entered is not valid. Please enter a valid username or press * to" +
                    " go back to the main menu");
            username = scanner.next();

            if (username.equals("*")) {
                break;
            }

            user = findUser(username);
        }

        if (!username.equals("*")) {
            String type = selectTypeOfAccount(false, user);
            printChoices(user, false, type);
            Account account = selectAccount(user, "undo its last transaction", user.getAccounts());
            atm.getBM().undoTransaction(user, account);
        }
    }

    private void shutDownSystem(){
        atm.shutDown();
        System.out.println("System now shutting down");
        System.exit(0);
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        try {
            oos.defaultWriteObject();
        } catch (IOException e){
            System.out.println("BMI writeObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        try{
            ois.defaultReadObject();
        } catch (Exception e){
            System.out.println("BMI readObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("BMI readObjectNoData, this should never happen!");
        System.exit(-1);
    }
}
