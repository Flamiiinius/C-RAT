package Util;

/**
 * C-RAT Server
 * author @ThePoog
 * MasterProject @SINTEF @UiO
 * November 2018
 */


import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.*;


/**
 * Class Responsible to create hashes, salts, and keys
 */
public class Security {

    public static byte[] createSalt(){
        byte[] bytes = new byte[32];
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);
        return bytes;
    }

    //https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html
    public static byte[] createHash(String input,byte[] salt){
        byte[] inputBytes = input.getBytes();
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.reset();
            messageDigest.update(salt);
            byte[] hash = messageDigest.digest(inputBytes);
            return hash;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return PublicKey Pair(Private and Public)
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair buildKeyPair() throws NoSuchAlgorithmException {
        final int keySize = 2048;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.genKeyPair();
    }


    public static byte[] generateAESKey() throws NoSuchAlgorithmException{
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256); // for example
        SecretKey secretKey = keyGen.generateKey();
        return secretKey.getEncoded();
    }


    /**
     * Using PBKey Crypto In conjuntion with the user
     * create an AESKey used to Encrypt EveryMessage in this session
     * @return byte[] AESKey used in this session with the user
     */
}
