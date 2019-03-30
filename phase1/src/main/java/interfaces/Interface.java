package atm;

import atm.*;
import account.*;
import bankmanager.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Interface implements Serializable {
    protected final ATM atm;
    private final BankManagerInterface bankManagerInterface;
    private final UserInterface userInterface;
    private final atm.BrokerInterface brokerInterface;
    protected Scanner scanner = new Scanner(System.in);

    protected Interface(ATM atm) {
        this.atm = atm;
        this.bankManagerInterface = new BankManagerInterface(atm);
        this.userInterface = new UserInterface(atm);
        this.brokerInterface = new atm.BrokerInterface(atm);
    }

    public String displayLoginMenu() {
        System.out.println("Welcome. Please login.");
        User loginUser;
        System.out.println("Username: ");
        String usernameAttempt = scanner.next();
        System.out.println("Password: ");
        String passwordAttempt = scanner.next();
        if (usernameAttempt.equals("manager") && passwordAttempt.equals("password")) {
            System.out.println("Login successful. Logging in as bank manager.");
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

    public void displayBrokerOrUserChoice(Broker broker) {
        System.out.println("Would you like to sign in as:");
        System.out.println("1. Broker");
        System.out.println("2. User");
        String option = scanner.next();
        switch (option) {
            case "1": {
                brokerInterface.displayBrokerMenu(broker);
            }
            case "2": {
                userInterface.displayUserMenu(atm.getUser("broker"));
            }
        }
    }

    public void displayUserMenu(User user) {
        userInterface.displayUserMenu(user);
    }

    public void displayBankManagerMenu(BankManager bankManager) {
        bankManagerInterface.displayManagerMenu(bankManager);
    }

    public void displayBrokerMenu(Broker broker){
        brokerInterface.displayBrokerMenu(broker);
    }


    private void writeObject(ObjectOutputStream oos) throws IOException {
        try {
            oos.defaultWriteObject();
        } catch (IOException e){
            System.out.println("Interface writeObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        try{
            ois.defaultReadObject();
        } catch (Exception e){
            System.out.println("Interface readObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("Interface readObjectNoData, this should never happen!");
        System.exit(-1);
    }

}
