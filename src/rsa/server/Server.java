package rsa.server;

import java.net.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import rsa.*;

/**
 * La classe Server gestisce la connessione con un client.
 * Utilizza due thread, uno per la lettura e un altro la scrittura.
 * @author Andrea Marano
 * @version 1.0
 */
public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private IOThread ioThread;

    /**
     * La porta che viene aperta è la numero 5000 per default
     */
    public static final int PORTA = 5000;

    /**
     * Costruttore di default
     * Inizializzazione degli attributi della classe
     */
    public Server() {
        serverSocket = null;
        ioThread = null;
    }

    /**
     * Il server ascolta su una porta (di default la porta 5000)
     * @throws Exception se il metodo work() o la creazione del socket fallisce
     */
    public void start() throws Exception {
        serverSocket = new ServerSocket(PORTA);
        System.out.println("Il server e' stato avviato ed e' in attesa di connessione");
        work();
    }

    /**
     * Terminazione di tutte le connessioni e stream di dati
     * @throws Exception se la chiusura delle connessione o stream fallisce
     */
    public void stop() throws Exception {

        if(ioThread!=null)
            ioThread.close();

        serverSocket.close();

        System.exit(0);
    }

    /**
     * Finché il server non riceve il messaggio "exit", legge il messaggio e lo invia al client
     * Se riceve "exit" invia il messaggio "Connessione terminata"
     * @throws Exception se la lettura/scrittura da/a client fallisce
     */
    public void messaggia() throws Exception
    {
        boolean running = true;
        String message;

        while(running) {
            ioThread = new IOThread(clientSocket);
            ioThread.run();

            if (!ioThread.getExMessage().equals("false"))
                throw new Exception(ioThread.getExMessage());

            running = !ioThread.getMessage().equals("exit");

            if(!running)
                 message = "Connessione terminata";
            else
                message = ioThread.getMessage();

            cryptdecrypt(message);

            ioThread.run(message);

            if (!ioThread.getExMessage().equals("false"))
                throw new Exception(ioThread.getExMessage());

        }
    }

    private void cryptdecrypt(String messaggio) throws Exception
    {
        int key;
        String type;
        String contenuto;
        int modalitaIndex;

        JsonObject json = new JsonObject();

        //Get json from message
        json = JsonParser.parseString(messaggio).getAsJsonObject();

        key = json.get("chiave").getAsInt();
        type = json.get("tipo").getAsString();
        contenuto = json.get("messaggio").getAsString();
        modalitaIndex = json.get("modalita").getAsInt();

        RSAKeyGeneration.setN(key);

        RSAKeyGeneration.generateKeys();

        Modalita modalita = new Modalita(contenuto);

        try {
            modalita.calcola(modalitaIndex, type);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        ioThread.run(modalita.getResult());
    }


    /**
     * Accetta la richiesta di connessione da parte del client ed elabora l'input
     * @throws Exception se il metodo stop() lancia un'eccezzione
     */
    public void work() throws Exception {

        clientSocket = serverSocket.accept();

        System.out.println("Il client " + clientSocket.getLocalAddress() + ":" + clientSocket.getLocalPort() + " si e' connesso");

        messaggia();

        try
        {
            stop();
            System.out.println("Il client " + clientSocket.getLocalAddress() + ":" + clientSocket.getLocalPort() + " si e' disconnesso");
        }catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }


    /**
     * Avviamento del server
     * @param args argomenti del main
     */
    public static void main(String[] args)
    {
        Server maranoServer = new Server();

        try
        {
            maranoServer.start();
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }

    }

}

