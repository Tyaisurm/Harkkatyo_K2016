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
public class SmartPost {
    public SmartPost(){}
    //table Smartpost
    protected int postID;
    protected String postDescription;
    protected String availability;
    //table AddressSP
    protected int postCode;
    protected String streetAddress;
    protected String city;
    //table CoordinatesSP
    protected String lat;
    protected String lng;

    /**
     * @return the postID
     */
    public int getPostID() {
        return postID;
    }

    /**
     * @return the postDescription
     */
    public String getPostDescription() {
        return postDescription;
    }

    /**
     * @return the availability
     */
    public String getAvailability() {
        return availability;
    }

    /**
     * @return the postCode
     */
    public int getPostCode() {
        return postCode;
    }

    /**
     * @return the streetAddress
     */
    public String getStreetAddress() {
        return streetAddress;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @return the lat
     */
    public String getLat() {
        return lat;
    }

    /**
     * @return the lng
     */
    public String getLng() {
        return lng;
    }
    @Override
    public String toString (){
        return this.postDescription;
    }
}
