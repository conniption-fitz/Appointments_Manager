/**
 *
 * @author Tory Fitzgerald, id: 000559078
 */

package helper;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Appointment;
import model.Customer;

import java.time.ZonedDateTime;
import java.util.Optional;

public abstract class AlertsManager {
    /**
     * Generates an alert to ask the user to confirm they want to delete an item.
     *
     * @param item - the item to be deleted
     */
    public static boolean confirmDelete(String item) {
        boolean delete = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + item.toUpperCase()
                + "?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Delete " + item.toUpperCase() + "?");
        Optional<ButtonType> response = alert.showAndWait();
        if(response.get() == ButtonType.YES) {
            delete = true;
        }
        return delete;
    }

    /**
     * Generates an alert that an item could not be deleted.
     *
     * @param item - the item to be deleted
     */
    public static void deleteFailed(String item) {
        Alert alert = new Alert(Alert.AlertType.ERROR, item.toUpperCase() + " could not be deleted.");
        alert.setTitle("Error");
        alert.show();
    }

    /**
     * Generates an alert that an item was successfully deleted.
     *
     * @param item - the item to be deleted
     */
    public static void deleteSuccess(String item) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, item.toUpperCase() + " deleted.");
        alert.setTitle("Success");
        alert.show();
    }

    /**
     * Generates an alert that an item could not be saved.
     *
     * @param item - the item to be saved
     */
    public static void saveFailed(String item) {
        Alert alert = new Alert(Alert.AlertType.ERROR, item.toUpperCase() + " could not be saved.");
        alert.setTitle("Error");
        alert.show();
    }

    /**
     * Generates an alert that an item was successfully saved.
     *
     * @param item - the item to be saved
     */
    public static void saveSuccess(String item) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, item.toUpperCase() + " saved.");
        alert.setTitle("Success");
        alert.show();
    }

    /**
     * Generates an alert that the user has not selected an item.
     *
     * @param item - the item to be selected
     */
    public static void missingSelection(String item) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a" + item + ".");
        alert.setTitle("Invalid Selection");
        alert.showAndWait();
    }

    /**
     * Generates an alert that a field is missing information.
     *
     * @param item - the missing item
     */
    public static void missingInfo(String item) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a " + item + ".");
        alert.setTitle("Missing Information");
        alert.showAndWait();
    }

    /**
     * Generates an alert when an appointment end time is before the start time.
     */
    public static void appointmentTime() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "End time cannot occur before start time.");
        alert.setTitle("Error");
        alert.showAndWait();
    }

    /**
     * Generates an error message.
     *
     * @param title - the title of the error message
     * @param message - the error message to be displayed
     */
    public static void errorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setTitle(title);
        alert.showAndWait();
    }

    /**
     * Generates an error message when an appointment does not fall within office hours.
     */
    public static void officeHours() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot schedule appointment outside of office hours: 8:00am - " +
                "10:00pm EST");
        alert.setTitle("Office Hours");
        alert.showAndWait();
    }

    /**
     * Generates an error message when an appointment overlaps with a Customer's existing appointment.
     *
     * @param customer - the customer attached to the appointment
     * @param start - the zoned appointment start time
     * @param end - the zoned appointment end time
     */
    public static void overlappingAppointment(Customer customer, ZonedDateTime start, ZonedDateTime end) {
        String text = "Cannot schedule appointment because " +
                customer.getCustomerName() + "\nalready has an appointment on " + start.toLocalDate() + "\nfrom "
                + start.toLocalTime() + " to " + end.toLocalTime();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Overlapping Appointments");
        alert.setContentText(text);
        alert.showAndWait();
    }

    /**
     * Generates an alert that the user has an appointment within 15 minutes, and displays the appointment.
     *
     * @param appointment - the upcoming appointment
     */
    public static void upcomingAppointment(Appointment appointment) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have an upcoming appointment:\n"
                + "ID: " + appointment.getAppointmentID() + "\nTitle: " + appointment.getTitle()
                + "\nDate:" + appointment.getStartLocalDateTime().toLocalDate() +"\nTime: "
                + appointment.getStartLocalDateTime().toLocalTime() + "-"
                + appointment.getEndLocalDateTime().toLocalTime());
        alert.setTitle("Upcoming Appointment");
        alert.show();
    }
}
