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
public class Object {

    public Object() {
    }

    protected String description;
    protected double weight;
    protected int size;
    protected String name;
    protected boolean fragile;
    protected Integer objectID;//jotta voi olla null

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the fragile
     */
    public boolean isFragile() {
        return fragile;
    }

    /**
     * @return the objectID
     */
    public int getObjectID() {
        return objectID;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
