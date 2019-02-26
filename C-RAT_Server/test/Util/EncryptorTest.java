package Util;

import org.junit.Assert;
import org.junit.Test;
import Util.*;

import javax.crypto.SealedObject;
import java.nio.charset.StandardCharsets;
import Messages.*;
import java.security.NoSuchAlgorithmException;

/**
 * C-RAT Server
 * author @ThePoog
 * MasterProject @SINTEF
 * November 2018
 */

public class EncryptorTest {

    @Test
    public void encryptAndDecriptString() {
        byte[] encryptionKey = "MZygpewJsCpRrfOr".getBytes(StandardCharsets.UTF_8);
        byte[] plainText = "Hello world!".getBytes(StandardCharsets.UTF_8);
        Encryptor advancedEncryptionStandard = new Encryptor(encryptionKey);
        try{
            byte[] cipherText = advancedEncryptionStandard.encrypt(plainText);
            byte[] decryptedCipherText = advancedEncryptionStandard.decrypt(cipherText);
            System.out.println(new String(plainText));
            System.out.println(new String(cipherText));
            System.out.println(new String(decryptedCipherText));

            Assert.assertEquals(new String(plainText),new String(decryptedCipherText));
        }catch (Exception e){
            e.printStackTrace();
            Assert.assertEquals(false,true);
        }
    }

    @Test
    public void generate2AESKeys(){
        try{
            byte[] key1 = Security.generateAESKey();
            byte[] key2 = Security.generateAESKey();
            System.out.println(new String(key1));
            System.out.println(new String(key2));

            if(key1 != key2){
                Assert.assertEquals(true,true);
            }else{
                System.out.println("Keys were equal");
                Assert.assertEquals(false,true);
            }
        }catch(Exception e){
            System.out.println("Error generating keys");
            Assert.assertEquals(false,true);
        }
    }

    @Test
    public void generateKeyandDoEncryptionandDecryption(){
        try {
            byte[] key = Security.generateAESKey();
            byte[] plainText = "You are awesome!!!".getBytes(StandardCharsets.UTF_8);
            Encryptor myEncryptor = new Encryptor(key);
            byte[] cipherText = myEncryptor.encrypt(plainText);
            byte[] decryptedCipherText = myEncryptor.decrypt(cipherText);

            System.out.println(new String(key));
            System.out.println(new String(plainText));
            System.out.println(new String(cipherText));
            System.out.println(new String(decryptedCipherText));

            Assert.assertEquals(new String(plainText),new String(decryptedCipherText));
        }catch (Exception e){
            e.printStackTrace();
            Assert.assertEquals(false,true);
        }
    }


    @Test
    public void encyptAndDecryptObject() throws Exception {
        Message m = new Message(1,false);
        Encryptor myEnc = new Encryptor(Security.generateAESKey());
        SealedObject so = (SealedObject) myEnc.encryptObject(m);
        Message m2 = (Message) myEnc.decryptObject(so);
        Assert.assertEquals(m,m2);
    }
}
