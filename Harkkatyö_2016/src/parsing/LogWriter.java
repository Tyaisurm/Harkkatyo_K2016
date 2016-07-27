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

    private static LogWriter instance = null;
    private ListView<String> lw;
    //private ListView<String> lw2;

    private LogWriter() {
        //
        //lw2 = new ListView();
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

    public void logThis(String input, ListView<String> lwIn) {
        //lw2nd.logthis(input);,this.log_listview
        for (String s : lw.getItems()) {
            lwIn.getItems().add(s);
        };
        lw = lwIn;
        Calendar cal = Calendar.getInstance();
        lw.getItems().add(cal.getTime().toString() + " : " + input);
    }

    public void logThis(String input) {
        Calendar cal = Calendar.getInstance();
        lw.getItems().add(cal.getTime().toString() + " : " + input);
    }


    public ListView<String> getListView() {
        return this.lw;
    }

}
