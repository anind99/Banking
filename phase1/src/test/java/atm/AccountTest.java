package atm;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class AccountTest {

    private Account account;

    @Before
    public void setUp() {
        account = new Chequing(000);
    }

    @Test
    public void Alert() {
        ATM.set_bills(0,100);
        ATM.set_bills(1,100);
        ATM.set_bills(2,100);
        ATM.set_bills(3,100);
        account.addMoney(10500.00);
        account.withdraw(8500.00);
        System.out.println(ATM.get_amount());

        assertEquals(2000.00, account.getBalance(), 0.0);
        //double balance = account.getBalance();
        //assertEquals(1000, balance, 0.0);
    }

    @Test
    public void testPayBillWriting() {
        account.addMoney(1000.00);
        boolean variable = account.payBillWriting(100.00,"Test1");
        assertTrue(variable);
        //double balance = account.getBalance();
        //assertEquals(1000, balance, 0.0);
    }

    @Test
    public void testDeposit() {
        account.deposit();

        assertEquals(55.02, account.getBalance(), 0.0);
        //double balance = account.getBalance();
        //assertEquals(1000, balance, 0.0);
    }

   @Test
    public void testWithdraw() {
        ATM.set_bills(0,100);
        ATM.set_bills(1,100);
        ATM.set_bills(2,100);
        ATM.set_bills(3,100);
        account.addMoney(500.00);
        account.withdraw(285.00);

        assertEquals(215.00, account.getBalance(), 0.0);
        //double balance = account.getBalance();
        //assertEquals(1000, balance, 0.0);
    }

}
