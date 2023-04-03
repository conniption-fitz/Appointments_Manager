package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ContactDAO {
    //read only
    private static ObservableList<Contact> allContacts;
    public static ObservableList<Contact> getAllContacts() {
        allContacts = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM contacts";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contact contact = new Contact(id, name, email);

                allContacts.add(contact);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return allContacts;
    }

    public static Contact getContactByID(int contactID) {
        //check if allContacts is initialized
        if (allContacts == null) {
            getAllContacts();
        }

        for (Contact current : allContacts) {
            if (current.getContactID() == contactID) {
                return current;
            }
        }
        return null;
    }
}
