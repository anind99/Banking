package atm;

public class Bills {

    private int[] bills = new int[4];

    Bills(int five, int ten, int twenty, int fifty) {
        bills[0] = five;
        bills[1] = ten;
        bills[2] = twenty;
        bills[3] = fifty;
    }

    /** Set the number of bills at array index "bill" */
    void set_bills(int bill, int number){
        bills[bill - 1] = number;
    }

    private void remove_bills(int bill, int number){
        bills[bill] -= number;
    }

    int getNumBills(int index) {
        return bills[index];
    }

    double get_amount(){
        // Returns how much money the ATM has.
        return (bills[0]*5.0 + bills[1]*10.0 + bills[2]*20.0 + bills[3]* 50.0);
    }

    void add_bills(int bill, int number){
        bills[bill] += number;
    }

    void withdrawBills(double amount){
        int amountToWithdraw = (int) amount;

        amountToWithdraw = withdrawBillsHelper(amountToWithdraw, 50, 3);
        amountToWithdraw = withdrawBillsHelper(amountToWithdraw, 20, 2);
        amountToWithdraw = withdrawBillsHelper(amountToWithdraw, 10, 1);
        withdrawBillsHelper(amountToWithdraw, 5, 0);

    }

    private int withdrawBillsHelper(int amountToWithdraw, int typeOfBill, int index) {
        // typeOfBill refers to either a 50, 20, 10 or 5 dollar bill in the ATM.
        // index refers to where typeOfBill is referred to in the bills array.
        // amountToWithdraw refers to the amount that the user wants to withdraw.

        if (amountToWithdraw/typeOfBill > bills[index]) {
            // Go to this if statement if there are not enough bills of this typeOfBill in the ATM.
            amountToWithdraw -= bills[index] * typeOfBill;
            System.out.println("You have received " + bills[index] + " " + typeOfBill + "$ bills");
            set_bills(index, 0);
        } else {
            int billsToWithdraw = (amountToWithdraw/typeOfBill);
            amountToWithdraw -= (billsToWithdraw * typeOfBill);
            remove_bills(index, billsToWithdraw);
            System.out.println("You have received " + (billsToWithdraw) + " " + typeOfBill + "$ bills");
        }

        return amountToWithdraw;
    }
}

