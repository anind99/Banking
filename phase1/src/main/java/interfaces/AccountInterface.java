package interfaces;

import atm.*;
import account.*;

import java.io.*;
import java.util.*;

/***
 * Class representing the account menu for users to manage their accounts.
 *
 */
public class AccountInterface implements Serializable {

    /***
     * The ATM that this interface is running on.
     */
    public ATM atm;

    /***
     * The class
     */
    public GeneralInterfaceMethods general;
    transient Scanner scanner;

    public AccountInterface(ATM atm) {
        this.atm = atm;
        this.general = new GeneralInterfaceMethods(atm);
    }

    public void displayAccountMenu(User user) {
        boolean goBack = false;

        while (!goBack) {
            printOptions();
            scanner = new Scanner(System.in);
            String option = scanner.next();

            switch (option) {
                case "1":
                    general.createAccount(user);
                    break;
                case "2":
                    requestJointAccountCreation(user);
                    break;
                case "3":
                    addUserToExistingAccount(user);
                    break;
                case "4":
                    summary(user);
                    break;
                case "5":
                    goBack = true;
                    break;
                default:
                    System.out.println("There is no option " + option + ". Pick a number from 1 to 5.");
                    break;
            }
        }
    }

    private void printOptions() {
        System.out.println("Select an option:");
        System.out.println("1. Request account creation");
        System.out.println("2. Request joint account creation");
        System.out.println("3. Add user to existing account");
        System.out.println("4. View summary of accounts");
        System.out.println("5. Go back to main menu");
        System.out.println("Enter a number: ");
    }

    private void requestJointAccountCreation(User user1) {
        scanner = new Scanner(System.in);
        System.out.println("Enter the username of the user you would like to open an account with: ");
        String username = scanner.next();

        User user2 = general.findUser(username);

        while (user2 == null || user2 == user1) {
            System.out.println("The user name you entered is not valid. Please enter a valid username (Press * to quit): ");
            username = scanner.next();

            user2 = general.findUser(username);
            if (username.equals("*")) {
                break;
            }
        }

        if (!username.equals("*")) {
            String type = general.selectTypeOfAccount(false, user1);
            atm.getBM().createJointAccount(user1, user2, type);
        }
    }

    private void addUserToExistingAccount(User user1) {
        scanner = new Scanner(System.in);

        String type = general.selectTypeOfAccount(false, user1);
        general.printChoices(user1, false, type);

        Account accountToAddUser = general.selectAccount(user1, "add user to", general.listOfAccounts(user1, type));
        System.out.println("Enter the username of the user you would to like to add to this account: ");
        String username = scanner.next();

        User user2 = general.findUser(username);

        while (user2 == null || user2 == user1 ) {
            System.out.println("The user name you entered is not valid. Please enter a valid username (Press * to quit): ");
            username = scanner.next();

            user2 = general.findUser(username);
            if (username.equals("*")) {
                break;
            }
        }

        if (!username.equals("*")) {
            atm.getBM().addExistingUserToAccount(user2, accountToAddUser);
        }
    }

    private void summary(User user) {
        // Method for users to see a summary of their accounts.

        general.printChoices(user, true, "chequing");
        general.printChoices(user, true, "loc");
        general.printChoices(user, true, "savings");
        general.printChoices(user, true, "creditcard");
        general.printChoices(user, true, "stock");
        System.out.println("Your net total is: " + user.getNetTotal());

    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        try {
            oos.defaultWriteObject();
        } catch (IOException e){
            System.out.println("AccountInterface writeObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        try{
            ois.defaultReadObject();
        } catch (Exception e){
            System.out.println("AccountInterface readObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("AccountInterface readObjectNoData, this should never happen!");
        System.exit(-1);
    }
}
