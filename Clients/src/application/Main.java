package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,478,396);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setOnShown(e1 -> primaryStage.getIcons().add(new Image("C:\\Users\\echra\\OneDrive\\Bureau\\finalllll\\disc.png")));
			primaryStage.setResizable(false);
			primaryStage.setTitle("Client");
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
