package atm;

import java.io.*;
import java.util.Scanner;

public class BankManagerInterface {
    static void displayManagerMenu(){
        Scanner scanner = new Scanner(System.in);
        boolean validselection = false;
        while (!validselection){
            System.out.println("Select an option:");
            System.out.println("1. Create User");
            System.out.println("2. Create Account");
            System.out.println("3. Check Alerts");
            System.out.println("4. Restock Machine");
            System.out.println("5. Undo transaction");
            System.out.println("6. Logout");
            String option = scanner.next();
            switch (option) {
                case "1": {
                    System.out.println("Type the username for the new user");
                    String username = scanner.next();
                    System.out.println("Type the password for the new user");
                    String password = scanner.next();
                    ATM.getBM().create_user(username, password);
                    validselection = true;
                }
                case "2": {
                    User user = null;
                    while (user == null) {
                        System.out.println("Type in the username of the user that would like to create an account: ");
                        String username = scanner.nextLine();
                        for (User parameter : ATM.getListOfUsers()) {
                            if (parameter.getUsername().equals(username)) {
                                user = parameter;
                                break;
                            }
                        }
                        System.out.println("The username is not valid, please try again.");
                    }

                    UserInterface.CreateAccount(user);
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
                            //line = r.readLine();
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        System.err.println("Problem reading the file alerts.txt");
                    }
                    validselection = true;
                }
                case "4": {
                    System.out.println("Set which dollar bill amount to 100?");
                    System.out.println("1. Five dollars, 2. Ten dollars, 3. Twenty dollars, 4. Fifty dollars 5. Quit menu");
                    String dollarType = scanner.next();
                    if (dollarType.equals("1")) {
                        BankManager.restock(1);
                    } else if (dollarType.equals("2")) {
                        BankManager.restock(2);
                    } else if (dollarType.equals("3")) {
                        BankManager.restock(3);
                    } else if (dollarType.equals("4")) {
                        BankManager.restock(4);
                    } else if (dollarType.equals("5")) {
                       displayManagerMenu();
                    } else {
                        System.out.println("There is no option " + dollarType + ". Pick a number from 1 to 5.");
                    }

                }
                case "5": {
                    User user = null;
                    while (user == null) {
                        System.out.println("Type in the username of the user that would like to undo their last transaction: ");
                        String username = scanner.nextLine();
                        for (User parameter : ATM.getListOfUsers()) {
                            if (parameter.getUsername().equals(username)) {
                                user = parameter;
                                break;
                            }
                        }
                        System.out.println("The username is not valid, please try again.");
                    }

                    Account account = UserInterface.selectAccount(user, "undo its last transaction");
                    ATM.getBM().undo_transaction(account);
                    validselection = true;
                }
                case "6": {
                    validselection = true;
                }
                default: {
                    System.out.println("There is no option " + option + ". Pick a number from 1 to 6.");
                }
            }
        }



    }

    static String displayLoginMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome. Please login.");
            User loginUser;
            System.out.println("Username: ");
            String usernameAttempt = scanner.next();
            System.out.println("Password: ");
            String passwordAttempt = scanner.next();
            if (usernameAttempt.equals("manager") && passwordAttempt.equals("password")) {
                System.out.println("Login Successful. Logging in as bank manager");
                return "manager";
            } else {
                for (User usr : ATM.getListOfUsers()) {
                    if (usr.getUsername().equals(usernameAttempt) && usr.getPassword().equals(passwordAttempt)) {
                        loginUser = usr;
                        System.out.println("Login Successful. Logging into " + loginUser.getUsername());
                        return loginUser.getUsername();
                    }
                }
            }

        System.out.println("Login Failed, please try again");
        return "";
    }
}
