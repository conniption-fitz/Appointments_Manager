/**
 *
 * @author Tory Fitzgerald, id: 000559078
 */

package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CustomerDAO {
    private static ObservableList<Customer> allCustomers;

    /**
     * Inserts a new Customer into the database.
     *
     * @param customer - the customer to be added
     * @return the number of customers added to the database
     */
    public static int insertCustomer(Customer customer) throws SQLException {
        int rowsAffected = -1;
        try {
            String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getZipCode());
            ps.setString(4, customer.getPhoneNum());
            ps.setInt(5, customer.getDivisionID());
            rowsAffected = ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    /**
     * Updates the data of an existing customer in the database.
     *
     * @param customer - the updated customer
     * @return the number of customers updated
     */
    public static int updateCustomer(Customer customer) throws SQLException {
        int rowsAffected = -1;
        try {
            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID =? "
                    + "WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getAddress());
            ps.setString(3, customer.getZipCode());
            ps.setString(4, customer.getPhoneNum());
            ps.setInt(5, customer.getDivisionID());
            ps.setInt(6, customer.getCustomerID());
            rowsAffected = ps.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    /**
     * Deletes a customer from the database.
     *
     * @param customer - the customer to be deleted
     * @return the number of customers deleted
     */
    public static int deleteCustomer(Customer customer) throws SQLException {
        int rowsAffected = -1;
        try {
            int appointmentsDeleted = -1;
            appointmentsDeleted = AppointmentDAO.deleteAppointmentByCustomerID(customer.getCustomerID());
            System.out.println("Appointments deleted: " + appointmentsDeleted);
        } catch (SQLException e) {
            System.out.println("No appointments for this customer.");
        }
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customer.getCustomerID());
            rowsAffected = ps.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    /**
     * @return a list of all customers
     */
    public static ObservableList<Customer> getAllCustomers() {
        allCustomers = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM customers";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String zip = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int division = rs.getInt("Division_ID");
                Customer customer = new Customer(id, name, address, zip, phone, division);
                allCustomers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomers;
    }

    /**
     * Returns a Customer by ID.
     *
     * @param customerID - the customerID
     * @return the customer
     */
    public static Customer getCustomerByID(int customerID) {
        //check if allCustomers is initialized
        if (allCustomers == null) {
            getAllCustomers();
        }
        for (Customer current : allCustomers) {
            if (current.getCustomerID() == customerID) {
                return current;
            }
        }
        return null;
    }

    /**
     * Returns the total number of appointments by Division.
     *
     * @param divisionID - the user-selected Division
     * @return the total number of appointmetns
     */
    public static int getTotalByDivision(int divisionID) {
        int total = 0;
        if (allCustomers == null) {
            getAllCustomers();
        }
        for (Customer allCustomer : allCustomers) {
            if(allCustomer.getDivisionID() == divisionID) {
                total += 1;
            }
        }
        return total;
    }
}
