/**
 *
 * @author Tory Fitzgerald, id: 000559078
 */

package model;

public class Customer {

    private int customerID;
    private String customerName;
    private String address;
    private String zipCode;
    private String phoneNum;
    private int divisionID;

    public Customer(int customerID, String customerName, String address, String zipCode, String phoneNum, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNum = phoneNum;
        this.divisionID = divisionID;
    }

    @Override
    public String toString() {
        return customerName;
    }

    /**
     * @return the customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @return the customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName - the customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address - the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the zip code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * @param zipCode - the zip code
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @return the phone number
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * @param phoneNum - the phone number
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * @return the divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * @param divisionID - the divisionID
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

}
