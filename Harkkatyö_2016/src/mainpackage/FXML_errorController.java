/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainpackage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import parsing.LogWriter;

/**
 * FXML Controller class
 *
 * TÄMÄ EI TULE OLEMAAN PAKOLLINEN!!!
 * TÄMÄN SIJAAN VOISI KÄYTTÄÄ "Alert"-luokkaa, tai mahdollisesti 3rd party JavaFX CONTROLLER
 * 
 * Käytettiin silti :3
 * 
 * @author m7942
 */
public class FXML_errorController implements Initializable {
    //Tämä kutsutaan kun halutaan näyttää käyttäjälle virheilmoitus-popup
    //Siis, kyseessä on itkevä doge joka pelkää tietokannan menevän sekaisin
    //...poor thing :c
    
    LogWriter lw = LogWriter.getInstance();
    
    @FXML
    private Button ok_button;
    @FXML
    private AnchorPane anchorpane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void pressedOK(ActionEvent event) {
        Stage stage = (Stage) ok_button.getScene().getWindow();
        stage.close();
    }
    
}
