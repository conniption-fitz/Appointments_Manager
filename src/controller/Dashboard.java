/**
 *
 * @author Tory Fitzgerald, id: 000559078
 */

package controller;

import helper.AppointmentDAO;
import helper.UserDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
    public Button customersButton;
    public Button appointmentsButton;
    public Button reportsButton;
    private int userID;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZonedDateTime currentTime = ZonedDateTime.now();
        ObservableList<User> allUsers = UserDAO.getAllUsers();
        for (User current : allUsers) {
            if (current.getLoggedInStatus()) {
                userID = current.getUserID();
            }
        }
        AppointmentDAO.checkForAppointmentInFifteen(currentTime, userID);
    }

    /**
     * Opens the Customer Menu.
     *
     * @param actionEvent - the customers button is clicked
     */
    public void onCustomers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Opens the Appointments Menu.
     *
     * @param actionEvent - the appointments button is clicked
     */
    public void onAppointments(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentsMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Schedule");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Opens the Reports Menu.
     *
     * @param actionEvent - the reports button is clicked
     */
    public void onReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportsMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Logs out the current user and returns to the LogIn page.
     *
     * @param actionEvent - the logout button is clicked
     */
    public void onLogout(ActionEvent actionEvent) throws IOException {
        UserDAO.logoutUser(userID);
        Parent root = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Reports");
        stage.setScene(scene);
        stage.show();
    }
}
