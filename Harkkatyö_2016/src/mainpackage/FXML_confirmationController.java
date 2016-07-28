/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import parsing.Database_manager;
import parsing.LogWriter;

/**
 * FXML Controller class
 *
 * @author m7942
 */
public class FXML_confirmationController implements Initializable {
    //tämä otus kutsutaan kun halutaan varmistaa että käyttäjä tietää mitä
    //tekee. Eli, jotain voi mennä kohta rikki :) (Avaa tietokanta-asetukset)

    LogWriter lw = LogWriter.getInstance();
    Database_manager dbm = Database_manager.getInstance();

    @FXML
    private Button confirm_no_button;
    @FXML
    private Button confirm_yes_button;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void confirmNO(ActionEvent event) {//ei tiedä mitä tekee
                                               //(knew it!)
        Stage stage = (Stage) confirm_no_button.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void confirmYES(ActionEvent event) {//tietää mitä tekee(toivottavasti D:)
        try {
            if (dbm.isWarehouseEmpty()) {
                Parent root3;
                Stage stage3 = new Stage();
                root3 = FXMLLoader.load(getClass().getResource("FXML_error.fxml"));
                Scene scene3 = new Scene(root3);
                stage3.setScene(scene3);
                stage3.setResizable(false);
                Image image3 = new Image(getClass().getResourceAsStream("/graphics/doge_icon.png"));
                stage3.getIcons().add(image3);
                stage3.setTitle("Doge on surullinen :'C");
                stage3.setMaxHeight(211);
                stage3.setMaxWidth(470);
                stage3.setMinHeight(211);
                stage3.setMinWidth(470);
                stage3.show();

            } else {
                lw.logThis("Changing database settings!");
                Parent root4 = FXMLLoader.load(getClass().getResource("FXML_settings.fxml"));
                Scene scene4 = new Scene(root4);
                Stage stage4 = new Stage();
                stage4.setScene(scene4);
                Image image4 = new Image(getClass().getResourceAsStream("/graphics/doge_icon.png"));
                stage4.getIcons().add(image4);
                stage4.setTitle("Tietokanta-asetukset");
                stage4.setResizable(false);
                stage4.setMaxHeight(263);
                stage4.setMaxWidth(635);
                stage4.setMinHeight(263);
                stage4.setMinWidth(635);
                stage4.show();
            }

            Stage stage = (Stage) confirm_yes_button.getScene().getWindow();
            stage.close();
        } catch (IOException ex) {
            lw.logThis("#IOExeption when trying to open 'database settings'-window");
            lw.logThis("..." + ex.getMessage());
            lw.logThis("...@" + this.getClass());

        }
    }

}
