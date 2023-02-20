# RSA in Java

A GUI/CLI Java application that encrypts and decrypts a string using RSA. The GUI was made using Java Swing with the flatlaf library for a better look and feel, the user can choose one of the several themes on the GUI version.

This project contains: a standalone app which does encryption/decryption, a client and a server.

The client sends a string to the server which does the encryption or decryption and returns the result.

The user can choose a key lenght between 512 and 8192 bits.

Moreover the user can choose between 3 modalities where the third one is the most secure.

The three modalities are:

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
