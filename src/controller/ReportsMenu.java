/**
 *
 * @author Tory Fitzgerald, id: 000559078
 */

package controller;

import helper.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportsMenu implements Initializable {
    public TableColumn<Integer, Appointment> appointmentIDCol;
    public TableColumn<String, Appointment> titleCol;
    public TableColumn<String, Appointment> typeCol;
    public TableColumn<String, Appointment> descriptionCol;
    public TableColumn<String, Appointment> startCol;
    public TableColumn<String, Appointment> endCol;
    public TableColumn<Integer, Appointment> customerIDCol;
    public ComboBox<Contact> contactCombo;
    public ComboBox<String> monthCombo;
    public ComboBox<Customer> customerCombo;
    public Label appointmentTypeNumLabel;
    public Label divisionCustomerNumLabel;
    public Button exitButton;
    public TableView<Appointment> appointmentTable;
    public ComboBox<String> typeCombo;
    public ComboBox<Division> divisionCombo;
    public ComboBox<Country> countryCombo;
    private String month;

    /**
     * Displays all Appointments associated with a user-selected Contact.
     *
     * @param actionEvent - a contact is selected
     */
    public void onContact(ActionEvent actionEvent) {
        Contact selected = contactCombo.getSelectionModel().getSelectedItem();
        ObservableList<Appointment> contactAppointments = AppointmentDAO.getAppointmentsByContact(selected);
        appointmentTable.setItems(contactAppointments);
    }

    /**
     * Records the user-selected month.
     *
     * @param actionEvent - a month is selected
     */
    public void onMonth(ActionEvent actionEvent) {
        month = monthCombo.getSelectionModel().getSelectedItem();
    }

    /**
     * Displays the total number of Appointments by Type and Month. First, the application validates the user selections.
     * Then the total is updated with the correct number of appointments.
     *
     * @param actionEvent - a type is selected
     */
    public void onType(ActionEvent actionEvent) {
        int total = 0;
        String type = typeCombo.getSelectionModel().getSelectedItem();
        if (month == null) {
            AlertsManager.missingSelection(" month.");
        } else {
            total = AppointmentDAO.getTotalByType(month, type);
            appointmentTypeNumLabel.setText(Integer.toString(total));
        }
    }

    /**
     * Sets the Division Combo Box values to the divisions within the user-selected country.
     *
     * @param actionEvent - a country is selected
     */
    public void onCountry(ActionEvent actionEvent) {
        Country selected = countryCombo.getSelectionModel().getSelectedItem();
        ObservableList<Division> divisions = DivisionDAO.getDivisionsByCountry(selected.getCountryID());
        divisionCombo.setItems(divisions);
    }

    /**
     * Displays the total number of Customers in the user-selected Division. First, the application validates the user
     * selection, then updates the total number of Customers.
     *
     * @param actionEvent - a division is selected
     */
    public void onDivision(ActionEvent actionEvent) {
        Division selected = divisionCombo.getSelectionModel().getSelectedItem();
        int total = CustomerDAO.getTotalByDivision(selected.getDivisionID());
        divisionCustomerNumLabel.setText(Integer.toString(total));
    }

    /**
     * Closes the Reports Menu and returns to the Dashboard.
     *
     * @param actionEvent - the exit button is clicked
     */
    public void onExit(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Appointment> allAppointments = AppointmentDAO.getAllAppointments();
        appointmentTable.setItems(allAppointments);
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startZonedString"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endZonedString"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        contactCombo.setItems(ContactDAO.getAllContacts());
        monthCombo.setItems(TimeManager.getMonths());
        typeCombo.setItems(AppointmentDAO.getTypes());
        countryCombo.setItems(CountryDAO.getAllCountries());
    }
}
