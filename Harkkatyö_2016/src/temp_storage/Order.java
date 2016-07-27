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
public class Order {

    public Order() {
    }
    //tämä pitää sisällään Warehouse-taulun rivit, eli yksittäist, lähettämistä vaille olevat paketit
    protected int warehouseID;
    protected int objectID;
    protected int packageClassID;
    protected int toPostID;
    protected int fromPostID;


    @Override
    public String toString() {
        int whID = this.warehouseID;
        int objID = this.objectID;
        int pckCID = this.packageClassID;
        int toPID = this.toPostID;
        int fromPID = this.fromPostID;
        String printout = "VarastoID: "+this.warehouseID;
        return printout;
    }

    public int getWarehouseID(){
        return warehouseID;
    }
    /**
     * @return the warehouseID
     */
    public int getObjectID() {
        return objectID;
    }

    /**
     * @return the packageClassID
     */
    public int getPackageClassID() {
        return packageClassID;
    }

    /**
     * @return the toPostID
     */
    public int getToPostID() {
        return toPostID;
    }

    /**
     * @return the fromPostID
     */
    public int getFromPostID() {
        return fromPostID;
    }

}
