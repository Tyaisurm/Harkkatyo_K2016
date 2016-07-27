/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import static java.lang.Double.parseDouble;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import parsing.*;
import temp_storage.*;

/**
 * FXML Controller class
 *
 * @author m7942
 */
public class FXML_mainController implements Initializable {

    LogWriter lw = LogWriter.getInstance();
    Database_manager dbm = Database_manager.getInstance();
    InterfaceController ifc = InterfaceController.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tab_timotei;
    @FXML
    private Tab tab_log;
    @FXML
    private ListView<String> log_listview;
    @FXML
    private MenuItem log_savebutton;
    @FXML
    private MenuItem log_clearbutton;
    @FXML
    private WebView webview;
    @FXML
    private Button remove_map_marks;
    @FXML
    private Button database_settings_button;
    @FXML
    private ComboBox<temp_storage.Order> send_order_comb;
    @FXML
    private Button send_order_button;
    @FXML
    private Button create_order_button;
    @FXML
    private ComboBox<String> choose_area_combo;//paikkakunnan valinta
    @FXML
    private ComboBox<temp_storage.SmartPost> choose_smartpost_combo;//tietyn paikkakunnan SP:n valinta
    @FXML
    private Button add_sp_button;
    @FXML
    private Button check_order_button;
    @FXML
    private Label check_error_label;
    @FXML
    private ImageView doge_on_path;
    @FXML
    private Label label_on_path;
    @FXML
    private Label error_sp_label;
    @FXML
    private ListView<String> history_listview;
    @FXML
    private ImageView packet_on_path;

    Stage stageThis;
    @FXML
    private Button del_wh_button;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        stageThis = Mainclass.getStage();
        stageThis.setOnCloseRequest(new EventHandler() {
            @Override
            public void handle(Event event2) {
                Calendar cal = Calendar.getInstance();
                String time = cal.getTime().toString();
                File file1 = new File("TIMOTEI_AUTOLOG_" + time + ".txt");
                File file2 = new File("TIMOTEI_AUTOHISTORY_" + time + ".txt");
                try {
                    //kokeillaan kirjoittamista valittuun tiedostoon 1
                    file1.createNewFile();
                    OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(file1), "UTF-8");

                    //valitaan avoin tab ja otetaan sen sisältä TextArean sisältö muuttujaan
                    ObservableList<String> al = log_listview.getItems();
                    output.write("###T.I.M.O.T.E.I AUTOLOG_FILE###\nProgram version: v.1.01\nMaker: Jaakko Tuuri\nTime@log_file_generation: " + time + "\n\n***LOG_FILE_START***\n");
                    for (String s : al) {
                        output.write(s + "\n");
                    }
                    output.write("***LOG_FILE_END***");
                    output.close();
                    //toiseen tiedosoon...
                    file2.createNewFile();
                    OutputStreamWriter output2 = new OutputStreamWriter(new FileOutputStream(file2), "UTF-8");

                    //valitaan avoin tab ja otetaan sen sisältä TextArean sisältö muuttujaan
                    ObservableList<String> al2 = history_listview.getItems();
                    output2.write("###T.I.M.O.T.E.I AUTOHISTORY_FILE###\nProgram version: v.1.01\nMaker: Jaakko Tuuri\n\n***HISTORY_FILE_START***\n");
                    if (al2 != null) {
                        if (!al2.isEmpty()) {
                            for (String s2 : al2) {
                                String toFile = s2 + "\n";
                                output2.write(toFile);
                            }
                        }
                    }
                    output2.write("***HISTORY_FILE_END***");
                    output2.close();
                    System.out.println("SULJETAAN");
                    Platform.exit();
                    //System.exit(0);

                } catch (FileNotFoundException ex) {
                    lw.logThis("#FileNotFoundException happened during writing the log into file " + file1.toString());
                    lw.logThis("..." + ex.getMessage());
                    lw.logThis("...@" + this.getClass());
                } catch (IOException ex) {
                    lw.logThis("#IOException happened during writing contents of log into file " + file1.toString());
                    lw.logThis("..." + ex.getMessage());
                    lw.logThis("...@" + this.getClass());
                }
            }
        }
        );

        final Timeline timeline2 = new Timeline();
        timeline2.setCycleCount(Timeline.INDEFINITE);
        final KeyValue kv2 = new KeyValue(doge_on_path.rotateProperty(), 360);
        final KeyFrame kf2 = new KeyFrame(Duration.millis(7000), kv2);
        timeline2.getKeyFrames().add(kf2);
        timeline2.play();

        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        final KeyValue kv = new KeyValue(packet_on_path.rotateProperty(), -360);
        final KeyFrame kf = new KeyFrame(Duration.millis(7000), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        // TODO
        ArrayList<String> alH = dbm.getHistory();
        if (alH != null) {
            if (!alH.isEmpty()) {
                this.history_listview.getItems().addAll(alH);
            }
        }

        lw.logThis("Main program controller started...", this.log_listview);

        ifc.setOrderCombo(send_order_comb);
        ifc.setSPAreaCombo(choose_area_combo);
        ifc.setSPCombo(choose_smartpost_combo);
        ifc.setHistory(history_listview);

        webview.getEngine().load(getClass().getResource("index.html").toExternalForm());
        //ArrayList<temp_storage.SmartPost> choose_sp_al;
        ArrayList<String> choose_area_al;
        //choose_sp_al = dbm.getSPAL();
        choose_area_al = dbm.getSPAreaAL();
        //choose_smartpost_combo.getItems().addAll(choose_sp_al);
        choose_area_combo.getItems().setAll(choose_area_al);
        ArrayList<Order> al = dbm.getOrderAL();
        if(al != null){
        if (!al.isEmpty()) {
            send_order_comb.getItems().setAll(al);
        }
        }

    }

    @FXML
    private void saveLogToFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Tallenna lokitiedosto");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        Stage stage = new Stage();
        File file = fileChooser.showSaveDialog(stage);

        if (file == null) {
            lw.logThis("#Selected file error - 'file' is null!");
        } else {

            try {
                //kokeillaan kirjoittamista valittuun tiedostoon

                OutputStreamWriter output = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");

                //valitaan avoin tab ja otetaan sen sisältä TextArean sisältö muuttujaan
                ObservableList<String> al = log_listview.getItems();
                Calendar cal = Calendar.getInstance();
                output.write("###T.I.M.O.T.E.I LOG_FILE###\nProgram version: v.1.01\nMaker: Jaakko Tuuri\nTime@log_file_generation: " + cal.getTime().toString() + "\n\n***LOG_FILE_START***\n");
                for (String s : al) {
                    output.write(s + "\n");
                }
                output.write("***LOG_FILE_END***");
                output.close();

            } catch (FileNotFoundException ex) {
                lw.logThis("#FileNotFoundException happened during writing the log into file " + file.toString());
                lw.logThis("...@" + ex.getClass());
            } catch (IOException ex) {
                lw.logThis("#IOException happened during writing contents of log into file " + file.toString());
                lw.logThis("..." + ex.getMessage());
                lw.logThis("...@" + this.getClass());
            }
        }
    }

    @FXML
    private void clearLog(ActionEvent event) {
        log_listview.getItems().clear();
        lw.logThis("#Last log was cleared!");
    }

    @FXML
    private void removeMapMarks(ActionEvent event) {//EI TOIMI VIELÄ!
        //webview.getEngine().executeScript("document.createMarker(-25.363882,131.044922,'red')");
        webview.getEngine().executeScript("document.deleteMarkers()");
        this.doge_on_path.setVisible(false);
        this.packet_on_path.setVisible(false);
        this.label_on_path.setVisible(false);
        lw.logThis("Map markers removed");
    }

    @FXML
    private void openDatabaseSettings(ActionEvent event) {
        try {
            Parent root4 = FXMLLoader.load(getClass().getResource("FXML_confirmation.fxml"));
            Scene scene4 = new Scene(root4);
            Stage stage4 = new Stage();
            stage4.setScene(scene4);
            Image image4 = new Image(getClass().getResourceAsStream("/graphics/doge_icon.png"));
            stage4.getIcons().add(image4);
            stage4.setTitle("Varoitus!");
            stage4.setResizable(false);
            stage4.setMaxHeight(260);
            stage4.setMaxWidth(600);
            stage4.setMinHeight(260);
            stage4.setMinWidth(600);
            stage4.show();
        } catch (IOException ex) {
            lw.logThis("#IOExeption when trying to open 'confirmation'-window");
            lw.logThis("..." + ex.getMessage());
            lw.logThis("...@" + this.getClass());

        }
    }

    @FXML
    private void sendOrder(ActionEvent event) {
        temp_storage.Order order = send_order_comb.getSelectionModel().getSelectedItem();
        if (order == null) {
            check_error_label.setText("Valitse lähetys ensin!");
        } else {
            check_error_label.setText("");
            label_on_path.setVisible(true);
            doge_on_path.setVisible(true);
            packet_on_path.setVisible(true);

            ArrayList<temp_storage.SmartPost> al = dbm.getSendAL(order);
            WebEngine engine = this.webview.getEngine();

            SmartPost from = al.get(0);
            SmartPost to = al.get(1);

            String latFROM = from.getLat();
            String lngFROM = from.getLng();
            String colorFROM = "'F53E05'";
            String infoFROM = "'" + from.getPostDescription() + "'";

            String latTO = to.getLat();
            String lngTO = to.getLng();
            String colorTO = "'0000FF'";
            String infoTO = "'" + to.getPostDescription() + "'";
            ArrayList<Double> alPath = new ArrayList<>();
            alPath.add(parseDouble(latFROM));
            alPath.add(parseDouble(lngFROM));
            alPath.add(parseDouble(latTO));
            alPath.add(parseDouble(lngTO));

            String pathColor = "'0076A3'";
            int speedP = 0;
            //status = onko esine perillä ehjä
            boolean status = true;
            temp_storage.Details details = dbm.showOrderDetails(order);
            
            //joko menee rikki tai ei mee rikki... fifty-fifty :o
            if (order.getPackageClassID() == 1) {
                speedP = 1;
                if (details.isObjFragile()) {
                    status = false;
                } else {
                    status = true;
                }

            }
            if (order.getPackageClassID() == 2) {
                speedP = 4;
                status = true;
            }
            if (order.getPackageClassID() == 3) {
                speedP = 7;
                if (details.isObjFragile()) {
                    //jos esine on suurempi kuin kokoluokka 7, se säilyy ehjänä
                    if (details.getObjSize() > 7) {
                        status = true;
                    } else {
                        status = false;
                    }
                } else {
                    status = true;
                }
            }
            //Piirretään reitti ja lisätään pari värikästä palluraa päihin 
            engine.executeScript(
                    "document.createMarker(" + latFROM + "," + lngFROM + "," + colorFROM + "," + infoFROM + ")");
            engine.executeScript(
                    "document.createMarker(" + latTO + "," + lngTO + "," + colorTO + "," + infoTO + ")");
            engine.executeScript("document.createPath(" + alPath + "," + pathColor + "," + speedP + ")");
            lw.logThis("Order sent...");

            //status: true=ehjä, false=rikki
            int mode = 1; //mode 1 = tallenna ja poista, mode 2 = poista
            dbm.delOrder(order, status, mode);
            ArrayList<Order> alO = dbm.getOrderAL();
            ComboBox<Order> combo = ifc.getOrderCombo();
            combo.getItems().setAll(alO);
            combo.valueProperty().set(null);
            ArrayList<String> alH = dbm.getHistory();
            if (alH != null) {
                if (!alH.isEmpty()) {
                    this.history_listview.getItems().setAll(alH);
                }
            }
        }
    }

    @FXML
    private void createOrder(ActionEvent event) { // Luodaan uusi tilaus (elikkäs Order). Avataan uusi ikkuna
        try {
            ifc.setWebView(webview);
            Parent root2 = FXMLLoader.load(getClass().getResource("FXML_newPackage.fxml"));
            Scene scene2 = new Scene(root2);
            Stage stage2 = new Stage();
            stage2.setScene(scene2);
            Image image2 = new Image(getClass().getResourceAsStream("/graphics/doge_icon.png"));
            stage2.getIcons().add(image2);
            stage2.setTitle("Luo uusi lähetys");
            stage2.setResizable(false);
            stage2.setMaxHeight(400);
            stage2.setMaxWidth(600);
            stage2.setMinHeight(400);
            stage2.setMinWidth(600);
            stage2.show();
        } catch (IOException ex) {
            lw.logThis("#IOExeption when trying to open 'create order'-window");
            lw.logThis("..." + ex.getMessage());
            lw.logThis("...@" + this.getClass());
        }
    }

    @FXML
    private void addSmartPost(ActionEvent event) {//Lisätään kartalle uusi värikäs pallura halutun SmartPostin paikalle
        SmartPost sp = choose_smartpost_combo.getSelectionModel().getSelectedItem();
        if (!(sp == null)) {
            error_sp_label.setText("");
            double lat = parseDouble(sp.getLat());
            double lng = parseDouble(sp.getLng());
            String info = "'" + sp.getPostDescription() + "Auki: " + sp.getAvailability() + "'";
            String color = "'69fe75'";//tämä on vihreä
            webview.getEngine().executeScript("document.createMarker(" + lat + "," + lng + "," + color + "," + info + ")");
            lw.logThis("Map marker(SmartPost) added!");
        } else {
            error_sp_label.setText("!!!");
        }
    }

    @FXML
    private void spAreaChosen(ActionEvent event) { //Kun joku meni ja valitsi paikkakunnan
        String area = choose_area_combo.getSelectionModel().getSelectedItem();
        ArrayList<SmartPost> al = dbm.getSPAL(area);
        choose_smartpost_combo.getItems().setAll(al);
        choose_smartpost_combo.valueProperty().set(null);
    }

    @FXML
    private void checkingOrder(ActionEvent event) { //Halutaan tarkastaa valitun tilauksen tiedot (yhä edelleen, tilaus = Order = varaston paketti)
        temp_storage.Order order = send_order_comb.getSelectionModel().getSelectedItem();
        if (order == null) {
            check_error_label.setText("Valitse lähetys ensin!");
        } else {
            check_error_label.setText("");
            dbm.setShowOrderDetails(order);
            lw.logThis("Checking order details for ID: " + order.getWarehouseID() + "...");
            try {
                Parent root = FXMLLoader.load(getClass().getResource("FXML_orderDetails.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                Image image = new Image(getClass().getResourceAsStream("/graphics/doge_icon.png"));
                stage.getIcons().add(image);
                stage.setTitle("Tarkista lähetyksen tiedot");
                stage.setResizable(false);
                stage.setMaxHeight(400);
                stage.setMaxWidth(600);
                stage.setMinHeight(400);
                stage.setMinWidth(600);
                stage.show();
            } catch (IOException ex) {
                lw.logThis("#IOExeption when trying to open 'check order'-window");
                lw.logThis("..." + ex.getMessage());
                lw.logThis("...@" + this.getClass());
            }
        }

    }

    @FXML
    private void deleteWarehouse(ActionEvent event) {//Tyhjennetään varasto. Jaa miksi? Jos joku niin haluaa :x
        System.out.println("deletewarehouse kutsuttu!!");
        ObservableList<temp_storage.Order> obs = send_order_comb.getItems();
        send_order_comb.valueProperty().set(null);
        int mode = 2;
        System.out.println("Ennen looppia@deletewarehouse");
        for(temp_storage.Order order : obs){
            System.out.println("order@deletewarehouseloop "+order.getWarehouseID());
            dbm.delOrder(order, false, mode);
        }
        check_error_label.setText("Varasto tyhjennetty!");
        lw.logThis("Warehouse was cleared!");
        ArrayList<Order> al = dbm.getOrderAL();
        send_order_comb.getItems().setAll(al);
    }

}
