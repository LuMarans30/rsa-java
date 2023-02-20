# RSA in Java

![GitHub](https://img.shields.io/github/license/LuMarans30/rsa-java)
![GitHub repo size](https://img.shields.io/github/repo-size/LuMarans30/rsa-java)
![Lines of code](https://img.shields.io/tokei/lines/github/LuMarans30/rsa-java)
![GitHub issues](https://img.shields.io/github/issues/LuMarans30/rsa-java)
![Website](https://img.shields.io/website?down_message=down&up_message=online&url=https%3A%2F%2Flumarans30.github.io%2Frsa-java%2F)
![GitHub last commit](https://img.shields.io/github/last-commit/LuMarans30/rsa-java)

A Java GUI/CLI application that encrypts and decrypts a string using RSA. The GUI was created using Java Swing with the flatlaf library for a better appearance; the user can choose one of several themes from the GUI app version.
<br/>This project contains: a standalone application that performs encryption/decryption, a client, and a server.
<br/>The client sends a string to the server that performs encryption or decryption and returns the result.
<br/>The user can choose a key length between 512 and 8192 bits.
<br/>In addition, the user can choose from 3 modes, the third of which is the most secure.

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

## Documentation

The JavaDoc documentation is available on the [github page](https://lumarans30.github.io/rsa-java) of this repository.
