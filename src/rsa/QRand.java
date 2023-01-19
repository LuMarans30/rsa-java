package rsa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
* Genera un numero casuale attraverso il webservice ANU Quantum Random Number Generator
*/
public class QRand implements Runnable {
    
    /**
     * Seed per la generazione di numeri casuali
     * volatile significa che il valore può essere modificato da più thread
     * @see SecureRandom
     */
    public volatile long seed;

    private void generateQRand() throws Exception
    {
        URL url = new URL("https://qrng.anu.edu.au/API/jsonI.php?length=1&type=uint8");

        String line = "";

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

        line = br.readLine();

        line = line.substring(35, line.length()).replaceAll("[^0-9]", "");

        long n = Long.parseLong(line);

        this.seed = n;
    }

    @Override
     public void run() {
        try {
            generateQRand();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getSeed() {
        return seed;
    }
}
