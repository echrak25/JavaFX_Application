package boutique;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Categorie {
	public IntegerProperty idC = new SimpleIntegerProperty(); //variable names should be exactly as column names in SQL Database Table. In case if you want to use <int> type instead of <IntegerProperty>, then you need to use getter/setter procedures instead of xxxProperty() below
    public StringProperty nom = new SimpleStringProperty();
    public StringProperty notes = new SimpleStringProperty();
    
    public IntegerProperty idCProperty() { //name should be exactly like this [IntegerProperty variable name (idA) + (Property) = idAProperty] (case sensitive)
        return idC;
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public StringProperty notesProperty() {
        return notes;
    }

	public void setIdC(IntegerProperty idC) {
		this.idC = idC;
	}

	public void setNom(StringProperty nom) {
		this.nom = nom;
	}

	public void setNotes(StringProperty notes) {
		this.notes = notes;
	}

	public Categorie(int idCValue, String nomValue,  String notesValue) {
        idC.set(idCValue);
        nom.set(nomValue);
        notes.set(notesValue);
    }

    public Categorie(){}
 }

