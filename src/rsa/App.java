package rsa;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * RSA Crypt/Decrypt
 * 
 * @author Andrea Marano
 */
public class App {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_RESET = "\u001B[0m";

        int key = 1, scelta = 1;

        HashMap<Integer, Integer> keys = new HashMap<Integer, Integer>();

        keys.put(1, 512);
        keys.put(2, 1024);
        keys.put(3, 2048);
        keys.put(4, 4096);

        do {

            System.out.println(ANSI_GREEN + "RSA Crypt/Decrypt" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "-----------------" + ANSI_RESET);
            System.out.println(ANSI_YELLOW + "Seleziona la lunghezza della chiave:" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "1 - 512 bit");
            System.out.println("2 - 1024 bit");
            System.out.println("3 - 2048 bit");
            System.out.println("4 - 4096 bit" + ANSI_RESET);
            System.out.print(ANSI_YELLOW + "\n" + "Scelta: " + ANSI_RESET);

            key = Integer.parseInt(System.console().readLine());

            if (key < 1 || key > 4)
                System.out.println(ANSI_RED + "Scelta non valida." + ANSI_RESET);

        } while (key < 1 || key > 4);

        do {
            System.out.println(ANSI_YELLOW + "Seleziona una modalità di RSA:" + ANSI_RESET);
            System.out.println(ANSI_CYAN + "1 - chiave pubblica di Alice ; chiave privata di Alice");
            System.out.println("2 - chiave privata di Bob ; chiave pubblica di Bob");
            System.out.println(
                    "3 - chiave privata di Bob + chiave pubblica di Alice ; chiave privata di Alice + chiave pubblica di Bob"
                            + ANSI_RESET);
            System.out.print("\n" + ANSI_YELLOW + "Scelta: " + ANSI_RESET);

            scelta = Integer.parseInt(System.console().readLine());

            if (scelta < 1 || scelta > 3)
                System.out.println(ANSI_RED + "Scelta non valida." + ANSI_RESET);

        } while (scelta < 1 || scelta > 3);

        System.out.print(ANSI_YELLOW + "\nImmetti un messaggio (" + ANSI_CYAN + "N - Dietro ogni codice c'è un'enigma."
                + ANSI_RESET + "): ");

        String inputString = System.console().readLine();

        String message = "Dietro ogni codice c'è un'enigma.";

        if (inputString.length() > 0 && !inputString.equalsIgnoreCase("N"))
            message = inputString;

        Modalita modalita = new Modalita(message, keys.get(key));

        RSAKeyGeneration.setN(modalita.getN());

        Future<?> generateKeys = Executors.newSingleThreadExecutor().submit(() -> {

            try {
                RSAKeyGeneration.generateKeys();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        });

        generateKeys.get();

        System.out.println(ANSI_GREEN + "Generazione delle chiavi pubblica e privata..." + ANSI_RESET);

        String messaggio_decifrato = "";
        String encrypted = "";

        modalita.calcola(scelta, "crypt");

        encrypted = modalita.getResult();

        modalita = new Modalita(encrypted, keys.get(key));

        modalita.calcola(scelta, "decrypt");

        messaggio_decifrato = modalita.getResult();

        System.out.println("\n" + ANSI_YELLOW + "Message: " + message + ANSI_RESET);

        System.out.println("\n" + ANSI_RED + "Encrypted: " + encrypted + ANSI_RESET);

        System.out.println("\n" + ANSI_GREEN + "Decrypted: " + messaggio_decifrato + ANSI_RESET + "\n");
    }

}
