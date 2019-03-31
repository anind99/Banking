package interfaces;

import atm.*;

import java.util.*;

public class UserInterface {
    private Scanner scanner = new Scanner(System.in);
    private final ATM atm;
    private final AccountInterface accountInterface;
    private final TransactionInterface transactionInterface;
    private final InvestmentInterface investmentInterface;
    private final UpdateProfileInterface updateProfileInterface;
    private final SubscriptionInterface subscriptionInterface;

    public UserInterface(ATM atm) {
        this.atm = atm;
        this.accountInterface = new AccountInterface(atm);
        this.transactionInterface = new TransactionInterface(atm);
        this.investmentInterface = new InvestmentInterface(atm);
        this.updateProfileInterface = new UpdateProfileInterface(atm);
        this.subscriptionInterface = new SubscriptionInterface(atm);
    }

    public void displayUserMenu(User user) {
        boolean logout = false;
        while (!logout) {
            printOptions();
            String option = scanner.next();
            switch (option) {
                case "1":
                    this.transactionInterface.displayTransactionMenu(user);
                    break;
                case "2":
                    this.accountInterface.displayAccountMenu(user);
                    break;
                case "3":
                    this.investmentInterface.displayInvestmentMenu(user);
                    break;
                case "4":
                    this.updateProfileInterface.displayUpdateProfileMenu(user);
                    break;
                case "5":
                    this.subscriptionInterface.displaySubscriptionMenu(user);
                    break;
                case "6":
                    logout = true;
                    break;
                default:
                    System.out.println("There is no option " + option + ". Pick a number from 1 to 5.");
                    break;
            }
        }

    }

    private void printOptions() {
        System.out.println("Select an option:");
        System.out.println("1. Perform Transactions");
        System.out.println("2. Manage Accounts");
        System.out.println("3. Manage Investments");
        System.out.println("4. Update User Profile");
        System.out.println("5. Manage subscriptions");
        System.out.println("6. Logout");
        System.out.println("Enter a number: ");
    }
}
