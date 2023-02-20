# RSA in Java

A Java GUI/CLI application that encrypts and decrypts a string using RSA. The GUI was created using Java Swing with the flatlaf library for a better appearance; the user can choose one of several themes from the GUI app version.

This project contains: a standalone application that performs encryption/decryption, a client, and a server.

The client sends a string to the server that performs encryption or decryption and returns the result.

The user can choose a key length between 512 and 8192 bits.

In addition, the user can choose from 3 modes, the third of which is the most secure.

The three modes are:

<ol>
  <li>
    Bob uses Alice's public key to encrypt the message before sending it to Alice. Alice can then use her private key to decrypt the message.
  </li>
  <br />
  <li>
    Bob uses his private key to encrypt the message, which can then be verified by Alice using Bob's public key. 
  </li>
  <br />
  <li>
   Bob uses his private key and Alice's public key to encrypt the message. Alice can then use her private key and Bob's public key to decrypt the message.
  </li>
</ol>
