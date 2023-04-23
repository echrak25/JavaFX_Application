package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.scene.layout.VBox;

public class Server {

    private ServerSocket serverSocket; // socket du serveur
    private Socket socket; // socket du client
    private BufferedReader bufferedReader; // flux de lecture des messages reçus du client
    private BufferedWriter bufferedWriter; // flux d'écriture des messages envoyés au client

    public Server() {
        try {
            serverSocket = new ServerSocket(9001); // création du socket serveur avec le port 9001
            System.out.println("Server started, waiting for client to connect...");
            socket = serverSocket.accept(); // accepter la connexion entrante du client
            System.out.println("Client connected!");
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // initialisation du flux de lecture de message du client
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); // initialisation du flux d'écriture de message au client
        } catch (IOException e) {
            System.out.println("Error Creating Server");
            e.printStackTrace();
            closeEverything(socket, bufferedReader, bufferedWriter); // fermer les flux et les sockets
        }
    }

    // méthode pour envoyer un message au client
    public void sendMessageToClient(String messageToClient) {
        try {
            bufferedWriter.write(messageToClient); // écrire le message dans le flux d'écriture
            bufferedWriter.newLine();
            bufferedWriter.flush(); // envoyer le message
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error Sending Message to Client");
            closeEverything(socket, bufferedReader, bufferedWriter); // fermer les flux et les sockets
        }
    }

    // méthode pour recevoir un message du client
    public void receiveMessageFromClient(VBox vbox) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (socket.isConnected()) {// boucle infinie pour recevoir les messages tant que la connexion est établie
                    try {
                        String messageFromClient = bufferedReader.readLine(); // lecture du message dans le flux de lecture
                        SampleController.addLabel(messageFromClient, vbox); // ajout du message à l'affichage dans la boîte de dialogue (TextFlow)
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error receiving message from client");
                        closeEverything(socket, bufferedReader, bufferedWriter); // fermer les flux et les sockets
                        break;
                    }

                }

            }

        }).start(); // démarrer le thread
    }

    // méthode pour fermer les sockets et les flux de lecture et d'écriture
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (socket != null) {
                socket.close(); // fermer le socket du client
            }
            if (bufferedReader != null) {
                bufferedReader.close(); // fermer le flux de lecture
            }
            if (bufferedWriter != null) {
                bufferedWriter.close(); // fermer le flux d'écriture
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}