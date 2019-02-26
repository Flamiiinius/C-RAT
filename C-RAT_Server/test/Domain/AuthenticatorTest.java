package Domain;

/**
 * C-RAT Server
 * author @ThePoog
 * MasterProject @SINTEF
 * November 2018
 */

import org.junit.Assert;
import org.junit.Test;

public class AuthenticatorTest {

    @Test
    public void creatingOneUser() {
        Authenticator.setPassword(4, "please Work!");
        Authenticator a1 = new Authenticator(4);
        boolean result = a1.verify("please Work!");
        Assert.assertEquals(true, result);
    }

    @Test
    public void createPasswordsIntoDB() {
        Authenticator.setPassword(1,"example");
        Authenticator.setPassword(2,"cool");
        Authenticator.setPassword(3,"One more");
    }

    @Test
    public void createMorePasswords(){
        Authenticator.setPassword(8,"super");
        Authenticator.setPassword(9,"M1ssA");
        Authenticator.setPassword(10,"*");
        Authenticator.setPassword(11,"");
    }

    @Test
    public void testingstoredPasswords(){
        Authenticator a1 = new Authenticator(1);
        boolean r1 =  a1.verify("example");

        Authenticator a2 = new Authenticator(2);
        boolean r2 =  a2.verify("coool");

        Authenticator a3 = new Authenticator(3);
        boolean r3 =  a3.verify("One more");

        boolean[] results = new boolean[]{r1,r2,r3};

        Assert.assertArrayEquals(new boolean[]{true,false,true},results);
    }


    @Test
    public void testingstoredPasswords2(){
        Authenticator a1 = new Authenticator(8);
        boolean r1 =  a1.verify("super");

        Authenticator a2 = new Authenticator(9);
        boolean r2 =  a2.verify("m1ssa");

        Authenticator a3 = new Authenticator(10);
        boolean r3 =  a3.verify("*");

        boolean[] results = new boolean[]{r1,r2,r3};

        Assert.assertArrayEquals(new boolean[]{true,false,true},results);
    }


}