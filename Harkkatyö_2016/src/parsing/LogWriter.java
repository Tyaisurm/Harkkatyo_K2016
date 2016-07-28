/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsing;

import java.util.Calendar;
import javafx.scene.control.ListView;

/**
 *
 * @author m7942
 */
public class LogWriter {
    //Tämä on ohjelman lokinpitäjä ja kirjuri. Jos jotain tapahtuu, tämä luokka
    //kirjaa kutsuttaessa annetun ilmoituksen ylös.

    private static LogWriter instance = null;
    private ListView<String> lw;

    private LogWriter() {
        lw = new ListView<>();
        Calendar cal = Calendar.getInstance();
        lw.getItems().add(cal.getTime().toString() + " : " + "LogWriter instance created...");
    }

    public static LogWriter getInstance() {
        if (instance == null) {
            instance = new LogWriter();
        }
        return instance;
    }

    public void logThis(String input, ListView<String> lwIn) {//Erikoismetodi. Kutsutaan vain kun "näkyvä" loki on luotu ohjelmassa,
        for (String s : lw.getItems()) {                      //jotta saadaan kaikki tiedot näkyviin näppärästi.
            lwIn.getItems().add(s);
        };
        lw = lwIn;
        Calendar cal = Calendar.getInstance();
        lw.getItems().add(cal.getTime().toString() + " : " + input);
    }

    public void logThis(String input) {//Perusmetodi. Tätä kutsutaan.
        Calendar cal = Calendar.getInstance();
        lw.getItems().add(cal.getTime().toString() + " : " + input);
    }


    public ListView<String> getListView() {//palauttaa viittauksen näkyvään ListViewiin
        return this.lw;
    }

}
