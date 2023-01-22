package rsa;

import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;

/**
 * Generazione delle chiavi pubblica e privata
 * 
 * @author Andrea Marano
 */
public class RSAKeyGeneration {

    /**
     * Lunghezza della chiave in bit
     */
    public static int N;

    public static String[] privateKey;
    public static String[] publicKey;

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";

    /**
     * La variabile N è la lunghezza della chiave in bit
     * 
     * @param N
     */
    public static void setN(int N) {
        if (N <= 0)
            throw new IllegalArgumentException(ANSI_RED + "N deve essere maggiore di zero" + ANSI_RESET);

        if ((N & (N - 1)) != 0)
            throw new IllegalArgumentException(ANSI_RED + "N deve essere una potenza di 2" + ANSI_RESET);

        RSAKeyGeneration.N = N;
    }

    public static int getN() {
        return N;
    }

    public static void setPrivateKey(String[] privateKey) {
        RSAKeyGeneration.privateKey = privateKey;
    }

    public static void setPublicKey(String[] publicKey) {
        RSAKeyGeneration.publicKey = publicKey;
    }

    /**
     * Genera un numero primo casuale
     * Se la connessione a internet è disponibile,
     * viene generato il seed attraverso il webservice ANU Quantum Random Number
     * Generator
     * in ogni caso viene generato con la classe SecureRandom
     * 
     * @return
     * @throws IOException
     */
    private static BigInteger getPrimeNumber() throws IOException, InterruptedException {

        BigInteger p;
        QRand qrand = new QRand();
        Thread t = new Thread(qrand);

        if (netIsAvailable()) {
            t.start();
            t.join();
            long n = qrand.getSeed();
            SecureRandom rnd = new SecureRandom();
            rnd.setSeed(n);
            p = BigInteger.probablePrime(RSAKeyGeneration.N, rnd);
        } else {
            SecureRandom rnd = new SecureRandom();
            p = BigInteger.probablePrime(RSAKeyGeneration.N, rnd);
        }

        return p;
    }

    /**
     * Genera le chiavi pubblica e privata
     * 
     * @throws IOException
     */
    public static void generateKeys() throws IOException, InterruptedException {

        BigInteger p = getPrimeNumber();

        BigInteger q = getPrimeNumber();

        BigInteger n = p.multiply(q);

        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        BigInteger e = getPrimeNumber();

        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0) {
            e.add(BigInteger.ONE);
        }

        BigInteger d = e.modInverse(phi);

        privateKey = new String[2];
        publicKey = new String[2];

        privateKey[0] = n.toString();
        privateKey[1] = d.toString();

        publicKey[0] = n.toString();
        publicKey[1] = e.toString();
    }

    /**
     * Verifica la disponibilità della connessione a internet
     * 
     * @return
     */
    private static boolean netIsAvailable() {
        try {
            final URL url = new URL("https://qrng.anu.edu.au/API/jsonI.php?length=1&type=uint8");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

}
