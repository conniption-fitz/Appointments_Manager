package controller;

import helper.AppointmentDAO;
import helper.ContactDAO;
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
import model.Contact;
import model.Customer;

import java.io.IOException;
import java.net.URL;
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
    private static ObservableList<Appointment> currentMonth;
    private static String year;
    private static String month;
    private static String day;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //get current date from local time
        year = "2020";
        month = "05";
        day = "01";

        System.out.println("Initializing appointments menu.");
        currentMonth = AppointmentDAO.getAppointmentsByMonth(month, year);

        appointmentTable.setItems(currentMonth);

        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactID")); //get contact name
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));


    }
    public void onExit(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    public void onDelete(ActionEvent actionEvent) {
        Appointment selected = appointmentTable.getSelectionModel().getSelectedItem();
        int isDeleted = -1;

        //add alert
        boolean valid = true;

        if (valid) {
            isDeleted = AppointmentDAO.deleteAppointment(selected);

            if (isDeleted > 0) {
                System.out.println("Appointment deleted.");
            }
            else {
                System.out.println("Deletion failed.");
            }
        }
    }

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
            //noSelection.showAndWait();
            System.out.println("No selection.");
        }
    }

    public void onAdd(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }


    public void onMonthRadio(ActionEvent actionEvent) {
        currentMonth = AppointmentDAO.getAppointmentsByMonth(month, year);

        appointmentTable.setItems(currentMonth);
    }

    public void onWeekRadio(ActionEvent actionEvent) {
        ObservableList<Appointment> currentWeek = AppointmentDAO.getAppointmentsByWeek(year, month, day);

        appointmentTable.setItems(currentWeek);
    }
}
