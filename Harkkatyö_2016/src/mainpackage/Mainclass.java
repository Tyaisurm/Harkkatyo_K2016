/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainpackage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import parsing.LogWriter;

/**
 *
 * @author m7942
 */
public class Mainclass extends Application {
    //Tämä on ohjelman pääluokka, joka kutsutaan ensimmäisenä. Tässä ei ole mitään erikoista
    
    static LogWriter lw = LogWriter.getInstance();
    private static Stage pStage;
    
    @Override
    public void start(Stage stage) throws Exception {
        pStage=stage;
        Parent root = FXMLLoader.load(getClass().getResource("FXML_login.fxml"));
        Scene scene = new Scene(root);
        pStage.setResizable(false);
        pStage.setScene(scene);
        pStage.setTitle("T.I.M.O.T.E.I - Login");
        Image image = new Image(getClass().getResourceAsStream("/graphics/doge_icon.png"));
        pStage.getIcons().add(image);
        
        pStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        lw.logThis("Program booted...");
        launch(args);
    }
    
    public static Stage getStage(){
        return pStage;
    }    
    
}
