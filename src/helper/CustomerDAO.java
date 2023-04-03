package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CustomerDAO {
    private static ObservableList<Customer> allCustomers;
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

    public static int deleteCustomer(Customer customer) throws SQLException {
        int rowsAffected = -1;

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

    public static Customer getCustomerByID(int customerID) {
        //check if allCustomers is initialized
        if (allCustomers.size() == 0) {
            getAllCustomers();
        }

        for (Customer current : allCustomers) {
            if (current.getCustomerID() == customerID) {
                return current;
            }
        }
        return null;
    }

}
