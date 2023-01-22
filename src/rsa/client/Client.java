package rsa.client;

import java.awt.Dimension;
import java.io.IOException;
import java.net.Socket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.google.gson.*;

import rsa.IOThread;

public class Client extends rsa.Form {

    private JLabel jLabel1;
    private JTextField txtIp;
    private JButton btnConnetti;

    public Client(String title) {

        super(title);

        // Aggiungo il form di connessione al server
        jLabel1 = new JLabel("Indirizzo del server:");
        txtIp = new JTextField("localhost");
        btnConnetti = new JButton("Connetti");

        // Aggiungo i componenti al form
        add(jLabel1);
        add(txtIp);
        add(btnConnetti);

        setPreferredSize(new Dimension(950, 700));
        setLayout(null);

        // Posiziono jlabel1 sotto a jcomp9
        // jcomp9.setBounds(15, 40, 110, 25);

        jLabel1.setBounds(15, 70, 110, 25);

        // Posiziono txtIp a destra di jlabel1
        txtIp.setBounds(125, 70, 100, 25);

        // Posiziono btnConnetti a destra di txtIp
        btnConnetti.setBounds(230, 70, 100, 25);

        // Sposto verso il basso di 60px i componenti che erano sotto a btnConnetti
        // jcomp5.setBounds(375, 55, 100, 25);

        jcomp5.setBounds(375, 115, 100, 25);

        // jcomp6.setBounds(380, 260, 100, 25);
        jcomp6.setBounds(380, 320, 100, 25);

        // txtMessage.setBounds(10, 85, 920, 165);
        txtMessage.setBounds(10, 145, 920, 165);

        // btnEncrypt.setBounds(10, 255, 80, 35);
        btnEncrypt.setBounds(10, 315, 80, 35);

        // txtResult.setBounds(10, 305, 925, 255);
        txtResult.setBounds(10, 375, 925, 255);

        // btnDecrypt.setBounds(95, 255, 80, 35);
        btnDecrypt.setBounds(95, 315, 80, 35);

        // btnCopyResult.setBounds(10, 565, 925, 25);
        btnCopyResult.setBounds(10, 635, 925, 25);

        btnDecrypt.setEnabled(false);
        btnCopyResult.setEnabled(false);
        btnEncrypt.setEnabled(false);

        btnEncrypt.addActionListener(e -> {
            try {
                invio("crypt");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDecrypt.addActionListener(e -> {
            try {
                invio("decrypt");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnConnetti.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             * Collegamento al server
             * 
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!txtIp.getText().isEmpty()) {
                        String ip = txtIp.getText();
                        start(ip);
                    }

                    btnConnetti.setEnabled(false);
                    btnDecrypt.setEnabled(true);
                    btnCopyResult.setEnabled(true);
                    btnEncrypt.setEnabled(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            /**
             * Chiusura della finestra del client e chiamata del metodo stop().
             * 
             * @param windowEvent evento che si verifica in caso di chiusura della finestra
             */
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    stop();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void invio(String type) throws Exception
    {
        String message = txtMessage.getText();

        JsonObject json = new JsonObject();

        json.addProperty("tipo", type);
        json.addProperty("modalita", cmbModalita.getSelectedIndex() + 1);
        json.addProperty("chiave", cmbChiave.getItemAt(cmbChiave.getSelectedIndex()));
        json.addProperty("messaggio", message);

        String jsonStr = json.toString();

        if (!txtMessage.getText().isEmpty())
            work(jsonStr);
    }

    /**
     * Gestisce la connessione client/server
     */
    private Socket clientSocket;

    private IOThread ioThread;

    /**
     * Creazione dello stream verso il server
     * 
     * @param ip ip del server
     * @throws Exception se la creazione del socket fallisce
     */
    public void start(String ip) throws Exception {
        clientSocket = new Socket(ip, 5000);
    }

    /**
     * Chiusura dei thread di lettura/scrittura da/a il server, del socket e
     * dell'applicazione.
     */
    public void stop() throws IOException {

        if (ioThread != null)
            ioThread.close();

        System.exit(0);
    }

    /**
     * Scrittura del messaggio inserito nella TextField txtMsg verso il server e
     * lettura del messaggio dal server con relativa visualizzazione nella TextField
     * txtRisultato.
     * 
     * @param messaggio messaggio da inviare al server
     */
    public void work(String messaggio) {

        try {
            ioThread = new IOThread(clientSocket);
            ioThread.run(messaggio);

            if (!ioThread.getExMessage().equals("false"))
                throw new Exception(ioThread.getExMessage());

            ioThread.run();

            if (!ioThread.getExMessage().equals("false"))
                throw new Exception(ioThread.getExMessage());

            String message = ioThread.getMessage();
            txtResult.setText(message);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {

        args = new String[1];
        args[0] = "RSA Client";

        rsa.Form.main(args);
    }

}
