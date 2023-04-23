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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ResourceBundle;

import boutique.Article;
import boutique.Categorie;

public class articleController implements Initializable{
	//la chaine qui se référer à une requête  
	public String query;
	
    @FXML
    private TextField idAField;
    
    @FXML
    private TextField marqueField;

    @FXML
    private TextField stockField;

    @FXML
    private TextField prixField;

    @FXML
    private ComboBox comboBox;
    
    @FXML
    private TextField taillePointureField;

    @FXML
    private Button ajouter;

    @FXML
    private Button mettreAJour;

    @FXML
    private Button supprimer;

    @FXML
    private TableView TableView;
    
    @FXML
    private TableColumn<Article, Integer> idAColumn;
    
    @FXML
    private TableColumn<Article, String> marqueColumn;

    @FXML
    private TableColumn<Article, Integer> stockColumn;

    @FXML
    private TableColumn<Article, Float> prixColumn;

    @FXML
    private TableColumn<Article, String> categorieColumn;

    @FXML
    private TableColumn<Article, Integer> taillePointureColumn;
    
    
    // Cette méthode est appelée lorsque le bouton "insert" est cliqué
    @FXML
    private void ajouter() throws ClassNotFoundException, SQLException, IllegalArgumentException {
    	query = "insert into article values("+idAField.getText()+
    			",'"+marqueField.getText()+"',"+stockField.getText()+","+prixField.getText()+
    			",'"+comboBox.getValue()+"',"+taillePointureField.getText()+")";
    	executeQuery(query);
    	
    	// Cette méthode met à jour la table des articles affichée
    	showArticle();
    }
    
    // Cette méthode est appelée lorsque le bouton "modify" est cliqué
    @FXML 
    private void mettreAJour() throws ClassNotFoundException, SQLException {
    String query = "UPDATE article SET marque='"+marqueField.getText()+"', stock="+stockField.getText()+", prix="+prixField.getText()+", categorie='"+comboBox.getValue()+
    		"', tailleOuPointure="+taillePointureField.getText()+" WHERE idA="+idAField.getText()+"";
    executeQuery(query);
	
    showArticle();
    }
    
    // Cette méthode est appelée lorsque le bouton "delete" est cliqué
    @FXML
    private void supprimer() throws ClassNotFoundException, SQLException {
    	String query = "DELETE FROM article WHERE idA ="+idAField.getText()+"";
    	executeQuery(query);
    	
    	showArticle();
    }
    
    // Cette méthode est appelée lorsque l'utilisateur clique sur le bouton "add category"
    @FXML
	public void CategorieVue(ActionEvent e)  {
		BorderPane root;
		try {
			root = (BorderPane) FXMLLoader.load(getClass().getResource("CategorieView.fxml"));
			Stage stage= (Stage) ((Node) e.getSource()).getScene().getWindow();
			Scene scene = new Scene(root,450,660);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e1) {
			System.out.println("erreur categorie view");
		}
		
	}
    
    // Cette méthode est appelée lorsque l'utilisateur clique sur le bouton "messages"
    @FXML
	public void ServerView(ActionEvent e) {
		AnchorPane root;
		try {
			root = FXMLLoader.load(getClass().getResource("ServerView.fxml"));
			Stage stage= (Stage) ((Node) e.getSource()).getScene().getWindow();
			Scene scene = new Scene(root,478,396);
			stage.setScene(scene);
			stage.show();
		} catch (Exception e1) {
			System.out.println("erreur server view");
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
 @Override
    public void initialize(URL location, ResourceBundle resources) {
	 	Connection conn=null;
		String drivername="com.mysql.jdbc.Driver";
			showArticle();
	        try {
	        	Class.forName(drivername);
	            conn =DriverManager.getConnection("jdbc:mysql://localhost/projet","root","");
	            ResultSet rs = conn.createStatement().executeQuery("SELECT nom FROM categorie;");
		        while (rs.next()) {  // loop
		            comboBox.getItems().addAll(rs.getString("nom")); // ajouter les noms de categorie dans la liste déroulante
		       }
	        }    	
	        catch (Exception e){
	        	System.out.println("erreur connection");
	    		return;
	    	}
    }
    public Connection getConnection() {
    	Connection conn=null;
		String drivername="com.mysql.jdbc.Driver";
    	try {
    		Class.forName(drivername);
    		conn = DriverManager.getConnection("jdbc:mysql://localhost/projet","root","");
    		return conn;
    	}
    	catch (Exception e){
    		System.out.println("erreur connection");
    		return null;
    	}
    }
    
    public ObservableList<Article> getarticleList(){
    	ObservableList<Article> data = FXCollections.observableArrayList();
    	Connection connection = getConnection();
    	String query = "SELECT * FROM article ";
    	Statement st;
    	ResultSet rs;
    	
    	try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
	    	  while (rs.next()) {
	    	      Article article = new Article();
	    	   // définir les champs de l'article
	    	      article.idA.set(rs.getInt("idA"));
	    	      article.marque.set(rs.getString("marque"));
	    	      article.stock.set(rs.getInt("stock"));
	    	      article.prix.set(rs.getInt("prix"));
	    	      article.categorie.set(rs.getString("categorie"));
	    	      article.tailleOuPointure.set(rs.getInt("tailleOuPointure"));
	    	      data.add(article);
	    	  }
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return (ObservableList<Article>) data;
    }
    
    public void showArticle() {
    	ObservableList<Article> list = getarticleList(); // récupérer la liste des articles
    	
    	// associé colonne à un champ de l'objet Article correspondant
    	idAColumn.setCellValueFactory(new PropertyValueFactory<Article,Integer>("idA"));
    	marqueColumn.setCellValueFactory(new PropertyValueFactory<Article,String>("marque"));
    	stockColumn.setCellValueFactory(new PropertyValueFactory<Article,Integer>("stock"));
    	prixColumn.setCellValueFactory(new PropertyValueFactory<Article,Float>("prix"));
    	categorieColumn.setCellValueFactory(new PropertyValueFactory<Article,String>("categorie"));
    	taillePointureColumn.setCellValueFactory(new PropertyValueFactory<Article,Integer>("tailleOuPointure"));

    	//associe la liste des articles au TableView
    	TableView.setItems(list);
    }

}