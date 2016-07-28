/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsing;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import temp_storage.*;

/**
 *
 * @author m7942
 */
public class Database_manager {
    //Tämä on pääasiallinen luokka kun tehdään asioita SQL-tietokannassa
    //"background" kansiossa on toiset luokat, jotka käyttävät tietokantaa itsestään suoraan

    LogWriter lw = LogWriter.getInstance();
    TempStorageController tsc = TempStorageController.getInstance();

    private static Database_manager instance = null;

    private Database_manager() {
        lw.logThis("Database_manager instance created...");
        //
    }

    public static Database_manager getInstance() {
        if (instance == null) {
            instance = new Database_manager();
        }
        return instance;
    }


    /*public ComboBox<temp_storage.Order> getOrderCombo() {
     return this.send_order_comb;
     }

     public void setOrderCombo(ComboBox<temp_storage.Order> combo) {
     send_order_comb = combo;
     }*/
    public ArrayList<temp_storage.SmartPost> getSPAL(String spArea) {//Palautetaan AL, jossa on lista SmartPosteista
        ArrayList<temp_storage.SmartPost> spal = null;
        try {
            Connection c;
            //Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DB.sqlite");
            c.setAutoCommit(false);
            PreparedStatement pst;
            String query;

            if (spArea == null) {
                query = "SELECT * FROM smartpost INNER JOIN addresssp ON smartpost.postid=addresssp.postid INNER JOIN coordinatessp ON smartpost.postid=coordinatessp.postid;";
                pst = c.prepareStatement(query);
            } else {
                query = "SELECT * FROM smartpost INNER JOIN addresssp ON smartpost.postid=addresssp.postid INNER JOIN coordinatessp ON smartpost.postid=coordinatessp.postid WHERE city=?;";
                pst = c.prepareStatement(query);
                pst.setString(1, spArea);
            }
            ResultSet rs = pst.executeQuery();
            spal = new ArrayList<>();
            while (rs.next()) {
                int postID = rs.getInt("PostID");
                String postDesc = rs.getString("PostDescription");
                String availability = rs.getString("Availability");
                int postCode = rs.getInt("Postcode");
                String street = rs.getString("StreetAddress");
                String city = rs.getString("City");
                String lat = rs.getString("Lat");
                String lng = rs.getString("Lng");
                SmartPost sp = tsc.setSP(postID, postDesc, availability, postCode, street, city, lat, lng);
                spal.add(sp);
            }
            pst.close();
            c.close();
            return spal;
        } catch (SQLException ex) {
            lw.logThis("#SQLException happened with JDBC!");
            lw.logThis("..." + ex.getCause());
            lw.logThis("..." + ex.getMessage());
            lw.logThis("...@" + this.getClass());
        }
        return spal;
    }

    public ArrayList<String> getSPAreaAL() {//Palautetaan AL, jossa on tiedot SP paikkakunnista
        ArrayList<String> al = null;
        try {
            Connection c;
            PreparedStatement pst;
            al = new ArrayList<>();
            //Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DB.sqlite");
            c.setAutoCommit(false);
            String query = "SELECT DISTINCT city from addresssp";
            pst = c.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String area = rs.getString("City");
                al.add(area);
            }
            pst.close();
            c.close();
            return al;
        } catch (SQLException ex) {
            lw.logThis("#SQLException happened with JDBC!");
            lw.logThis("..." + ex.getCause());
            lw.logThis("..." + ex.getMessage());
            lw.logThis("...@" + this.getClass());
        }
        return al;
    }

    public ArrayList<temp_storage.Object> getObjectAL() {//palautetaan AL, jossa on lista esineistä
        ArrayList<temp_storage.Object> al = null;
        try {
            Connection c;
            Statement stmt;
            al = new ArrayList<>();
            //Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DB.sqlite");
            c.setAutoCommit(false);

            PreparedStatement pst;
            al = new ArrayList<>();
            String query = "SELECT * FROM object;";
            pst = c.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String description = rs.getString("Description");
                double weight = rs.getDouble("Weight");
                int size = rs.getInt("Size");
                String name = rs.getString("Name");
                boolean bool = rs.getBoolean("Fragile");
                int objectID = rs.getInt("ObjectID");
                temp_storage.Object obj = tsc.setObject(description, weight, size, name, bool, objectID);
                al.add(obj);
            }
            pst.close();
            c.close();
            return al;
        } catch (SQLException ex) {
            lw.logThis("#SQLException happened with JDBC!");
            lw.logThis("..." + ex.getCause());
            lw.logThis("..." + ex.getMessage());
            lw.logThis("...@" + this.getClass());
        }
        return al;
    }

    public ArrayList<temp_storage.Package> getPackageAL() {//Palautetaan AL, jossa on lista pakettityypeistä
        ArrayList<temp_storage.Package> al = null;
        try {
            Connection c;
            al = new ArrayList<>();
            //Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DB.sqlite");
            c.setAutoCommit(false);

            PreparedStatement pst;
            al = new ArrayList<>();
            String query = "SELECT * FROM packettype;";
            pst = c.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int classID = rs.getInt("ClassID");
                String description = rs.getString("Description");
                double travelLimit = rs.getDouble("TravelLimit");
                int sizeLimit = rs.getInt("SizeLimit");
                temp_storage.Package pck = tsc.setPackage(classID, description, travelLimit, sizeLimit);
                al.add(pck);
            }
            pst.close();
            c.close();
            return al;
        } catch (SQLException ex) {
            lw.logThis("#SQLException happened with JDBC!");
            lw.logThis("..." + ex.getCause());
            lw.logThis("..." + ex.getMessage());
            lw.logThis("...@" + this.getClass());
        }
        return al;
    }

    public void setObject(String description, double weight, int size, String name, boolean bool, Integer objectID) {//luodaan uusi esine tietokantaan
        try {
            Connection c;
            //Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DB.sqlite");
            c.setAutoCommit(false);

            PreparedStatement pst;
            String command = "INSERT INTO 'Object' VALUES(?,?,?,?,?,?)";
            pst = c.prepareStatement(command);
            pst.setString(1, description);
            pst.setDouble(2, weight);
            pst.setInt(3, size);
            pst.setString(4, name);
            pst.setBoolean(5, bool);
            pst.setNull(6, Types.INTEGER);

            pst.executeUpdate();
            c.commit();
            pst.close();
            c.close();
            lw.logThis("New Object has been added into database!");
        } catch (SQLException ex) {
            lw.logThis("#SQLException happened with JDBC!");
            lw.logThis("..." + ex.getCause());
            lw.logThis("..." + ex.getMessage());
            lw.logThis("...@" + this.getClass());
        }
    }

    //jotta voi olla null
    public void setOrder(Integer packetID, int packageClassID, int objectID, int toPostID, int fromPostID) {//luodaan uusi paketti/tilaus tietokantaan
        try {
            Connection c;
            //Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DB.sqlite");
            c.setAutoCommit(false);

            PreparedStatement pst1;
            String command1 = "INSERT INTO 'Warehouse' VALUES(?,?,?,?,?)";
            pst1 = c.prepareStatement(command1);
            pst1.setNull(1, Types.INTEGER);
            pst1.setInt(2, packageClassID);
            pst1.setInt(3, objectID);
            pst1.setInt(4, toPostID);
            pst1.setInt(5, fromPostID);
            pst1.executeUpdate();

            c.commit();
            pst1.close();
            c.close();
            lw.logThis("New Order/Package has been placed into warehouse!");
        } catch (SQLException ex) {
            lw.logThis("#SQLException happened with JDBC!");
            lw.logThis("..." + ex.getCause());
            lw.logThis("..." + ex.getMessage());
            lw.logThis("...@" + this.getClass());
        }
    }

    public ArrayList<temp_storage.Order> getOrderAL() {//palautetaan AL, jossa on lista paketeista/tilauksista
        ArrayList<temp_storage.Order> al = null;
        try {
            Connection c;
            al = new ArrayList<>();
            //Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DB.sqlite");
            c.setAutoCommit(false);

            PreparedStatement pst;
            al = new ArrayList<>();
            String query = "SELECT * FROM warehouse;";
            pst = c.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int w = rs.getInt("PacketID");
                int o = rs.getInt("Class");
                int p = rs.getInt("ObjectID");
                int spFROM = rs.getInt("FromPostID");
                int spTO = rs.getInt("ToPostID");
                temp_storage.Order order = tsc.setOrder(w, o, p, spTO, spFROM);
                al.add(order);
            }
            pst.close();
            c.close();
            return al;
        } catch (SQLException ex) {
            lw.logThis("#SQLException happened with JDBC!");
            lw.logThis("..." + ex.getCause());
            lw.logThis("..." + ex.getMessage());
            lw.logThis("...@" + this.getClass());
        }
        return al;
    }

    public temp_storage.Details showOrderDetails(temp_storage.Order order) {//palautetaan tiedot sisääntulevasta(order) paketista
        int whID = order.getWarehouseID();
        int objID = order.getObjectID();
        int pckType = order.getPackageClassID();
        int fpID = order.getFromPostID();
        int tpID = order.getToPostID();
        String objName;
        int objSize;
        double objWeight;
        boolean objFragile;
        String objDesc;
        String pckDesc;
        String toPost;
        String fromPost;
        temp_storage.Details details = null;

        try {
            Connection c;
            //Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DB.sqlite");
            c.setAutoCommit(false);
            String query = "SELECT * FROM smartpost "
                    + "WHERE postid = ?;";
            PreparedStatement pst = c.prepareStatement(query);
            pst.setInt(1, fpID);
            ResultSet rs = pst.executeQuery();
            fromPost = rs.getString("PostDescription");
            query = "SELECT * FROM smartpost "
                    + "WHERE postid = ?;";
            PreparedStatement pst2 = c.prepareStatement(query);
            pst2.setInt(1, tpID);
            ResultSet rs2 = pst2.executeQuery();
            toPost = rs2.getString("PostDescription");
            query = "SELECT * FROM object "
                    + "WHERE objectid = ?;";
            PreparedStatement pst3 = c.prepareStatement(query);
            pst3.setInt(1, objID);
            ResultSet rs3 = pst3.executeQuery();
            objName = rs3.getString("Name");
            objSize = rs3.getInt("Size");
            objWeight = rs3.getDouble("Weight");
            objFragile = rs3.getBoolean("Fragile");
            objDesc = rs3.getString("Description");

            String query2 = "SELECT * FROM packettype "
                    + "WHERE classid = ?;";
            PreparedStatement pst4 = c.prepareStatement(query2);
            pst4.setInt(1, pckType);
            ResultSet rs4 = pst4.executeQuery();
            pckDesc = rs4.getString("Description");

            details = tsc.setDetails(whID, objID, objName, objSize, objWeight, objFragile, objDesc, pckType, pckDesc, toPost, fromPost);

            pst.close();
            pst2.close();
            pst3.close();
            pst4.close();
            c.close();
            return details;
        } catch (SQLException ex) {
            lw.logThis("#SQLException happened with JDBC!");
            lw.logThis("..." + ex.getCause());
            lw.logThis("..." + ex.getMessage());
            lw.logThis("...@" + this.getClass());
        }
        return details;
    }

    public ArrayList<temp_storage.SmartPost> getSendAL(temp_storage.Order order) {//palautetaan AL, jossa on tiedot lähetys- ja vastaanottoSmartPosteista
        ArrayList<temp_storage.SmartPost> al = null;
        int toPostID = order.getToPostID();
        int fromPostID = order.getFromPostID();
        try {
            Connection c;
            //Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DB.sqlite");
            c.setAutoCommit(false);

            al = new ArrayList<>();

            //otetaan FROM SmartPost listaan
            String query1 = "SELECT * FROM smartpost "
                    + "WHERE postid=?;";
            String query2 = "SELECT * FROM addresssp "
                    + "WHERE postid = ?";
            String query3 = "SELECT * FROM coordinatessp "
                    + " WHERE postid = ?";
            PreparedStatement pst11 = c.prepareStatement(query1);
            PreparedStatement pst12 = c.prepareStatement(query2);
            PreparedStatement pst13 = c.prepareStatement(query3);
            pst11.setInt(1, fromPostID);
            pst12.setInt(1, fromPostID);
            pst13.setInt(1, fromPostID);
            ResultSet rs11 = pst11.executeQuery();
            ResultSet rs12 = pst12.executeQuery();
            ResultSet rs13 = pst13.executeQuery();
            int postID = rs11.getInt("PostID");
            String postDesc = rs11.getString("PostDescription");
            String availability = rs11.getString("Availability");
            int postCode = rs12.getInt("Postcode");
            String street = rs12.getString("StreetAddress");
            String city = rs12.getString("City");
            String lat = rs13.getString("Lat");
            String lng = rs13.getString("Lng");
            SmartPost sp = tsc.setSP(postID, postDesc, availability, postCode, street, city, lat, lng);
            al.add(sp);
            pst11.close();
            pst12.close();
            pst13.close();

            //otetaan TO SmartPost
            query1 = "SELECT * FROM smartpost "
                    + "WHERE postid=?;";
            query2 = "SELECT * FROM addresssp "
                    + "WHERE postid = ?";
            query3 = "SELECT * FROM coordinatessp "
                    + " WHERE postid = ?";
            PreparedStatement pst21 = c.prepareStatement(query1);
            PreparedStatement pst22 = c.prepareStatement(query2);
            PreparedStatement pst23 = c.prepareStatement(query3);
            pst21.setInt(1, toPostID);
            pst22.setInt(1, toPostID);
            pst23.setInt(1, toPostID);
            ResultSet rs21 = pst21.executeQuery();
            ResultSet rs22 = pst22.executeQuery();
            ResultSet rs23 = pst23.executeQuery();
            int postID2 = rs21.getInt("PostID");
            String postDesc2 = rs21.getString("PostDescription");
            String availability2 = rs21.getString("Availability");
            int postCode2 = rs22.getInt("Postcode");
            String street2 = rs22.getString("StreetAddress");
            String city2 = rs22.getString("City");
            String lat2 = rs23.getString("Lat");
            String lng2 = rs23.getString("Lng");
            SmartPost sp2 = tsc.setSP(postID2, postDesc2, availability2, postCode2, street2, city2, lat2, lng2);
            al.add(sp2);
            pst11.close();
            pst12.close();
            pst13.close();

            c.close();
            return al;
        } catch (SQLException ex) {
            lw.logThis("#SQLException happened with JDBC!");
            lw.logThis("..." + ex.getCause());
            lw.logThis("..." + ex.getMessage());
            lw.logThis("...@" + this.getClass());
        }
        return al;
    }

    public void delOrder(temp_storage.Order order, boolean status, int mode) {//mode 1 = tallenna ja poista, mode 2 = poista
        try {                                                                 //Poistetaan valittu paketti/tilaus tietokannasta.
            Connection c;                                                     //Halutessa voidaan kirjata kyseinen yksilö historiaan.
            //Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DB.sqlite");
            c.setAutoCommit(false);
            
            if(mode == 1){
            //Haetaan vaaditut tiedot historaa varten.../Siirretään tiedot historiaan...
            temp_storage.Details details = this.showOrderDetails(order);
            String command1 = "INSERT INTO history VALUES(?,?,?,?,?,?,?,?);";
            PreparedStatement pst1 = c.prepareStatement(command1);
            pst1.setNull(1, Types.INTEGER);
            Calendar calendar = Calendar.getInstance();
            java.sql.Timestamp ts = new java.sql.Timestamp(calendar.getTime().getTime());
            pst1.setTimestamp(2, ts);
            pst1.setString(3, details.getObjName());
            pst1.setBoolean(4, status);
            pst1.setString(5, details.getFromPost());
            pst1.setString(6, details.getToPost());
            pst1.setInt(7, details.getPckType());
            pst1.setDouble(8, details.getObjWeight());
            pst1.executeUpdate();
            pst1.close();
            lw.logThis("Preparation for history entry done...");
            }

            //Poistetaan warehousesta...
            PreparedStatement pst;
            String command = "DELETE FROM warehouse WHERE packetid = ?;";
            pst = c.prepareStatement(command);
            pst.setInt(1, order.getWarehouseID());
            
            pst.executeUpdate();
            c.commit();
            pst.close();
            c.close();
            lw.logThis("Order has been deleted from warehouse!");
        } catch (SQLException ex) {
            lw.logThis("#SQLException happened with JDBC!");
            lw.logThis("..." + ex.getCause());
            lw.logThis("..." + ex.getMessage());
            lw.logThis("...@" + this.getClass());
        }
    }

    public ArrayList<String> getHistory() {//Palautetaan AL, jossa on lähetyshistoria
        ArrayList<String> al = null;

        try {
            Connection c;
            //Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DB.sqlite");
            c.setAutoCommit(false);

            al = new ArrayList<>();
            PreparedStatement pst1;
            String command1 = "SELECT * FROM history "
                    + "INNER JOIN packettype ON history.classid = packettype.classid;";
            pst1 = c.prepareStatement(command1);
            ResultSet rs1 = pst1.executeQuery();

            while (rs1.next()) {
                int delID = rs1.getInt("DeliveryID");
                Timestamp sentDate = rs1.getTimestamp("SentDate");
                String objName = rs1.getString("ObjectName");
                boolean fragileStatus = rs1.getBoolean("FragileStatus");
                String fromP = rs1.getString("FromPost");
                String toP = rs1.getString("ToPost");
                String className = rs1.getString("Description");
                double objWeight = rs1.getDouble("ObjectWeight");
                String status = "";
                if (fragileStatus) {
                    status = "Kyllä!";
                } else {
                    status = "Ei!";
                }
                String output = "Kuljetuksen ID: " + delID + "; Aikaleima: " + sentDate.toString() + "; Esine: " + objName + "; Paino: " + objWeight +"; Pakettiluokka: " + className + "; Ehjänä perille: " + status + "; Mistä: " + fromP + "; Mihin: " + toP;

                al.add(output);
            }

            pst1.close();
            c.close();
            lw.logThis("History updated from database!");
            return al;
        } catch (SQLException ex) {
            lw.logThis("#SQLException happened with JDBC!");
            lw.logThis("..." + ex.getCause());
            lw.logThis("..." + ex.getMessage());
            lw.logThis("...@" + this.getClass());
        }

        return al;
    }

    public boolean isWarehouseEmpty() {//Tarkistetaan, että onko varasto tyhjä paketeista/lähetyksistä
        boolean bool = false;
        try {
            Connection c;
            //Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DB.sqlite");
            c.setAutoCommit(false);

            PreparedStatement pst;
            String command1 = "SELECT * FROM warehouse;";
            pst = c.prepareStatement(command1);
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()){
                bool = true;
            }else{
                bool = false;
            }
            
            pst.close();
            pst.close();
            c.close();
            return bool;
        } catch (SQLException ex) {
            lw.logThis("#SQLException happened with JDBC!");
            lw.logThis("..." + ex.getCause());
            lw.logThis("..." + ex.getMessage());
            lw.logThis("...@" + this.getClass());
        }
        return bool;
    }
    
    public void delObject(temp_storage.Object object){//Poistetaan valittu esine tietokannasta
        try {
            Connection c;
            //Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DB.sqlite");
            c.setAutoCommit(false);

            lw.logThis("Preparation for history entry done...");

            //Poistetaan object-taulusta...
            PreparedStatement pst;
            String command = "DELETE FROM object WHERE ObjectID = ?;";
            pst = c.prepareStatement(command);
            pst.setInt(1, object.getObjectID());
            
            pst.executeUpdate();
            c.commit();
            pst.close();
            c.close();
            lw.logThis("Object has been deleted from warehouse!");
        } catch (SQLException ex) {
            lw.logThis("#SQLException happened with JDBC!");
            lw.logThis("..." + ex.getCause());
            lw.logThis("..." + ex.getMessage());
            lw.logThis("...@" + this.getClass());
        }
    }
    
}
