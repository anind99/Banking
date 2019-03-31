package atm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;

public class Run {

    public static void main(String args[]) {
        ATM atm = null;
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream(new File("serialized.blob")));
            atm = (ATM) ois.readObject();
            ois.close();
        }

        catch (FileNotFoundException e){
            System.out.println("System booting up for the first time!");
            atm = new ATM();
        }

        catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        atm.run();

    }
}
