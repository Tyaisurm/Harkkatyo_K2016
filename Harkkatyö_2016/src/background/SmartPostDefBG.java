/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package background;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Integer.parseInt;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import javafx.concurrent.Task;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import parsing.LogWriter;
/**
 *
 * @author m7942
 */
public class SmartPostDefBG extends Task<Void>{
    LogWriter lw = LogWriter.getInstance();
    private Document document;

    @Override
    //tämä kutsutaan kun halutaan resetoida tietokannassa olevat "SmartPost" taulut.
    //eli, kun halutaan hakea uusimmat SmartPostit resetoimatta koko tietokantaa
    protected Void call() throws Exception {
        this.updateMessage("Tietokanta oli tyhjä tai se halutaan korvata...");
        lw.logThis("Preparing database initialization...");
        Thread.sleep(2000);
        this.updateMessage("Valmistellaan SmartPost-datan latausta");
        Thread.sleep(2000);
        Element root = parseToElement();
        NodeList nl = root.getElementsByTagName("place");
        double prvalue = 0;
        double max = 0;
        int i = 0;
        double done = 0;
        Node node = nl.item(0);
        Element element = (Element) node;
        if (element == null) {
            this.updateMessage("Virhe SmartPost-dataa ladatessa!");
            lw.logThis("#Unable to load SmartPost XML! Inputnode was null!");
            lw.logThis("...@" + this.getClass());
            Thread.sleep(1000);
            this.failed();
        } else {
            this.updateMessage("SmartPost-data ladattu! Valmistellaan tietokantaa...");
            lw.logThis("SmartPost-data download finished...");
            Connection c;
            PreparedStatement pst;
            Thread.sleep(1000);

            try {//tässä poistetaan vanha ja lisätään uusi SQL:n avulla
                
                String command = "DELETE FROM smartpost";

                //Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:DB.sqlite");
                c.setAutoCommit(false);
                pst = c.prepareStatement(command);
                pst.executeUpdate();
                c.commit();
                c.close();
                lw.logThis("Database(SmartPost) cleared. Ready for fresh data...");
                

                //Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:DB.sqlite");
                c.setAutoCommit(false);
                lw.logThis("Ready for new SmartPost-data...");
                String command1 = "INSERT INTO 'Smartpost' VALUES(?,?,?)";
                String command2 = "INSERT INTO 'AddressSP' VALUES(?,?,?,?)";
                String command3 = "INSERT INTO 'CoordinatesSP' VALUES(?,?,?)";
                PreparedStatement ps1 = c.prepareStatement(command1);
                PreparedStatement ps2 = c.prepareStatement(command2);
                PreparedStatement ps3 = c.prepareStatement(command3);
                ps1.setNull(1, Types.INTEGER);
                ps2.setNull(1, Types.INTEGER);
                ps2.setNull(1, Types.INTEGER);

                while (nl.getLength() > i) {//otetaan halutut tiedot XML-dokumentista ja tungetaan tietokantaan :)
                    //this.updateMessage("Odotetaan...");
                    //this.updateMessage("EI ODOTETA!!!!");
                    Thread.sleep(50);
                    //tehdään taikoja
                    Element e = (Element) nl.item(i);
                    Integer codeI = parseInt(e.getElementsByTagName("code").item(0).getTextContent());
                    ps2.setInt(2, codeI);
                    String cityS = e.getElementsByTagName("city").item(0).getTextContent().toUpperCase();
                    ps2.setString(4, cityS);
                    String addressS = e.getElementsByTagName("address").item(0).getTextContent();
                    ps2.setString(3, addressS);
                    String availabilityS = e.getElementsByTagName("availability").item(0).getTextContent();
                    ps1.setString(3, availabilityS);
                    String postofficeS = e.getElementsByTagName("postoffice").item(0).getTextContent();
                    ps1.setString(2, postofficeS);
                    String latS = e.getElementsByTagName("lat").item(0).getTextContent();
                    ps3.setString(2, latS);
                    String lngS = e.getElementsByTagName("lng").item(0).getTextContent();
                    ps3.setString(3, lngS);
                    this.updateMessage("Ladataan SmartPost: " + postofficeS);
                    ps1.execute();
                    ps2.execute();
                    ps3.execute();

                    prvalue = prvalue + 1;
                    max = nl.getLength();
                    this.updateProgress(prvalue, max);

                    i++;
                }
                c.commit();
                ps1.close();
                ps2.close();
                ps3.close();
                c.close();
                lw.logThis("SmartPost-data added into database successfully...");

                this.updateProgress(-1, -1);
                this.succeeded();

            } catch (SQLException ex) {
                lw.logThis("#SQLException happened with JDBC!");
                lw.logThis("..." + ex.getCause());
                lw.logThis("..." + ex.getMessage());
                lw.logThis("...@" + this.getClass());
                this.updateMessage("Virhe havaittu! Avataan ohjelma....");
                Thread.sleep(1000);
                this.failed();

            }
        }
        /*if (this.stateProperty().get() == State.FAILED) {
            this.cancel();
        } else {
            this.succeeded();
        }*/
        return null;
    }
    private String getCode() {//haetaan annetusta URL lähdekoodi, joka palautetaan String muodossa(TÄTÄ EI KÄYTETÄ ATM!)
        String urlS = "http://smartpost.ee/fi_apt.xml";
        URL url;
        String content = "";
        String line;

        try {
            url = new URL(urlS);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = br.readLine()) != null) {
                content += line + "\n";
            }

        } catch (MalformedURLException ex) {
            lw.logThis("#MalformedURLException in getting data from given URL!");
            lw.logThis("..." + ex.getMessage());
            lw.logThis("...@" + this.getClass());
        } catch (IOException ex) {
            lw.logThis("#IOException in getting data from given URL!");
            lw.logThis("..." + ex.getMessage());
            lw.logThis("...@" + this.getClass());
        }
        return content;
    }

    private Element parseToElement() {//otetaan vastaan String-muodossa oleva koodi, josta tehdään DOM Document Instance.
        String url = "http://smartpost.ee/fi_apt.xml";
        Element root = null;                    //Tästä palautetaan Element root, jossa on suora pääsy childnodeen
        try {
            //
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(url);
            root = document.getDocumentElement();

            return root;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            lw.logThis("#Error happened during DOM document creation!");
            lw.logThis("..." + ex.getCause());
            lw.logThis("..." + ex.getMessage());
            lw.logThis("...@" + this.getClass());
        }
        return root;
    }
    
}
