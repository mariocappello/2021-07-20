/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.yelp.model.Model;
import it.polito.tdp.yelp.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnUtenteSimile"
    private Button btnUtenteSimile; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtX2"
    private TextField txtX2; // Value injected by FXMLLoader

    @FXML // fx:id="cmbAnno"
    private ComboBox<Integer> cmbAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="cmbUtente"
    private ComboBox<User> cmbUtente; // Value injected by FXMLLoader

    @FXML // fx:id="txtX1"
    private TextField txtX1; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	// controlli per numero Intero inserito in Stringa e su valore cmbBox
    	
    	String NrecensioniControllo =txtN.getText();
    	if(NrecensioniControllo==null) {
    		txtResult.appendText("inserire un valore");
    		return ;
    	}
    	
    	int Nrecensioni = 0;
    	try {
    		Nrecensioni = Integer.parseInt( this.txtN.getText() );
    	} catch(NumberFormatException e) {
    		this.txtResult.setText("Carattere invalido! Il valore da inserire deve essere un numero intero!");
    		return;
    	}
    	 if(Nrecensioni<0) {
    		 this.txtResult.setText("Carattere invalido! Il valore da inserire deve essere un numero intero!");
    	 }
    	 
    	 if(cmbAnno.getValue()==null) {
    		 txtResult.appendText("Inserire un valore");
     		return ;
    	 }
    	 // fine controlli sui valori
    	 
    	 
    	 int anno = Integer.parseInt(cmbAnno.getValue().toString());
    	 int recensioni = Integer.parseInt(txtN.getText().toString());
    	 model.creaGrafo(recensioni , anno);
    	 
    	 
    	 // controlli su grafo e poi stampa
    	 
    	 if(model.grafoCreato()==true) {
    		txtResult.appendText("Grafo creato!"+"\n");
     		txtResult.appendText("Vertici : " + model.getNumeroVertici()+"\n");
     		txtResult.appendText("Archi : "+model.getNumeroArchi()+"\n");
    	 }
    	 else {
    		 txtResult.setText("Grafo NON creato!");
    		 return ;
    	 }

    }

    @FXML
    void doUtenteSimile(ActionEvent event) {
    	
    	

    }
    
    @FXML
    void doSimula(ActionEvent event) {

    }
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUtenteSimile != null : "fx:id=\"btnUtenteSimile\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX2 != null : "fx:id=\"txtX2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbUtente != null : "fx:id=\"cmbUtente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX1 != null : "fx:id=\"txtX1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	cmbAnno.getItems().addAll(2005,2006,2007,2008,2009,2010,2011,2012,2013);
    }
}
