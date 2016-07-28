/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;

import static java.lang.Double.parseDouble;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import parsing.*;
import temp_storage.*;

/**
 * FXML Controller class
 *
 * @author m7942
 */
public class FXML_newPackageController implements Initializable {
    //tämä kutsutaan, kun halutaan luoda uusi paketti. Tämän ikkunan sisällä on
    //mahdollista luoda sekä uusia paketteja, että esineitä.

    LogWriter lw = LogWriter.getInstance();
    Database_manager dbm = Database_manager.getInstance();
    InterfaceController ifc = InterfaceController.getInstance();

    @FXML
    private ComboBox<temp_storage.Object> choose_object_combo;
    @FXML
    private TextField weight_input;
    @FXML
    private TextField name_input;
    @FXML
    private Button create_object_button;
    @FXML
    private ComboBox<temp_storage.Package> choose_class_combo;
    @FXML
    private ComboBox<temp_storage.SmartPost> order_origin_combo;
    @FXML
    private ComboBox<temp_storage.SmartPost> order_target_combo;
    @FXML
    private Button confirm_order_button;
    @FXML
    private CheckBox fragile_choice;
    @FXML
    private TextArea description_input;
    @FXML
    private ComboBox<Integer> choose_size_class_combo;
    @FXML
    private Label object_name_label;
    @FXML
    private Label object_description_label;
    @FXML
    private ComboBox<String> from_city_combo;
    @FXML
    private ComboBox<String> to_city_combo;
    @FXML
    private Label error_prompt_label;
    @FXML
    private Label size_class_restrict_label;
    @FXML
    private Label travel_restrict_label;

    private WebView wv;
    @FXML
    private Label travel_label;
    @FXML
    private Label size_class_label;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        wv = ifc.getWebView();
        ArrayList<String> choose_area_al = dbm.getSPAreaAL();
        ArrayList<temp_storage.Object> objects_al;
        ArrayList<temp_storage.Package> packageType_al;

        ArrayList<Integer> sizeClass_al = new ArrayList<>();
        for (int a = 1; a < 11; a++) {
            sizeClass_al.add(a);
        }
        objects_al = dbm.getObjectAL();
        packageType_al = dbm.getPackageAL();

        choose_object_combo.getItems().setAll(objects_al);
        choose_class_combo.getItems().setAll(packageType_al);
        choose_size_class_combo.getItems().setAll(sizeClass_al);

        from_city_combo.getItems().setAll(choose_area_al);
        to_city_combo.getItems().setAll(choose_area_al);

        // TODO
    }

    @FXML
    private void createObject(ActionEvent event) {//kaikissa syötteissä max 255 merkkiä, paino on REAL, max 2000
        try {                                     //Tässä luodaan uusi esine tietokantaan.
            double weight = parseDouble(weight_input.getText());
            String name = name_input.getText();
            boolean fragile = fragile_choice.isSelected();
            String description = description_input.getText();
            int size_class = choose_size_class_combo.getSelectionModel().getSelectedItem();
            if (weight <= 2000) {//painoraja 2000kg
                if (name.length() <= 255) {
                    if (description.length() <= 255) {
                        if ((1 <= size_class) && (size_class <= 10)) {
                            dbm.setObject(description, weight, size_class, name, fragile, null);
                            error_prompt_label.setText("");
                            choose_object_combo.getItems().setAll(dbm.getObjectAL());
                            name_input.clear();
                            weight_input.clear();
                            fragile_choice.setSelected(false);
                            description_input.clear();
                            choose_size_class_combo.getSelectionModel().clearSelection();
                        }
                    } else {
                        error_prompt_label.setText("Syöttöraja on 255 merkkiä!");
                    }
                } else {
                    error_prompt_label.setText("Syöttöraja on 255 merkkiä!");
                }
            } else {
                error_prompt_label.setText("Painoraja on 2000!");
            }
        } catch (NullPointerException ex) {
            error_prompt_label.setText("Virheellisiä syötteitä!");
        } catch (NumberFormatException ex) {
            error_prompt_label.setText("Virheellisiä syötteitä!");
        }
        //tarkistettu syötettyjen arvojen oikeellisuus...
    }

    @FXML
    private void createOrder(ActionEvent event) {//kun luodaan uusi paketti
        ArrayList<String> gps = new ArrayList<>();
        temp_storage.Object obj = choose_object_combo.getSelectionModel().getSelectedItem();
        if (obj != null) {
            SmartPost spFROM = order_origin_combo.getSelectionModel().getSelectedItem();
            if (spFROM != null) {
                SmartPost spTO = order_target_combo.getSelectionModel().getSelectedItem();
                if (spTO != null) {
                    if (spFROM.getPostID() != spTO.getPostID()) {//tarkistetaan että ei sama lähtö-ja kohdeosoite
                        temp_storage.Package pck = choose_class_combo.getSelectionModel().getSelectedItem();
                        if (pck != null) {
                            if (pck.getSizeLimit() >= obj.getSize()) {
                                gps.add(spFROM.getLat());
                                gps.add(spFROM.getLng());
                                gps.add(spTO.getLat());
                                gps.add(spTO.getLng());
                                double distance = (double) wv.getEngine().executeScript("document.getPathLenght(" + gps + ")");

                                if ((distance < pck.getTravelLimit()) || pck.getTravelLimit() == -1.0) {
                                    travel_label.setText(Double.toString(distance));
                                    int packageClassID = pck.getClassID();
                                    int objectID = obj.getObjectID();
                                    int toPostID = spTO.getPostID();
                                    int fromPostID = spFROM.getPostID();
                                    dbm.setOrder(null, objectID, packageClassID, toPostID, fromPostID);
                                    ArrayList<Order> al = dbm.getOrderAL();
                                    ComboBox<Order> combo = ifc.getOrderCombo();
                                    
                                    combo.getItems().setAll(al);
                                    error_prompt_label.setText("Uusi paketti luotu!");
                                } else {
                                    travel_label.setText(Double.toString(distance));
                                    error_prompt_label.setText("Kuljetusmatka liian pitkä!");
                                }
                            } else {
                                error_prompt_label.setText("Esine on liian iso!");
                            }
                        } else {
                            error_prompt_label.setText("Valitse Pakettiluokka!");
                        }
                    } else {
                        error_prompt_label.setText("Samat FROM ja TO!");
                    }
                } else {
                    error_prompt_label.setText("Valitse TO!");
                }
            } else {
                error_prompt_label.setText("Valitse FROM!");
            }
        } else {
            error_prompt_label.setText("Valitse esine!");
        }
        //tarkistetaan että on syötetty kaikki arvot...
    }

    @FXML
    private void objectChosen(ActionEvent event) {//kun on valittu esine, näytetään tiedot
        temp_storage.Object obj = choose_object_combo.getSelectionModel().getSelectedItem();
        object_name_label.setText(obj.getName());
        object_description_label.setText(obj.getDescription());
        size_class_label.setText(Integer.toString(obj.getSize()));

    }

    @FXML
    private void fromCityChosen(ActionEvent event) {//näytetään valitun paikkakunnan SPt
        String area = from_city_combo.getSelectionModel().getSelectedItem();
        ArrayList<SmartPost> al = dbm.getSPAL(area);
        order_origin_combo.getItems().setAll(al);
        order_origin_combo.valueProperty().set(null);
    }

    @FXML
    private void toCityChose(ActionEvent event) {//näytetään valitun paikkakunnan SPt
        String area = to_city_combo.getSelectionModel().getSelectedItem();
        ArrayList<SmartPost> al = dbm.getSPAL(area);
        order_target_combo.getItems().setAll(al);
        order_target_combo.valueProperty().set(null);
    }

    @FXML
    private void classChosen(ActionEvent event) {//näytetään valitun pakettiluokan tiedot
        temp_storage.Package pck = choose_class_combo.getSelectionModel().getSelectedItem();
        String travellimit = Double.toString(pck.getTravelLimit());
        String sizelimit = Integer.toString(pck.getSizeLimit());
        size_class_restrict_label.setText(sizelimit);
        travel_restrict_label.setText(travellimit);
    }

}
