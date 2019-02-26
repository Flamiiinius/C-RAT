package Core;

/*C-RAT Client
 * author @ThePoog
 * MasterProject @SINTEF
 * November 2018
 */

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;
import Messages.*;

/*
 * Server MUST be running while executing the test!
 */

public class ClientTest {


    /**
     * Server code 601, replyied the same object
     */
    @Test
    public void sendReceiveObject() throws IOException, ClassNotFoundException {
       /* Client myClient = Client.getInstance();
        Message m = new Message(601,false);
        Message m2 = myClient.sendReceiveMessage(m);
        System.out.println("received msg");
        myClient.logout();
        Assert.assertEquals(m,m2);*/
    }

    @Test
    public void LogOut() throws IOException {
        Client myClient = Client.getInstance();
        myClient.logout();
    }
}