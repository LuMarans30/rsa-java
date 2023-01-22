package rsa;

import java.net.Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IOThread implements Runnable {

  private Socket socket;
  private DataInputStream input;
  private DataOutputStream output;
  private String message;

  public String getExMessage() {
    return exMessage;
  }

  /**
   * Se viene lanciata un'eccezzione da run()
   */
  private String exMessage;

  /**
   * Costruttore parametrico per la lettura del messaggio
   * @param socket
   */
  public IOThread(Socket socket) {
    this.socket = socket;
    this.exMessage = "false";
  }

  public String getMessage() {
    return message;
  }

  /**
   * Lettura del messaggio 
   */
  @Override
  public void run() {
    try {
      input = new DataInputStream(socket.getInputStream());
      message = input.readUTF();
    }catch(Exception ex)
    {
      exMessage = ex.getMessage();
    }
  }

  /**
  * Scrittura del messaggio
  */
  public void run(String message)
  {
    try {
      output = new DataOutputStream(socket.getOutputStream());
      output.writeUTF(message);
    } catch (Exception ex) {
      exMessage = ex.getMessage();
    }
  }

  /**
   * Chiusura dello stream di input e del socket.
   * @throws IOException
   */ 
  public void close() throws IOException {
    input.close();
    socket.close();
  }

}