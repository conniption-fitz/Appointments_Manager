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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerMenu implements Initializable {
    public TableColumn<Customer, Integer> customerIDCol;
    public TableColumn<Customer, String> nameCol;
    public TableColumn<Customer, String> phoneCol;
    public TableColumn<Customer, String> addressCol;
    public TableColumn<Customer, Integer> divisionCol;
    public TableColumn<Customer, String> zipCol;
    public Button exitButton;
    public Button deleteButton;
    public Button editButton;
    public Button addButton;
    public TableView<Customer> customerTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Customer> allCustomers = CustomerDAO.getAllCustomers();

        customerTable.setItems(allCustomers);

        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
        zipCol.setCellValueFactory(new PropertyValueFactory<>("zipCode"));

    }

    /**
     * Closes the Customer Menu and returns to the Dashboard.
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
     * Deletes a user-selected Customer. The application checks to confirm that the user wants to delete the customer.
     * If the user clicks OK, the application checks to see if the customer has any appointments, and deletes them,
     * then deletes the Customer from the database.
     *
     * @param actionEvent - the delete button is clicked
     */
    public void onDelete(ActionEvent actionEvent) throws SQLException {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        int appointmentsDeleted = -1;
        int customerDeleted = -1;

        boolean confirm = AlertsManager.confirmDelete(selected.getCustomerName());
        if(confirm) {
            appointmentsDeleted = AppointmentDAO.deleteAppointmentByCustomerID(selected.getCustomerID());
            if (appointmentsDeleted == -1) {
                AlertsManager.deleteFailed(selected.getCustomerName());
            } else {
                customerDeleted = CustomerDAO.deleteCustomer(selected);
                if (customerDeleted == -1) {
                    AlertsManager.deleteFailed(selected.getCustomerName());
                } else {
                    AlertsManager.deleteSuccess(selected.getCustomerName());
                }
            }
        }
        ObservableList<Customer> allCustomers = CustomerDAO.getAllCustomers();
        customerTable.setItems(allCustomers);
    }

    /**
     * Opens the Edit Customer form and loads the user-selected Customer.
     *
     * @param actionEvent - the edit button is clicked
     */
    public void onEdit(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/EditCustomer.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        EditCustomer controller = loader.getController();
        try {
            Customer selected = customerTable.getSelectionModel().getSelectedItem();
            controller.loadCustomer(selected);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Edit Customer");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            AlertsManager.missingSelection(" customer.");
        }
    }

    /**
     * Opens the Add Customer form.
     *
     * @param actionEvent - the add button is clicked
     */
    public void onAdd(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

}
