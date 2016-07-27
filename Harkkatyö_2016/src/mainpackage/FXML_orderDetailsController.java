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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import parsing.*;
import temp_storage.*;

/**
 * FXML Controller class
 *
 * @author m7942
 */
public class FXML_orderDetailsController implements Initializable {

    LogWriter lw = LogWriter.getInstance();
    Database_manager dbm = Database_manager.getInstance();

    @FXML
    private Button ok_button;
    @FXML
    private Label objID_label;
    @FXML
    private Label whID_label;
    @FXML
    private Label objWeight_label;
    @FXML
    private Label objFragile_label;
    @FXML
    private Label objDesc_label;
    @FXML
    private Label pckType_label;
    @FXML
    private Label pckDesc_label;
    @FXML
    private Label fromPost_label;
    @FXML
    private Label toPost_label;
    @FXML
    private Label objName_label;
    @FXML
    private Label objSize_label;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        temp_storage.Order order = dbm.getShowOrderDetails();
        temp_storage.Details details = dbm.showOrderDetails(order);
        objID_label.setText(Integer.toString(details.getObjID()));
        whID_label.setText(Integer.toString(details.getWhID()));
        objWeight_label.setText(Double.toString(details.getObjWeight()));
        objSize_label.setText(Integer.toString(details.getObjSize()));
        if (details.isObjFragile()) {
            objFragile_label.setText("Kyllä");
        } else {
            objFragile_label.setText("Ei");
        }
        objName_label.setText(details.getObjName());
        objDesc_label.setText(details.getObjDesc());
        pckDesc_label.setText(details.getPckDesc());
        pckType_label.setText(Integer.toString(details.getPckType()));
        fromPost_label.setText(details.getFromPost());
        toPost_label.setText(details.getToPost());

    }

    @FXML
    private void okClicked(ActionEvent event) {
        Stage stage = (Stage) ok_button.getScene().getWindow();
        stage.close();
    }

}
