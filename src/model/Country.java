/**
 *
 * @author Tory Fitzgerald, id: 000559078
 */

package model;

public class Country {

    private int countryID;
    private String countryName;

    public Country(int countryID, String countryName) {
        this.countryID = countryID;
        this.countryName = countryName;
    }

    /**
     * @return the countryID
     */
    public int getCountryID() {
        return countryID;
    }

    @Override
    public String toString() {
        return countryName;
    }
}
