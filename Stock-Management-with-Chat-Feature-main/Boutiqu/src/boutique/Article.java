package boutique;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Article {

    public IntegerProperty idA = new SimpleIntegerProperty(); 
    public StringProperty marque = new SimpleStringProperty();
    public IntegerProperty stock = new SimpleIntegerProperty();
    public IntegerProperty prix = new SimpleIntegerProperty();
    public StringProperty categorie= new SimpleStringProperty();
    public IntegerProperty tailleOuPointure = new SimpleIntegerProperty();

    public IntegerProperty idAProperty() { 
        return idA;
    }
    public StringProperty marqueProperty() {
        return marque;
    }

    public IntegerProperty stockProperty() {
        return stock;
    }
    

    public IntegerProperty prixProperty() {
        return prix;
    }


    public StringProperty categorieProperty() {
        return categorie;
    }
    
    public IntegerProperty tailleOuPointureProperty() {
        return tailleOuPointure;
    }
    
	public void setIdA(IntegerProperty idA) {
		this.idA = idA;
	}
	public void setMarque(StringProperty marque) {
		this.marque = marque;
	}
	public void setStock(IntegerProperty stock) {
		this.stock = stock;
	}
	public void setPrix(IntegerProperty prix) {
		this.prix = prix;
	}
	public void setCategorie(StringProperty categorie) {
		this.categorie = categorie;
	}
	public void setTailleOuPointure(IntegerProperty tailleOuPointure) {
		this.tailleOuPointure = tailleOuPointure;
	}
	

    public Article(int idAValue, String marqueValue, int stockValue, int prixValue, String categoryValue, int tailleOuPointureValue) {
        idA.set(idAValue);
        marque.set(marqueValue);
        stock.set(stockValue);
        prix.set(stockValue);
        categorie.set(categoryValue);
        tailleOuPointure.set(tailleOuPointureValue);

    }

    public Article(){}
}

