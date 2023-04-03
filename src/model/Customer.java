package model;

public class Customer {

    private int customerID;
    private String customerName;
    private String address;
    private String zipCode;
    private String phoneNum;
    private int divisionID;
    private int autoID;
    private String createUser;
    private String editUser;

    public Customer(int customerID, String customerName, String address, String zipCode, String phoneNum, int divisionID) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNum = phoneNum;
        this.divisionID = divisionID;
        //this.createUser = createUser;
        //this.editUser = editUser;
    }

    @Override
    public String toString() {
        return customerName;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getDivisionID() {
        return divisionID;
    }

    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

}
