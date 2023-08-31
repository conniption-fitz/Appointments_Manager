/**
 *
 * @author Tory Fitzgerald, id: 000559078
 */

package model;


public class Division {

    private int divisionID;
    private String divisionName;
    private int countryID;


    public Division(int divisionID, String divisionName, int countryID) {
        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /**
     * @return the divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * @return the countryID
     */
    public int getCountryID() {
        return countryID;
    }

    @Override
    public String toString() {
        return divisionName;
    }
}
