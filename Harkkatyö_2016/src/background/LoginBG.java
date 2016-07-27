/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package background;

import javafx.concurrent.Task;
import parsing.LogWriter;

/**
 *
 * @author m7942
 */
public class LoginBG extends Task<Void> {
    LogWriter lw = LogWriter.getInstance();

    @Override
    protected Void call() throws Exception {
        lw.logThis("Using existing database by user choice...");
        this.updateMessage("Generating those dank memes");
        Thread.sleep(1000);
        this.updateMessage("Generating those dank memes.");
        Thread.sleep(1000);
        this.updateMessage("Generating those dank memes..");
        Thread.sleep(1000);
        this.updateMessage("Generating those dank memes...");
        Thread.sleep(2000);
        this.updateMessage("Generating those dank memes...PASSED.");
        Thread.sleep(2000);
        return null;
    }

}
