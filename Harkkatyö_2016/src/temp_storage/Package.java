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
public class Package {
    public Package(){}
    
    protected int classID;
    protected String description;
    protected double travelLimit;
    protected int sizeLimit;

    /**
     * @return the classID
     */
    public int getClassID() {
        return classID;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the travelLimit
     */
    public double getTravelLimit() {
        return travelLimit;
    }

    /**
     * @return the sizeLimit
     */
    public int getSizeLimit() {
        return sizeLimit;
    }
    @Override
    public String toString(){
        return this.description;
    }
}
