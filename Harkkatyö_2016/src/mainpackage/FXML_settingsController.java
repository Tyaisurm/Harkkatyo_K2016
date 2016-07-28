/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;

import background.SmartPostDefBG;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import parsing.Database_manager;
import parsing.InterfaceController;
import background.LoaderBG;
import parsing.LogWriter;

/**
 * FXML Controller class
 *
 * @author m7942
 */
public class FXML_settingsController implements Initializable {
    //Tämä kutsutaan kun halutaan muokata tietokannan asetuksia

    LogWriter lw = LogWriter.getInstance();
    InterfaceController ifc = InterfaceController.getInstance();
    Database_manager dbm = Database_manager.getInstance();

    @FXML
    private Button default_button;
    @FXML
    private Button SP_reset_button;
    @FXML
    private Button exterminate_button;
    @FXML
    private Button delete_selected_object_button;
    @FXML
    private ComboBox<temp_storage.Object> object_combo;
    @FXML
    private Label exterminated_label;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<temp_storage.Object> objects_al;
        objects_al = dbm.getObjectAL();
        object_combo.getItems().setAll(objects_al);

        // TODO
    }

    @FXML
    private void restoreDefaultState(ActionEvent event) {//palautetaan tietokanta oletustilaan
        exterminated_label.setText("");
        lw.logThis("Restoring database default state...");
        Stage stage = new Stage();
        Task task = new LoaderBG();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("FXML_loading.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            Image image = new Image(getClass().getResourceAsStream("/graphics/doge_icon.png"));
            stage.getIcons().add(image);
            stage.setTitle("WOW!");
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            //stage3.setFullScreen(true);
            stage.show();
        } catch (IOException ex) {
            lw.logThis("#IOException when opening FXML_loading.fxml!");
            lw.logThis("..." + ex.getMessage());
            lw.logThis("...@" + this.getClass());
        }
        task.setOnSucceeded((Event event1) -> {//taustaprosessi onnistui
            stage.close();
            lw.logThis("Database default state restored!");
            object_combo.valueProperty().set(null);
            ArrayList<temp_storage.Object> objects_al;
            objects_al = dbm.getObjectAL();
            object_combo.getItems().setAll(objects_al);

            ComboBox<temp_storage.SmartPost> spcombo = ifc.getSPCombo();
            ComboBox<String> areacombo = ifc.getSPAreaCombo();
            ComboBox<temp_storage.Order> ordercombo = ifc.getOrderCombo();
            ordercombo.valueProperty().set(null);
            spcombo.valueProperty().set(null);
            areacombo.valueProperty().set(null);
            spcombo.getItems().clear();

            ArrayList<String> choose_area_al;
            choose_area_al = dbm.getSPAreaAL();
            areacombo.getItems().setAll(choose_area_al);
            ArrayList<String> alH = dbm.getHistory();
            ListView<String> historylist = ifc.getHistory();
            if (alH != null) {
                if (!alH.isEmpty()) {
                    historylist.getItems().setAll(alH);
                }
            }
            

        });
        task.setOnFailed((Event event2) -> {//taustaprosessi meni puihin
            stage.close();
            lw.logThis("#Database default state setup failed!");
        });
        Thread thread = new Thread(task);
        thread.start();

    }

    @FXML
    private void resetSmartPostData(ActionEvent event) {//ladataan uusiksi vain SP-tietokanta

        Task task = new SmartPostDefBG();
        lw.logThis("Deleting and redownloading SmartPost data...");
        Stage stage = new Stage();
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getResource("FXML_loading.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            Image image = new Image(getClass().getResourceAsStream("/graphics/doge_icon.png"));
            stage.getIcons().add(image);
            stage.setTitle("WOW!");
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            //stage3.setFullScreen(true);
            stage.show();
        } catch (IOException ex) {
            lw.logThis("#IOException when opening FXML_loading.fxml!");
            lw.logThis("..." + ex.getMessage());
            lw.logThis("...@" + this.getClass());
        }
        task.setOnSucceeded((Event event1) -> {//taustaprosessi onnistui
            stage.close();
            lw.logThis("SmartPost data refreshed!");

            ComboBox<temp_storage.SmartPost> spcombo = ifc.getSPCombo();
            ComboBox<String> areacombo = ifc.getSPAreaCombo();
            spcombo.valueProperty().set(null);
            areacombo.valueProperty().set(null);
            spcombo.getItems().clear();

            ArrayList<String> choose_area_al;
            choose_area_al = dbm.getSPAreaAL();
            areacombo.getItems().setAll(choose_area_al);            

        });
        task.setOnFailed((Event event2) -> {//taustaprosessi meni puihin
            stage.close();
            lw.logThis("#SmartPost data setup failed!");
        });
        Thread thread = new Thread(task);
        thread.start();
    }

    @FXML
    private void exterminate_database(ActionEvent event) {
        //Kas niin, tämä on täysin turha(vaarallinen) nappi joka rikkoo koko ohjelman tuhoamalla DB:n
        //alkutekijöihinsä. "Nuke"-nappi, jos niin voisi sanoa.
        lw.logThis("Exterminating database...");
        try {
            BufferedWriter out;
            out = new BufferedWriter(new FileWriter("DB.sqlite"));
            out.write("");
            out.close();
            exterminated_label.setText("Kaikki hajos ny!");
            lw.logThis("DATABASE HAS BEEN EXTERMINATED!!!");
            // Stage stageThis = (Stage) default_button.getScene().getWindow();
            //stageThis.close(); TÄTÄ EI KÄYTETÄ
        } catch (IOException ex) {
            lw.logThis("#IOException happened when trying to delete database...");
        }
    }

    @FXML
    private void removeSelectedObject(ActionEvent event) {//poistetaan valittu esine tietokannasta
        temp_storage.Object object = object_combo.getSelectionModel().getSelectedItem();
        object_combo.valueProperty().set(null);
        dbm.delObject(object);
        ArrayList<temp_storage.Object> objects_al;
        objects_al = dbm.getObjectAL();
        object_combo.getItems().setAll(objects_al);
    }

}
