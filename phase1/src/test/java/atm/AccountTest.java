package atm;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;


public class AccountTest {

    private Account account;

    @Before
    public void setUp() {
        account = new Checking(000);
    }

    @Test
    public void test() {
        account.addMoney(1000.00);
        boolean variable = account.payBillWriting(100.00,"Test1");
        assertTrue(variable);
        //double balance = account.getBalance();
        //assertEquals(1000, balance, 0.0);
    }

}
