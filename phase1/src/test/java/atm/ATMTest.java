package atm;

import org.junit.Before;
import org.junit.Test;

public class ATMTest {

    @Before
    public void runmain(){
        ATM.Restart();
        ATM.getListOfUsers().get(0).accounts.get(0).lastTransaction = null;
    }

    @Test
    public void testshutdown() {
        ATM.shutdown();
    }


}

