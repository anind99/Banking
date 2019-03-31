package interfaces;

import atm.*;
import account.*;
import bankmanager.*;
import com.sun.xml.internal.bind.v2.TODO;

import java.io.*;
import java.util.Scanner;

public class UpdateProfileInterface implements Serializable {
    private final ATM atm;
    private Scanner scanner = new Scanner(System.in);

    public UpdateProfileInterface(ATM atm) {
        this.atm = atm;
    }

    public void displayUpdateProfileMenu(User user) {
        boolean validselection = false;
        boolean goBack = false;

        while (!goBack) {
            printOptions();
            String option = scanner.next();

            switch (option) {
                case "1":
                    changePassword(user);
                    break;
                case "2":
                    goBack = true;
                    break;
                default:
                    System.out.println("There is no option " + option + ". Pick a number from 1 to 2.");
                    break;
            }
        }
    }

    private void printOptions() {
        System.out.println("Select an option:");
        System.out.println("1. Change password");
        System.out.println("2. Go back to main menu");
        System.out.println("Enter a number: ");
    }

    private void changePassword(User user) {
        // Method for users to change their password.

        System.out.println("Type in your new password (spaces not allowed):");
        System.out.println("If you type in a password with a space, only the word before the space will be your password");
        String newPassword = scanner.next();

        user.setPassword(newPassword);
        System.out.println("\nPassword change successful");
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        try {
            oos.defaultWriteObject();
        } catch (IOException e){
            System.out.println("UPI writeObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        try{
            ois.defaultReadObject();
        } catch (Exception e){
            System.out.println("UPI readObject Failed!");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void readObjectNoData() throws ObjectStreamException {
        System.out.println("UPI readObjectNoData, this should never happen!");
        System.exit(-1);
    }
}
