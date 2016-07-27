/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainpackage;

import background.LoaderBGW;
import background.LoginBG;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import parsing.Database_manager;
import parsing.LogWriter;

/**
 * FXML Controller class
 *
 * @author m7942
 */
public class FXML_loginController implements Initializable {

    LogWriter lw = LogWriter.getInstance();
    Database_manager dbm = Database_manager.getInstance();

    @FXML
    private ImageView timotei_BG;
    @FXML
    private ImageView timotei_doge;
    @FXML
    private ImageView timotei_loading;
    @FXML
    private TextField username_field;
    @FXML
    private TextField password_field;
    @FXML
    private Button login_button;
    @FXML
    private Label timotei_loadinglabel;
    @FXML
    private ProgressBar timotei_progressbar;
    private static double progress = 0;
    @FXML
    private CheckBox database_choice;

    private int luku = 0;
    @FXML
    private Label error_prompt_label;
    @FXML
    private CheckBox warehouse_choice;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void login_button_pressed(ActionEvent event) throws IOException {
        if (!username_field.getText().equals("admin") || (!password_field.getText().equals("password"))) {
            //jos käyttäjänimi ja salasana ei toimi
            luku++;
            lw.logThis("Login attempt: " + luku + "...FAILED!");
            lw.logThis("...by username: " + username_field.getText());
            error_prompt_label.setText("Virheelliset tiedot!");
        } else {
            error_prompt_label.setText("");
            luku++;
            lw.logThis("Login attempt: " + luku + "...PASSED.");
            lw.logThis("...by username: " + username_field.getText());
            //tämä dialog ei ole enää käytössä...
       /*Dialog dialog = new Dialog();
             dialog.setTitle("Wow! Much Error!");
             dialog.setHeaderText("Something went wrong. Doge is sad!");
             dialog.setContentText("Wow :'C");
             dialog.setGraphic(new ImageView(getClass().getResource("/graphics/dogesad.png").toString()));
             dialog.getDialogPane().getButtonTypes().add(ButtonType.OK); 
             Optional result = dialog.showAndWait();*/

            timotei_loading.setVisible(true);
            final Timeline timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            final KeyValue kv = new KeyValue(timotei_loading.rotateProperty(), -360);
            final KeyFrame kf = new KeyFrame(Duration.millis(7000), kv);
            timeline.getKeyFrames().add(kf);
            timeline.play();

            final Timeline timeline2 = new Timeline();
            timeline2.setCycleCount(Timeline.INDEFINITE);
            final KeyValue kv2 = new KeyValue(timotei_doge.rotateProperty(), 360);
            final KeyFrame kf2 = new KeyFrame(Duration.millis(7000), kv2);
            timeline2.getKeyFrames().add(kf2);
            timeline2.play();

            login_button.setVisible(false);
            username_field.setVisible(false);
            password_field.setVisible(false);
            database_choice.setVisible(false);
            warehouse_choice.setVisible(false);
            timotei_progressbar.setVisible(true);
            //timotei_loadinglabel.setText("Generating those dank memes...");
            if (!database_choice.isSelected()) {
                Task task = new LoaderBGW();
                timotei_progressbar.progressProperty().bind(task.progressProperty());
                timotei_loadinglabel.textProperty().bind(task.messageProperty());
                task.setOnSucceeded((Event event2) -> {
                    Parent root3;
                    try {
                        Stage stage3 = Mainclass.getStage();
                        root3 = FXMLLoader.load(getClass().getResource("FXML_main.fxml"));
                        Scene scene3 = new Scene(root3);
                        stage3.setScene(scene3);
                        stage3.setResizable(true);
                        stage3.setFullScreen(true);
                        Image image3 = new Image(getClass().getResourceAsStream("/graphics/doge_icon.png"));
                        stage3.getIcons().add(image3);
                        stage3.setTitle("T.I.M.O.T.E.I - Loota kulkee kun Doge polkee");
                        //stage3.setResizable(true);
                        //stage3.setFullScreen(true);
                        //stage3.show();
                    } catch (IOException ex) {
                        lw.logThis("#IOException when opening FXML_main.fxml!");
                        lw.logThis("..." + ex.getMessage());
                        lw.logThis("...@" + this.getClass());
                    }
                });
                task.setOnFailed((Event event3) -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        lw.logThis("#InterruptedEsception while waiting");
                        lw.logThis("..." + ex.getMessage());
                        lw.logThis("...@" + this.getClass());
                    }
                    lw.logThis("#SmartPost-data download failed!");
                    Parent root3;
                    try {
                        Stage stage3 = Mainclass.getStage();
                        root3 = FXMLLoader.load(getClass().getResource("FXML_main.fxml"));
                        Scene scene3 = new Scene(root3);
                        stage3.setScene(scene3);
                        stage3.setResizable(true);
                        stage3.setFullScreen(true);
                        Image image3 = new Image(getClass().getResourceAsStream("/graphics/doge_icon.png"));
                        stage3.getIcons().add(image3);
                        stage3.setTitle("T.I.M.O.T.E.I - Loota kulkee kun Doge polkee");
                        //stage3.setResizable(true);
                        //stage3.setFullScreen(true);
                        //stage3.show();
                    } catch (IOException ex) {
                        lw.logThis("#IOException when opening FXML_main.fxml!");
                        lw.logThis("..." + ex.getMessage());
                        lw.logThis("...@" + this.getClass());
                    }
                });

                Thread thread = new Thread(task);
                thread.start();
            } else {
                if (!warehouse_choice.isSelected()) {//halutaan luoda uusi varasto
                    System.out.println("HUEH");
                    ArrayList<temp_storage.Order> orderAL = dbm.getOrderAL();
                    if (orderAL != null) {
                        System.out.println("EI ollut order null");
                        if (!orderAL.isEmpty()) {
                            System.out.println("Ei ollut order empty");
                            for(temp_storage.Order order : orderAL){
                                System.out.println("POISTETAAN: "+order.getWarehouseID());
                                dbm.delOrder(order, false, 2);
                            }
                        }
                    }
                }
                BufferedReader in;
                try {
                    in = new BufferedReader(new FileReader("DB.sqlite"));
                } catch (FileNotFoundException ex) {
                    File file = new File("DB.sqlite");
                    file.createNewFile();
                    in = new BufferedReader(new FileReader("DB.sqlite"));
                }

                //File file = new File("DB.sqlite");
                //System.out.println(file.length());
                //System.out.println(file.getAbsolutePath());
                //System.out.println(file.getTotalSpace());
                if (in.readLine() == null) {
                    database_choice.setSelected(false);
                    warehouse_choice.setSelected(false);
                    lw.logThis("Database was empty! Creating new with defaults...");
                    this.login_button_pressed(event);
                } else {
                    Task task2 = new LoginBG();
                    timotei_progressbar.progressProperty().bind(task2.progressProperty());
                    timotei_loadinglabel.textProperty().bind(task2.messageProperty());
                    Thread thread2 = new Thread(task2);
                    thread2.start();
                    task2.setOnSucceeded(new EventHandler() {
                        @Override
                        public void handle(Event event2) {
                            Parent root3;
                            try {
                                Stage stage3 = Mainclass.getStage();
                                root3 = FXMLLoader.load(getClass().getResource("FXML_main.fxml"));
                                Scene scene3 = new Scene(root3);
                                stage3.setScene(scene3);
                                stage3.setResizable(true);
                                stage3.setFullScreen(true);
                                Image image3 = new Image(getClass().getResourceAsStream("/graphics/doge_icon.png"));
                                stage3.getIcons().add(image3);
                                stage3.setTitle("T.I.M.O.T.E.I - Loota kulkee kun Doge polkee");
                                //stage3.setResizable(true);
                                //stage3.setFullScreen(true);
                                //stage3.show();
                            } catch (IOException ex) {
                                lw.logThis("#IOException when opening FXML_main.fxml!");
                                lw.logThis("..." + ex.getMessage());
                                lw.logThis("...@" + this.getClass());
                            }
                        }
                    });
                }
            }

        }
    }

    @FXML
    private void databaseChoiceClicked(ActionEvent event) {
        if(database_choice.isSelected()){
            warehouse_choice.setVisible(true);
        } else{
            warehouse_choice.setSelected(false);
            warehouse_choice.setVisible(false);
        }
    }

}
