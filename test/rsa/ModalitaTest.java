package rsa;

import junit.framework.TestCase;

/**
 * JUnit test per RSA Crypt/Decrypt
 * 
 * @author Andrea Marano
 */
public class ModalitaTest extends TestCase {

    /**
     * Controllo che il messaggio originale sia uguale al messaggio decifrato
     * 
     * @throws Exception
     */
    public void testModalita1() throws Exception {

        int n = 2048;

        String messaggio = "Dietro ogni codice c'è un'enigma.";

        RSAKeyGeneration.setN(n);

        RSAKeyGeneration.generateKeys();

        Modalita modalita = new Modalita(messaggio, n);

        modalita.calcola(1, "crypt");

        String encrypted = modalita.getResult();

        String decrypted = "";

        modalita = new Modalita(encrypted, n);

        modalita.calcola(1, "decrypt");

        decrypted = modalita.getResult();

        assertEquals(messaggio, decrypted);
    }

    public void testModalita2() throws Exception {

        int n = 2048;

        String messaggio = "Dietro ogni codice c'è un'enigma.";

        RSAKeyGeneration.setN(n);

        RSAKeyGeneration.generateKeys();

        Modalita modalita = new Modalita(messaggio, n);

        modalita.calcola(2, "crypt");

        String encrypted = modalita.getResult();

        String decrypted = "";

        modalita = new Modalita(encrypted, n);

        modalita.calcola(2, "decrypt");

        decrypted = modalita.getResult();

        assertEquals(messaggio, decrypted);
    }

    public void testModalita3() throws Exception {

        int n = 2048;

        String messaggio = "Dietro ogni codice c'è un'enigma.";

        RSAKeyGeneration.setN(n);

        RSAKeyGeneration.generateKeys();

        Modalita modalita = new Modalita(messaggio, n);

        modalita.calcola(3, "crypt");

        String encrypted = modalita.getResult();

        String decrypted = "";

        modalita = new Modalita(encrypted, n);

        modalita.calcola(3, "decrypt");

        decrypted = modalita.getResult();

        assertEquals(messaggio, decrypted);
    }

}
