package application;

import java.io.IOException;
import java.net.ServerSocket;
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
	
	
	private Server server;
	
	//créer un objet Server et ajoute un écouteur de changement de hauteur à la zone de messages (VBox) pour permettre le défilement automatique
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//initialisation de l'objet Server
		server= new Server();
		
	//ajout d'un écouteur pour le changement de hauteur de la VBox contenant les messages
	vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			//changer la valeur de la position verticale de ScrollPane en fonction de la hauteur de la VBox
			sp_main.setVvalue((Double) newValue);
			
		}
		
	});
	
	//démarrer la réception de messages des clients en passant la VBox des messages en paramètre
	server.receiveMessageFromClient(vbox_messages);
	
	//définir l'action à exécuter lors du clic sur le bouton "Envoyer"
	button_send.setOnAction(new EventHandler<ActionEvent>(){

		@Override
		public void handle(ActionEvent arg0) {
			//récupérer le message saisi dans le champ texte
			String messageToSend=tf_message.getText();
			if(!messageToSend.isEmpty()) {
				//création d'une HBox pour afficher le message envoyé
				HBox hBox=new HBox();
				hBox.setAlignment(Pos.CENTER_RIGHT);
				hBox.setPadding(new Insets(5,5,5,10));
				
				//création d'un objet Text pour afficher le message envoyé
				Text text=new Text(messageToSend);
				//création d'un objet TextFlow pour mettre le Text dans une HBox
				TextFlow textFlow=new TextFlow(text);
				textFlow.setStyle("-fx-color:#EEEDFF;"+"-fx-background-color:#929BF8;"+"-fx-background-radius:20px;");
				text.setFill(Color.color(0.943,0.945,0.996));
				
				//ajouter TextFlow à la HBox
				hBox.getChildren().add(textFlow);
				vbox_messages.getChildren().add(hBox);
				
				//envoyer le message au client
				server.sendMessageToClient(messageToSend);
				
				//vider le champ texte après l'envoi du message
				tf_message.clear();
				
				
				
			}
			
		}
		
	});
	
	}
	
	//méthode pour ajouter un label avec le message reçu depuis un client
	public static void addLabel(String messageFromClient,VBox vbox) {
		//création d'une HBox pour afficher le message reçu
		HBox hBox=new HBox();
		hBox.setAlignment(Pos.CENTER_LEFT);
		hBox.setPadding(new Insets(10,10,10,10));
		
		//création d'un objet Text pour afficher le message reçu
		Text text=new Text(messageFromClient);
		//création d'un objet TextFlow pour mettre le Text dans une HBox
		TextFlow textFlow= new TextFlow(text);
		textFlow.setStyle("-fx-background-color:#E9E9EB;"+"-fx-background-radius:20px;");
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