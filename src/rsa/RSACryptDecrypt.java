package rsa;

import java.math.BigInteger;
import java.util.Base64;

/**
 * RSA Crypt/Decrypt
 * 
 * @author Andrea Marano
 */
public class RSACryptDecrypt {

    /**
     * Il messaggio viene cifrato
     * 
     * @param message
     * @param key
     * @return
     */
    public static String encrypt(String message, String[] key) {

        BigInteger m = new BigInteger(message.getBytes());

        BigInteger c = m.modPow(new BigInteger(key[1]), new BigInteger(key[0]));

        return Base64.getEncoder().encodeToString(c.toByteArray());
    }

    /**
     * Modalità 3
     * Il messaggio viene cifrato due volte con chiavi diverse
     * @param message
     * @param keyAlice
     * @param keyBob
     * @return
     */
    public static String encrypt(String message, String[] keyAlice, String[] keyBob) {

        BigInteger m = new BigInteger(message.getBytes());

        BigInteger c = m.modPow(new BigInteger(keyBob[1]), new BigInteger(keyBob[0]));

        c = c.modPow(new BigInteger(keyAlice[1]), new BigInteger(keyAlice[0]));

        return Base64.getEncoder().encodeToString(c.toByteArray());
    }

    /**
     * Il messaggio cifrato viene decifrato
     * 
     * @param encrypted
     * @param key
     * @return
     */
    public static String decrypt(String encrypted, String[] key) {

        BigInteger c = new BigInteger(Base64.getDecoder().decode(encrypted));

        BigInteger m = c.modPow(new BigInteger(key[1]), new BigInteger(key[0]));

        return new String(m.toByteArray());
    }

    /**
     * Modalità 3
     * Il messaggio cifrato viene decifrato due volte con chiavi diverse
     * @param encrypted
     * @param keyAlice
     * @param keyBob
     * @return
     */
    public static String decrypt(String encrypted, String[] keyAlice, String[] keyBob) {

        BigInteger c = new BigInteger(Base64.getDecoder().decode(encrypted));

        BigInteger m = c.modPow(new BigInteger(keyAlice[1]), new BigInteger(keyAlice[0]));

        m = m.modPow(new BigInteger(keyBob[1]), new BigInteger(keyBob[0]));

        return new String(m.toByteArray());
    }

}
