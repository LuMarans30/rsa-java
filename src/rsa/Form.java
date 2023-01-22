package rsa;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.awt.datatransfer.Clipboard;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.formdev.flatlaf.*;

/**
 * Form per RSA Crypt/Decrypt
 * 
 * @author Andrea Marano
 */
public class Form extends JFrame {
    protected JComboBox<String> cmbModalita;
    protected JTextArea txtMessage;
    protected JButton btnEncrypt;
    protected JButton btnDecrypt;
    protected JTextArea txtResult;
    protected JLabel jcomp5;
    protected JLabel jcomp6;
    protected JComboBox<Integer> cmbChiave;
    protected JLabel jcomp8;
    private JLabel jcomp9;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    protected JButton btnCopyResult;
    private JComboBox<String> cmbTheme;

    private int N = 2048;

    public Form(String title) {
        // construct preComponents
        String[] cmbModalitaItems = { "Modalità 1", "Modalità 2", "Modalità 3 (Default)" };
        Integer[] cmbChiaveItems = { 256, 512, 1024, 2048, 4096 };
        String[] cmbThemeItems = { "Seleziona tema (Default: Dark)", "Light", "Dark", "Darcula", "MacDark",
                "MacLight",
                "IntelliJ" };

        this.setTitle(title);

        // construct components
        cmbModalita = new JComboBox<>(cmbModalitaItems);
        cmbModalita.setSelectedIndex(2);
        btnCopyResult = new JButton("Copia risultato");
        txtMessage = new JTextArea(5, 5);
        btnEncrypt = new JButton("Encrypt");
        txtResult = new JTextArea(5, 5);
        txtResult.setEditable(false);
        jcomp5 = new JLabel("Messaggio:");
        jcomp6 = new JLabel("Risultato:");
        cmbChiave = new JComboBox<>(cmbChiaveItems);
        jcomp8 = new JLabel("Modalità:");
        jcomp9 = new JLabel("Lunghezza chiave: ");
        btnDecrypt = new JButton("Decrypt");
        jScrollPane1 = new JScrollPane(txtMessage);
        jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane2 = new JScrollPane(txtResult);
        jScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        cmbTheme = new JComboBox<>(cmbThemeItems);

        // adjust size and set layout
        setPreferredSize(new Dimension(950, 630));
        setLayout(null);

        // add components
        add(cmbModalita);
        add(txtMessage);
        add(btnEncrypt);
        add(txtResult);
        add(jcomp5);
        add(jcomp6);
        add(cmbChiave);
        add(jcomp8);
        add(jcomp9);
        add(btnDecrypt);
        add(jScrollPane1);
        add(jScrollPane2);
        add(btnCopyResult);
        add(cmbTheme);

        // set component bounds (only needed by Absolute Positioning)
        cmbModalita.setBounds(125, 10, 100, 25);
        txtMessage.setBounds(10, 85, 920, 165);
        btnEncrypt.setBounds(10, 255, 80, 35);
        txtResult.setBounds(10, 305, 925, 255);
        jcomp5.setBounds(375, 55, 100, 25);
        jcomp6.setBounds(380, 260, 100, 25);
        cmbChiave.setBounds(125, 40, 100, 25);
        jcomp8.setBounds(65, 10, 60, 25);
        jcomp9.setBounds(15, 40, 110, 25);
        btnDecrypt.setBounds(95, 255, 80, 35);
        btnCopyResult.setBounds(10, 565, 925, 25);
        cmbTheme.setBounds(235, 10, 120, 25);

        // event handling
        cmbChiave.addActionListener(e -> {
            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            generaChiavi();
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        });

        cmbTheme.addActionListener(e -> {

            String theme = cmbTheme.getSelectedItem().toString();

            cmbTheme.removeItem("Seleziona tema (Default: Dark)");

            try {
                Class<?> classe = Class
                        .forName(theme.contains("Mac") ? "com.formdev.flatlaf.themes.Flat" + theme + "Laf"
                                : "com.formdev.flatlaf.Flat" + theme + "Laf");

                UIManager.setLookAndFeel((LookAndFeel) classe.getDeclaredConstructor().newInstance());

            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            } catch (IllegalArgumentException e1) {
                e1.printStackTrace();
            } catch (InvocationTargetException e1) {
                e1.printStackTrace();
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            } catch (SecurityException e1) {
                e1.printStackTrace();
            } catch (UnsupportedLookAndFeelException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }

            SwingUtilities.updateComponentTreeUI(Form.this);
        });

        btnCopyResult.addActionListener(e -> {
            StringSelection stringSelection = new StringSelection(txtResult.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        });

        // event handling
        btnEncrypt.addActionListener(e -> {

            cryptdecrypt("crypt");
        });

        btnDecrypt.addActionListener(e -> {

            cryptdecrypt("decrypt");
        });
    }

    protected void generaChiavi() {

        this.N = cmbChiave.getItemAt(cmbChiave.getSelectedIndex());

        Future<?> generateKeys = Executors.newSingleThreadExecutor().submit(() -> {

            try {
                RSAKeyGeneration.setN(this.N);
                RSAKeyGeneration.generateKeys();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        });


        setCursor(new Cursor(Cursor.WAIT_CURSOR));

        try {
            generateKeys.get();
        } catch (InterruptedException | ExecutionException e1) {
            e1.printStackTrace();
        }

        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

    }

    protected void cryptdecrypt(String cryptdecrypt) {

        Cursor wait = new Cursor(Cursor.WAIT_CURSOR);

        this.setCursor(wait);

        if(RSAKeyGeneration.publicKey == null || RSAKeyGeneration.privateKey == null) {
            generaChiavi();
            return;
        }

        String message = txtMessage.getText();

        Modalita modalita = new Modalita(message);

        try {
            modalita.calcola(cmbModalita.getSelectedIndex() + 1, cryptdecrypt);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }

        txtResult.setText(modalita.getResult());

        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame frame = null;

        if (args.length == 0)
            frame = new Form("RSA");
        else
            frame = new rsa.client.Client(args[0]);

        frame.getContentPane().add(new JPanel());

        try {
            frame.setIconImage(ImageIO.read(new File("assets/key.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
    }
}
