/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package parsing;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.web.WebView;


/**
 *
 * @author m7942
 */
public class InterfaceController {
    //Tämä luokka pitää huolen siitä, että ikkunat voivat muokkailla toistensa sisältöä vaivattomasti
    //Jokaisella tämän luokan muuttujalla on siis oma getter ja setter
    
    LogWriter lw = LogWriter.getInstance();
    private static InterfaceController instance = null;
    
    private ComboBox<temp_storage.Order> send_order_comb;
    private WebView wv;
    private ComboBox<String> choose_area_combo;
    private ComboBox<temp_storage.SmartPost> choose_smartpost_combo;
    ListView<String> history_listview;
    private temp_storage.Order order;
    
    private InterfaceController() {
        lw.logThis("InterfaceController instance created...");
    }

    public static InterfaceController getInstance() {
        if (instance == null) {
            instance = new InterfaceController();
        }
        return instance;
    }
    
    public ComboBox<temp_storage.Order> getOrderCombo() {
        return this.send_order_comb;
    }

    public void setOrderCombo(ComboBox<temp_storage.Order> combo) {
        send_order_comb = combo;
    }
    
    public void setWebView(WebView wvIN) {
        this.wv = wvIN;
    }

    public WebView getWebView() {
        return this.wv;
    }
    public void setSPAreaCombo(ComboBox<String> in){
        this.choose_area_combo = in;
    }
    public void setSPCombo(ComboBox<temp_storage.SmartPost> in){
        this.choose_smartpost_combo = in;
    }
    public ComboBox<String> getSPAreaCombo(){
        return this.choose_area_combo;
    }
    public ComboBox<temp_storage.SmartPost> getSPCombo(){
        return this.choose_smartpost_combo;
    }
    public ListView<String> getHistory(){
        return this.history_listview;
    }
    public void setHistory(ListView<String> in){
        this.history_listview = in;
    }
    
    public void setShowOrderDetails(temp_storage.Order orderIN) {
        this.order = orderIN;
    }

    public temp_storage.Order getShowOrderDetails() {
        return this.order;
    }
}
