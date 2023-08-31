/**
 *
 * @author Tory Fitzgerald, id: 000559078
 */

package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserDAO {
    //read only
    public static ObservableList<User> allUsers;

    /**
     * Initializes the list of all Users.
     */
    public static void setAllUsers() throws SQLException {
        allUsers = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String username = rs.getString("User_Name");
                String password = rs.getString("Password");
                User user = new User(id, username, password);
                allUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the list of users
     */
    public static ObservableList<User> getAllUsers() {
        return allUsers;
    }

    /**
     * Sets the User's login status to false.
     *
     * @param userID - the user
     */
    public static void logoutUser(int userID) {
        for (User current : allUsers) {
            if (current.getUserID() == userID) {
                current.setLoggedInStatus(false);
            }
        }
    }
}
