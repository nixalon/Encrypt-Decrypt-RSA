import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class Crypt {

    public static String encrypt(String message, KeyPair key) {  
        return (new BigInteger(message.getBytes(StandardCharsets.UTF_8))).modPow(key.getKey(), key.getN()).toString();
    } 

    public static String decrypt(String message, KeyPair key) { 
        String msg = new String(message.getBytes(StandardCharsets.UTF_8));
        return new String((new BigInteger(msg)).modPow(key.getKey(), key.getN()).toByteArray());
    }
}
