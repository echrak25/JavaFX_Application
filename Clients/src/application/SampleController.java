package application;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class SampleController implements Initializable {
	@FXML
	private Button button_send;
	@FXML
	private TextField tf_message;
	@FXML
	private VBox vbox_messages;
	@FXML
	private ScrollPane sp_main;
	
	
	private Client client;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Création d'un client et connexion au serveur
		client = new Client("127.0.0.1", 9001);
		System.out.println("Connected to Server");
		
		// Ajouter un écouteur de changement de hauteur pour ajuster la valeur de la barre de défilement
		vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				sp_main.setVvalue((Double) newValue);
				
			}
			
		});		
		// démarrer la réception des messages entrants du serveur et les afficher dans la zone de message
		client.recieveMessageFromServer(vbox_messages);
		
		// Envoyer un message lorsqu'on clique sur le bouton "Send"
		button_send.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				String messageToSend=tf_message.getText();
				if(!messageToSend.isEmpty()) {
					// Création d'une zone de message pour le message entrant
					HBox hBox=new HBox();
					hBox.setAlignment(Pos.CENTER_RIGHT);
					hBox.setPadding(new Insets(5,5,5,10));
					
					//création d'un objet Text pour afficher le message envoyé
					Text text=new Text(messageToSend);
					//création d'un objet TextFlow pour mettre le Text dans une HBox
					TextFlow textFlow=new TextFlow(text);
					textFlow.setStyle("-fx-color:#000000;"+"-fx-background-color:#EE0077;"+"-fx-background-radius:20px;");
					text.setFill(Color.color(0.943,0.945,0.996));
					
					hBox.getChildren().add(textFlow);
					vbox_messages.getChildren().add(hBox);
					
					// Envoyer le message au serveur
					client.sendMessageToServer(messageToSend);
					
					//vider le champ texte après l'envoi du message
					tf_message.clear();
					
					
					
				}
				
			}
			
		});
		
		}
	
	//méthode pour ajouter un label avec le message reçu depuis le serveur
	public static void addLabel(String messageFromServer,VBox vbox) {
		//création d'une HBox pour afficher le message reçu
		HBox hBox=new HBox();
		hBox.setAlignment(Pos.CENTER_LEFT);
		hBox.setPadding(new Insets(5,5,5,10));
		
		//création d'un objet Text pour afficher le message reçu
		Text text=new Text(messageFromServer);
		//création d'un objet TextFlow pour mettre le Text dans une HBox
		TextFlow textFlow= new TextFlow(text);
		textFlow.setStyle("-fx-bachground-color:#EE0077;"+"-fx-background-radius:20px;");
		hBox.getChildren().add(textFlow);
		
		//mettre à jour l'interface graphique avec le nouveau message reçu de manière asynchrone sans bloquer l'interface
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				//ajoute un HBox contenant un message à la VBox
				vbox.getChildren().add(hBox);
				
			}
			
		});
	}		
	
	
	
	
	
	
	}
	

