package Util;

/**
 * C-RAT Server & Client
 * author @ThePoog
 * MasterProject @SINTEF @UiO
 * November 2018
 */

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;

/**
 * Class Responsible to Encrypt and Decrypt
 * Communication with users after safeProtocolCompleted
 *
 * http://tutorials.jenkov.com/java-cryptography/cipher.html
 */
public class Encryptor {
    private final static String ALGO = "AES";
    private byte[] key;

    public Encryptor(byte[] key){
        this.key = key;
    }

    public byte[] encrypt(byte[] plainText) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGO);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(plainText);
    }

    public byte[] decrypt(byte[] cipherText) throws Exception{
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGO);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        return cipher.doFinal(cipherText);
    }

    public SealedObject encryptObject(Serializable object) throws Exception{
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGO);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        SealedObject sealedObject = new SealedObject(object,cipher);
        return sealedObject;
    }

    public Object decryptObject(SealedObject sealedObject) throws Exception{
        SecretKeySpec secretKey = new SecretKeySpec(key, ALGO);
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        return sealedObject.getObject(cipher);
    }
}
