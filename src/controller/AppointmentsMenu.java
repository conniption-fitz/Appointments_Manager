/**
 *
 * @author Tory Fitzgerald, id: 000559078
 */

package controller;

import helper.AlertsManager;
import helper.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class AppointmentsMenu implements Initializable {
    public TableColumn<Appointment, Integer> appointmentIDCol;
    public TableColumn<Appointment, String> titleCol;
    public TableColumn<Appointment, String> descriptionCol;
    public TableColumn<Appointment, String> locationCol;
    public TableColumn<Appointment, String> contactCol;
    public TableColumn<Appointment, String> typeCol;
    public TableColumn<Appointment, String> startCol;
    public TableColumn<Appointment, String> endCol;
    public TableColumn<Appointment, Integer> customerIDCol;
    public TableColumn<Appointment, Integer> userIDCol;
    public Button exitButton;
    public Button deleteButton;
    public Button editButton;
    public Button addButton;
    public ToggleGroup appointmentView;
    public TableView<Appointment> appointmentTable;
    public RadioButton monthRadio;
    public RadioButton weekRadio;
    LocalDateTime today;
    ZoneId zone;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //display appointments in local timezone
        today = LocalDateTime.now();
        zone = ZoneId.systemDefault();
        ObservableList<Appointment> currentMonth = AppointmentDAO.getAppointmentsByMonth(today, zone);
        appointmentTable.setItems(currentMonth);
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startZonedString"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endZonedString"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }

    /**
     * Closes the Appointments Menu and returns to the Dashboard.
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

    /**
     * Deletes a user-selected Appointment. First, a confirmation pop-up asks the user to confirm they want to delete
     * the selected Appointment. If the user selects OK, the appointment is deleted and removed from the database.
     *
     * @param actionEvent - the delete button is clicked
     */
    public void onDelete(ActionEvent actionEvent) {
        Appointment selected = appointmentTable.getSelectionModel().getSelectedItem();
        int isDeleted = -1;
        boolean confirm = AlertsManager.confirmDelete("APPOINTMENT " + selected.getAppointmentID() + ": "
                + selected.getType());
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        if (confirm) {
            isDeleted = AppointmentDAO.deleteAppointment(selected);
            if (isDeleted > 0) {
                AlertsManager.deleteSuccess("APPOINTMENT " + selected.getAppointmentID() + ": "
                        + selected.getType());
                if (monthRadio.isSelected()) {
                    appointments = AppointmentDAO.getAppointmentsByMonth(today, zone);
                } else if (weekRadio.isSelected()) {
                    appointments = AppointmentDAO.getAppointmentsByWeek(today, zone);
                }
                appointmentTable.setItems(appointments);
            } else {
                AlertsManager.deleteFailed("APPOINTMENT " + selected.getAppointmentID() + ": "
                        + selected.getType());
            }
        }
    }

    /**
     * Opens the Edit Appointment form. The application checks to ensure an appointment is selected, and generates an
     * error message if no appointment is selected.
     *
     * @param actionEvent - the edit button is clicked
     */
    public void onEdit(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/EditAppointment.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        EditAppointment controller = loader.getController();

        try {
            Appointment selected = appointmentTable.getSelectionModel().getSelectedItem();
            controller.loadAppointment(selected);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Edit Customer");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            AlertsManager.missingSelection("n appointment.");
        }
    }

    /**
     * Opens the Add Appointment form.
     *
     * @param actionEvent - the add button is clicked
     */
    public void onAdd(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Displays all appointments in the current month.
     *
     * @param actionEvent - the month radio button is clicked
     */
    public void onMonthRadio(ActionEvent actionEvent) {
        ObservableList<Appointment> currentMonth = AppointmentDAO.getAppointmentsByMonth(today, zone);
        appointmentTable.setItems(currentMonth);
    }

    /**
     * Displays all the appointments in the current week.
     *
     * @param actionEvent - the week radio button is clicked
     */
    public void onWeekRadio(ActionEvent actionEvent) {
        ObservableList<Appointment> currentWeek = AppointmentDAO.getAppointmentsByWeek(today, zone);
        appointmentTable.setItems(currentWeek);
    }
}
