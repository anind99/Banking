package interfaces;

import atm.*;
import bankmanager.*;
import broker.Broker;

import java.io.*;
import java.util.Scanner;

public class Interface implements Serializable {
    protected final ATM atm;
    private final BankManagerInterface bankManagerInterface;
    private final UserInterface userInterface;
    private final BrokerInterface brokerInterface;
    protected Scanner scanner = new Scanner(System.in);

    public Interface(ATM atm) {
        this.atm = atm;
        this.bankManagerInterface = new BankManagerInterface(atm);
        this.userInterface = new UserInterface(atm);
        this.brokerInterface = new BrokerInterface(atm);
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
        } else if (usernameAttempt.equals("broker") && passwordAttempt.equals("password")) {
            System.out.println("Login successful. Logging in as broker.");
            return "broker";
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
        boolean validselection = false;

        while (!validselection) {
            switch (option) {
                case "1": {
                    validselection = true;
                    brokerInterface.displayBrokerMenu();
                    break;
                }
                case "2": {
                    validselection = true;
                    userInterface.displayUserMenu(atm.getUser("broker"));
                    break;
                }
                default: {
                    System.out.println("There is no option " + option + ". Please try again.");
                    break;
                }
            }
        }
    }

    public void displayUserMenu(User user) {
        userInterface.displayUserMenu(user);
    }

    public void displayManagerMenu(BankManager bankManager) {
        bankManagerInterface.displayManagerMenu(bankManager);
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
