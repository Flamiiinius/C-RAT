package Messages;

import javax.crypto.Cipher;
import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * C-RAT Server & Client
 * author @ThePoog
 * MasterProject @SINTEF @UiO
 * November 2018
 */

/**
 * Class Responsible for transport:
 * int messageCode
 * boolean safeProtcol
 */
//https://security.stackexchange.com/questions/33434/rsa-maximum-bytes-to-encrypt-comparison-to-aes-in-terms-of-security
public class SafeProtocol implements Serializable {
    private static final long serialVersionUID = 22L;
    private static  final String ALGO = "RSA";
    private PublicKey clientPublicKey;
    private byte[] AESSecret; //encryped on transport

    public void setClientPublicKey(PublicKey clientPublicKey){
        this.clientPublicKey=clientPublicKey;
    }

    public void setAESSecret(byte[] AESsecret)throws Exception {
        Cipher cipher=Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE,clientPublicKey);
        this.AESSecret = cipher.doFinal(AESsecret);
    }

    public byte[] getAESSecret(PrivateKey privateKey) throws Exception {
        Cipher cipher=Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE,privateKey);
        return cipher.doFinal(AESSecret);
    }


    public PublicKey getClientPublicKey(){
        return clientPublicKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SafeProtocol sp = (SafeProtocol) o;
        if (AESSecret != sp.AESSecret) {
            return false;
        }
        if (clientPublicKey != sp.clientPublicKey) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return clientPublicKey.hashCode()+AESSecret.hashCode();
    }
}
