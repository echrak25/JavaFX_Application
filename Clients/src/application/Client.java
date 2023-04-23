package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javafx.scene.layout.VBox;

public class Client {
    private Socket socket; // La socket utilisée par le client
    private BufferedReader bufferedReader; // Le buffer de lecture pour la communication entrante
    private BufferedWriter bufferedWriter; // Le buffer d'écriture pour la communication sortante

    public Client(String ipAddress, int port) {
        try {
            this.socket = new Socket(ipAddress, port); // Créer une nouvelle socket avec l'adresse IP et le port spécifiés
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Initialiser le buffer de lecture avec la socket en entrée
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); // Initialiser le buffer d'écriture avec la socket en sortie
        } catch (IOException e) {
            System.out.println("Error Creating Client");
            e.printStackTrace();
            closeEverything(socket, bufferedReader, bufferedWriter); // Fermer tout en cas d'erreur
        }
    }

    // Envoyer un message au serveur
    public void sendMessageToServer(String messageToServer) {
        try {
            bufferedWriter.write(messageToServer); // Écrire le message dans le buffer d'écriture
            bufferedWriter.newLine();
            bufferedWriter.flush(); // Envoyer le message au serveur

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error Sending Message to Server");
            closeEverything(socket, bufferedReader, bufferedWriter); // Fermer tout en cas d'erreur
        }
    }

    // Recevoir un message du serveur
    public void recieveMessageFromServer(VBox vbox) {
        new Thread(new Runnable() {// Créer un nouveau thread pour la réception des messages

            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        String messageFromServer = bufferedReader.readLine(); // Lire le message entrant dans le buffer de lecture
                        SampleController.addLabel(messageFromServer, vbox); // Ajouter le message à une étiquette de l'interface utilisateur

                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error Recieving Message from Cleint");
                        closeEverything(socket, bufferedReader, bufferedWriter);
                        break;
                    }

                }

            }

        }).start(); // démarrer le thread
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (socket != null) {
                socket.close(); // fermer la socket
            }
            if (bufferedReader != null) {
                bufferedReader.close(); // fermer le buffer de lecture
            }
            if (bufferedWriter != null) {
                bufferedWriter.close(); // fermer le buffer d'ecriture
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}