package atm;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class BankManagerInterface extends Interface {

    public BankManagerInterface(ATM atm) {
        super(atm);
    }

    void displayManagerMenu(BankManager bm, ATM atm){
        Scanner scanner = new Scanner(System.in);
        boolean loggedOut = false;
        boolean validselection = false;
        while (!validselection){
            System.out.println("Select an option:");
            System.out.println("0. Set Date");
            System.out.println("1. Create User");
            System.out.println("2. Create Account");
            System.out.println("3. Check Alerts");
            System.out.println("4. Restock Machine");
            System.out.println("5. Undo transaction");
            System.out.println("6. Logout");
            System.out.println("7. Turn Off System");
            String option = scanner.next();
            switch (option) {
                case "0": {
                    boolean condition = false;
                    String year = null, month = null, day = null;
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
                case "1": {
                    System.out.println("Type the username for the new user");
                    String username = scanner.next();
                    System.out.println("Type the password for the new user");
                    String password = scanner.next();
                    atm.getBM().create_user(username, password);
                    validselection = true;
                    break;
                }
                case "2": {
                    User user = null;
                    boolean created = false;
                    int count = 0;
                    int count2 = 0;
                    while (user == null) {
                        if (count != 0){
                            System.out.println("Type in the username of the user that would like to create an account: ");
                        }
                        String username = scanner.nextLine();
                        for (User parameter : atm.getListOfUsers()) {
                            if (parameter.getUsername().equals(username)) {
                                user = parameter;
                                CreateAccount(user);
                                created = true;
                                break;
                            }
                        }
                        if (count2 !=0 && !created){System.out.println("The username is not valid, please try again.");}
                        count += 1;
                        count2 += 1;
                    }

                    validselection = true;
                    break;
                }
                case "3": {
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
                    validselection = true;
                    break;
                }
                case "4": {
                    System.out.println("Select what type of bill to restock.");
                    System.out.println("1. Five dollars, 2. Ten dollars, 3. Twenty dollars, 4. Fifty dollars");
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
                    validselection = true;
                    break;

                }
                case "5": {
                    User user = null;
                    int count = 0;
                    int count2 = 0;
                    while (user == null) {
                        if (count2 != 0){
                            System.out.println("Type in the username of the user that would like to undo their last transaction: ");
                        }
                        String username = scanner.nextLine();
                        System.out.println(username);
                        for (User parameter : atm.getListOfUsers()) {
                            if (parameter.getUsername().equals(username)) {
                                user = parameter;
                                break;
                            }
                        } if (count != 0){System.out.println("The username is not valid, please try again.");}
                        count += 1;
                        count2 += 1;
                        //System.out.println("The username is not valid, please try again.");
                    }

                    Account account = selectAccount(user, "undo its last transaction", user.getAccounts());
                    atm.getBM().undo_transaction(user, account);
                    validselection = true;
                    break;
                }
                case "6": {
                    validselection = true;
                    loggedOut = true;
                    break;
                }
                case "7":{
                    atm.testShutDown();
                    System.out.println("System now shutting down");
                    System.exit(0);

                }
                default: {
                    System.out.println("There is no option " + option + ". Pick a number from 1 to 7.");
                    break;
                }
            }if (!loggedOut) displayManagerMenu(bm, atm);
        }
        scanner.close();



    }

    String displayLoginMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome. Please login.");
            User loginUser;
            System.out.println("Username: ");
            String usernameAttempt = scanner.next();
            System.out.println("Password: ");
            String passwordAttempt = scanner.next();
            if (usernameAttempt.equals("manager") && passwordAttempt.equals("password")) {
                System.out.println("Login successful. Logging in as bank manager");
                return "manager";
            } else {
                for (User usr : atm.getListOfUsers()) {
                    if (usr.getUsername().equals(usernameAttempt) && usr.getPassword().equals(passwordAttempt)) {
                        loginUser = usr;
                        System.out.println("Login successful. Logging into " + loginUser.getUsername());
                        return loginUser.getUsername();

                    }
                }
            }

        System.out.println("Login Failed, please try again");
        return "";
    }
}
