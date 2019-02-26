package Util;

import Core.Client;
import Messages.SafeProtocol;
import org.junit.Assert;
import org.junit.Test;

/**
 * Testing Safe Procotol
 * SafeProtocol needed to be turned on for this tests
 */
public class SafeProtocolTest {

    @Test
    public void EncryptDecryptInSafeProtocolString() throws Exception {
        PubKey pubKey = new PubKey(1024);
        System.out.println("Key generated");
        SafeProtocol sp = new SafeProtocol();
        System.out.println("Safe Protocol Message Created");
        sp.setClientPublicKey(pubKey.getPublicKey());
        System.out.println("PublicKey appended");
        String expected = "This is a secret Message";
        byte[] aeskey = expected.getBytes(); //temporary
        System.out.println(aeskey);
        sp.setAESSecret(aeskey);
        System.out.println("AESKey added");
        byte[] result =   sp.getAESSecret(pubKey.getPrivateKey());
        String str = new String(result, "UTF-8"); // for UTF-8 encoding
        System.out.println(str);
        if(expected.equals(str)){
            Assert.assertEquals(aeskey,result);
        }
    }


    @Test
    public void SafeProtocolWithLogout(){
        Client myClient = Client.getInstance();
        myClient.logout();
    }

    @Test
    public void SafeProtocolWithServerCommunication(){
        Client myClient = Client.getInstance();
    }
}
