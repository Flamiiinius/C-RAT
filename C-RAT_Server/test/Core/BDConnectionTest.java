package Core;

/**
 * C-RAT Server
 * author @ThePoog
 * MasterProject @SINTEF
 * November 2018
 */

import static org.junit.Assert.*;

import Messages.NewRisk;
import Util.Security;
import org.junit.Assert;
import org.junit.Test;
import Domain.*;

import java.sql.SQLException;

/*
 * DB MUST be running while executing the test!
 */

public class BDConnectionTest {


    @Test
    public void getUserByName() {
        try {
            BDConnection myConnection = new BDConnection();
            User myUser = myConnection.getUserByName("Rodrigo");
            System.out.println(myUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void storePasswordForUser() {
        byte[] salt = Security.createSalt();
        byte[] hash = Security.createHash("example", salt);
        boolean r = false;
        System.out.println(salt + " " +hash);
        try {
            BDConnection myConnection = new BDConnection();
            r = myConnection.storePasswordForUser(1, salt, hash);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(true,r);
    }

    @Test
    public void setNewRisk() throws SQLException, InterruptedException {
        NewRisk myNewRisk = new NewRisk(2,1,"Nice Description", "Vegeta");
        BDConnection dbc = new BDConnection();
        dbc.setNewRisk(myNewRisk);
    }

    @Test
    public void getUserMap() throws InterruptedException {
        User tUser = new User(5,"BigBoss","Owner",null,null,"2,6");
        BDConnection dbc = new BDConnection();
        System.out.println(dbc.getMultipleUsersById(tUser));
    }
}