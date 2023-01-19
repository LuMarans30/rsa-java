package rsa;

public class Modalita {

    private String message;
    private String result;

    private String[] privateKeyAlice;
    private String[] publicKeyAlice;

    private String[] privateKeyBob;
    private String[] publicKeyBob;

    private int n;

    private boolean cryptdecrypt;

    public Modalita(String message, int n) {
        this.message = message;
        this.n = n;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    /**
     * Calcola seguendo la modalità scelta
     * 1- Modalità 1
     * 2- Modalità 2
     * 3- Modalità 3
     * 
     * Inoltre se cryptdecrypt è true cifra il messaggio, altrimenti lo decifra
     * Per impostare cryptdecrypt viene passato il parametro 'opzione' che può
     * essere "crypt" o "decrypt"
     * 
     * @param scelta
     * @param cryptdecrypt
     * @throws Exception
     */
    public void calcola(int scelta, String opzione) throws Exception {

        this.cryptdecrypt = opzione.equals("crypt") ? true : false;

        switch (scelta) {
            case 1:
                modalita1();
                break;
            case 2:
                modalita2();
                break;
            case 3:
                modalita3();
                break;
        }
    }

    /**
     * Modalità 1
     * 
     * Bob utlizza la chiave pubblica di Alice per cifrare il messaggio
     * Alice utilizza la chiave privata di Alice per decifrare il messaggio
     * 
     * @throws Exception
     */
    private void modalita1() throws Exception {

        privateKeyAlice = RSAKeyGeneration.privateKey;
        publicKeyAlice = RSAKeyGeneration.publicKey;

        this.result = cryptdecrypt ? RSACryptDecrypt.encrypt(message, publicKeyAlice)
                : RSACryptDecrypt.decrypt(message, privateKeyAlice);
    }

    /**
     * Modalità 2
     * 
     * Bob utilizza la propria chiave privata per cifrare il messaggio
     * Alice utilizza la chiave pubblica di Bob per decifrare il messaggio
     * 
     * @throws Exception
     */
    private void modalita2() throws Exception {

        privateKeyBob = RSAKeyGeneration.privateKey;
        publicKeyBob = RSAKeyGeneration.publicKey;

        this.result = cryptdecrypt ? RSACryptDecrypt.encrypt(message, privateKeyBob)
                : RSACryptDecrypt.decrypt(message, publicKeyBob);
    }

    /**
     * Modalità 3
     * 
     * Bob utilizza la propria chiave privata e la chiave pubblica di Alice per
     * cifrare il messaggio
     * Alice utilizza la propria chiave privata e la chiave pubblica di Bob per
     * decifrare il messaggio
     * 
     * @throws Exception
     */
    private void modalita3() throws Exception {

        privateKeyAlice = RSAKeyGeneration.privateKey;
        publicKeyAlice = RSAKeyGeneration.publicKey;

        privateKeyBob = RSAKeyGeneration.privateKey;
        publicKeyBob = RSAKeyGeneration.publicKey;

        this.result = cryptdecrypt ? RSACryptDecrypt.encrypt(message, publicKeyAlice, privateKeyBob)
                : RSACryptDecrypt.decrypt(message, privateKeyAlice, publicKeyBob);
    }

    public String getResult() {
        return result;
    }

}
