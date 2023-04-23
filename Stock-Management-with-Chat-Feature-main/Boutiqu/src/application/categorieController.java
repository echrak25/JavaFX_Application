package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import boutique.Categorie;

public class categorieController{
	

	    @FXML
	    private TextField idCField;

	    @FXML
	    private TextField nomField;

	    @FXML
	    private TextField notesField;

	    @FXML
	    private Button ajouterButton;

	    @FXML
	    private Button mettreAJourButton;

	    @FXML
	    private Button supprimerButton;
	    
	    @FXML
	    private Button button;

	    @FXML
	    private TableView TableView;

	    @FXML
	    private TableColumn<Categorie, Integer> idCColumn;

	    @FXML
	    private TableColumn<Categorie, String> nomColumn;

	    @FXML
	    private TableColumn<Categorie, String> notesColumn;
	    
	    @FXML
	    public void initialize() 
	    {
			showCategorie();
	    }
	    
	    // Cette méthode est appelée lorsque le bouton "insert" est cliqué
	    @FXML
	    private void ajouter() throws ClassNotFoundException, SQLException {
	        String query = "INSERT INTO categorie VALUES(" + idCField.getText() + ",'" + nomField.getText() + "','" + notesField.getText() + "')";
	        executeQuery(query);
	        
	        //mettre à jour la vue
	        showCategorie();
	    }

	    // Cette méthode est appelée lorsque le bouton "modify" est cliqué
	    @FXML
	    private void mettreAJour() throws ClassNotFoundException, SQLException {
	        String query = "UPDATE categorie SET nom='" + nomField.getText() + "',notes='" + notesField.getText() + "' WHERE idC=" + idCField.getText() + "";
	        executeQuery(query);
	        
	        showCategorie();
	    }

	    // Cette méthode est appelée lorsque le bouton "delete" est cliqué
	    @FXML
	    private void supprimer() throws ClassNotFoundException, SQLException {
	        String query = "DELETE FROM categorie WHERE idC =" + idCField.getText() + "";
	        executeQuery(query);
	        
	        showCategorie();
	    }
	    
	    // Cette méthode est appelée lorsque l'utilisateur clique sur le bouton "article"
	    @FXML
		public void ArticleView(ActionEvent e)  {
			BorderPane root;
			try {
				root = (BorderPane) FXMLLoader.load(getClass().getResource("ArticleView.fxml"));
				Stage stage= (Stage) ((Node) e.getSource()).getScene().getWindow();
				Scene scene = new Scene(root,1200,700);
				stage.setScene(scene);
				stage.show();
			} catch (Exception e1) {
				System.out.println("erreur article view");
			}
			
		}
	    
	    public void executeQuery(String query) {
	    	Connection conn = getConnection(); // se connecter à la base de données
	    	Statement st;
	    	try {
				st = conn.createStatement(); // créer une instruction SQL
				st.executeUpdate(query); // exécuter la requête
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }

	    public Connection getConnection() {
	    	Connection conn=null;
			String drivername="com.mysql.jdbc.Driver";
	        try {
	        	Class.forName(drivername);
	            conn = DriverManager.getConnection("jdbc:mysql://localhost/projet", "root", "");
	            return conn;
	        } catch (Exception e) {
	            System.out.println("erreur connection");
	            return null;
	        }
	    }

	    public ObservableList<Categorie> getCategorieList() {
	    	ObservableList<Categorie> data = FXCollections.observableArrayList();
	        Connection connection = getConnection();
	        String query = "SELECT * FROM categorie ";
	        Statement st;
	        ResultSet rs;

	        try {
	            st = connection.createStatement();
	            rs = st.executeQuery(query);
	            while (rs.next()) {
	            	Categorie categorie = new Categorie();
	            	// définir les champs du categorie
		    	      categorie.idC.set(rs.getInt("idC"));
		    	      categorie.nom.set(rs.getString("nom"));
		    	      categorie.notes.set(rs.getString("notes"));
		    	      data.add(categorie);
            }
        } catch (Exception e) {
        	System.out.println("erreur connection");
            e.printStackTrace();
        }
        return (ObservableList<Categorie>) data;
    }

	    //afficher les catégories dans la TableView
	    public void showCategorie() {
	    	ObservableList<Categorie> list = getCategorieList(); // récupérer la liste des categories
	    	
	    	idCColumn.setCellValueFactory(new PropertyValueFactory<Categorie,Integer>("idC"));
	    	nomColumn.setCellValueFactory(new PropertyValueFactory<Categorie,String>("nom"));
	    	notesColumn.setCellValueFactory(new PropertyValueFactory<Categorie,String>("notes"));
			
	    	//associe la liste des categories au TableView
	    	TableView.setItems(list);
	    }

}
