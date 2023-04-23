package application;
	
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("ArticleView.fxml"));
			Scene scene = new Scene(root,1200,700);
			primaryStage.setOnShown(e1 -> primaryStage.getIcons().add(new Image("C:\\Users\\echra\\OneDrive\\Bureau\\finalllll\\icone.jpg")));
			primaryStage.setResizable(false);
			primaryStage.setTitle("Server");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}