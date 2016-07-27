/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package temp_storage;

/**
 *
 * @author m7942
 */
public class Details {
    
    public Details(){
        //
    }
    
    protected int whID;
    protected int objID;
    protected String objName;
    protected int objSize;
    protected double objWeight;
    protected boolean objFragile;
    protected String objDesc;
    protected int pckType;
    protected String pckDesc;
    protected String toPost;
    protected String fromPost;

    /**
     * @return the whID
     */
    public int getWhID() {
        return whID;
    }

    /**
     * @return the objID
     */
    public int getObjID() {
        return objID;
    }

    /**
     * @return the objName
     */
    public String getObjName() {
        return objName;
    }

    /**
     * @return the objSize
     */
    public int getObjSize() {
        return objSize;
    }

    /**
     * @return the objWeight
     */
    public double getObjWeight() {
        return objWeight;
    }

    /**
     * @return the objFragile
     */
    public boolean isObjFragile() {
        return objFragile;
    }

    /**
     * @return the objDesc
     */
    public String getObjDesc() {
        return objDesc;
    }

    /**
     * @return the pckType
     */
    public int getPckType() {
        return pckType;
    }

    /**
     * @return the pckDesc
     */
    public String getPckDesc() {
        return pckDesc;
    }

    /**
     * @return the toPost
     */
    public String getToPost() {
        return toPost;
    }

    /**
     * @return the fromPost
     */
    public String getFromPost() {
        return fromPost;
    }
    
}
