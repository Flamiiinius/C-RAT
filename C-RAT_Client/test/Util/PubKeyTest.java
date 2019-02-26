package Util;


import org.junit.Test;

public class PubKeyTest {

        @Test
        public void testPubKey() throws Exception {
            PubKey pk = new PubKey(1024);
            System.out.println(pk.getPrivateKey());
            System.out.println( pk.getPublicKey());
        }
}
