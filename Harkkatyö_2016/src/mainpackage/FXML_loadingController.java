/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mainpackage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import parsing.LogWriter;

/**
 * FXML Controller class
 *
 * @author m7942
 */
public class FXML_loadingController implements Initializable {
    //Tämä on kutsuttaessa miniatyyriversio sisäänkirjautumisikkunan pyörivästä
    //dogesta. Sama käyttötarkoitus, mutta muualla kuin siellä(Koska kuka muka laittaisi
    //kaksi latauskuvaketta ilmoittamaan YHDESTÄ latauksesta :S).
    
    LogWriter lw = LogWriter.getInstance();
    
    @FXML
    private ImageView loading_circle;
    @FXML
    private ImageView doge_face;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //You spin me right round, baby / Right round like a record, baby / Right round round round
        //:D
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        final KeyValue kv = new KeyValue(loading_circle.rotateProperty(),-360);
        final KeyFrame kf = new KeyFrame(Duration.millis(7000), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        final Timeline timeline2 = new Timeline();
        timeline2.setCycleCount(Timeline.INDEFINITE);
        final KeyValue kv2 = new KeyValue(doge_face.rotateProperty(),360);
        final KeyFrame kf2 = new KeyFrame(Duration.millis(7000), kv2);
        timeline2.getKeyFrames().add(kf2);
        timeline2.play();        
    }    
    
}
