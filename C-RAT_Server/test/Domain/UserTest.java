package Domain;

/**
 * C-RAT Server
 * author @ThePoog
 * MasterProject @SINTEF
 * November 2018
 */

import org.junit.Assert;
import org.junit.Test;
import Core.BDConnection;

public class UserTest {
    @Test
    public void creatingUser() throws Exception {
        BDConnection myConnection = new BDConnection();
        User myUser= myConnection.getUserByName("Ketil");
        System.out.println(myUser);
    }

  /*  @Test
    public void createUserTree() throws Exception {
        BDConnection myConnection = new BDConnection();
        User myUser= myConnection.getUserByName("Ketil");
        System.out.println(myUser.getChildTree());
        Assert.assertEquals("Niels",myUser.getChildTree().get(2).getUsername());
    }*/
}
