/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temp_storage;

import parsing.LogWriter;

/**
 *
 * @author m7942
 */
public class TempStorageController {
    //Tämä luokka hallinnoi tietokannasta saadun datan käyttöä ohjelmassa luomalla olioita,
    //jotka toimivat ikään kuin "tilapäissäilöinä" datalle. Kaikki metodit luovat uuden olion, täyttävät tiedot ja palauttavat sen.

    LogWriter lw = LogWriter.getInstance();

    private static TempStorageController instance = null;

    private TempStorageController() {
        lw.logThis("TempStorageController instance created...");
        //
    }

    public static TempStorageController getInstance() {
        if (instance == null) {
            instance = new TempStorageController();
        }
        return instance;
    }
    //luodaan tilaus/paketti (Order) olio
    public Order setOrder(int wID, int oID, int ptypeID, int spTOid, int spFROMid) {
        Order varasto_paketti = new Order();
        varasto_paketti.warehouseID = wID;
        varasto_paketti.objectID = oID;
        varasto_paketti.packageClassID = ptypeID;
        varasto_paketti.fromPostID = spFROMid;
        varasto_paketti.toPostID = spTOid;
        return varasto_paketti;
    }
    //Luodaan esin (Object) olio
    public Object setObject(String description, double weight, int size, String name, boolean fragile, Integer objectID) {//"Integer", jotta voi olla null!
        Object esine = new Object();
        esine.description = description;
        esine.weight = weight;
        esine.size = size;
        esine.name = name;
        esine.fragile = fragile;
        esine.objectID = objectID;
        return esine;
    }
    //Luodaan SmartPost (SmartPost) olio
    public SmartPost setSP(int postID, String postDesc, String availability, int postCode, String street, String city, String lat, String lng) {
        SmartPost sp = new SmartPost();
        sp.postID = postID;
        sp.postDescription = postDesc;
        sp.availability = availability;
        sp.postCode = postCode;
        sp.streetAddress = street;
        sp.city = city;
        sp.lat = lat;
        sp.lng = lng;
        return sp;
    }
    //Luodaan pakettiluokka (Package) olio
    public Package setPackage(int classID, String description, double travelLimit, int sizeLimit) {
        Package paketti = new Package();
        paketti.classID = classID;
        paketti.description = description;
        paketti.travelLimit = travelLimit;
        paketti.sizeLimit = sizeLimit;
        return paketti;
    }
    //Luodaan tiedot (Details) olio
    public Details setDetails(int whID, int objID, String objName, int objSize, double objWeight, boolean objFragile, String objDesc, int pckType, String pckDesc, String toPost, String fromPost) {
        Details details = new Details();
        details.whID = whID;
        details.objID = objID;
        details.objName = objName;
        details.objSize = objSize;
        details.objWeight = objWeight;
        details.objFragile = objFragile;
        details.objDesc = objDesc;
        details.pckType = pckType;
        details.pckDesc = pckDesc;
        details.toPost = toPost;
        details.fromPost = fromPost;
        return details;
    }
}
