/**
 *
 * @author Tory Fitzgerald, id: 000559078
 */

package controller;

import helper.AlertsManager;
import helper.CountryDAO;
import helper.CustomerDAO;
import helper.DivisionDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomer implements Initializable {
    public ComboBox<Country> countryCombo;
    public ComboBox<Division> divisionCombo;
    public Button saveButton;
    public Button cancelButton;
    public TextField nameTF;
    public TextField phoneTF;
    public TextField addressTF;
    public TextField zipTF;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCombo.setItems(CountryDAO.getAllCountries());
        divisionCombo.setItems(DivisionDAO.getAllDivisions());

    }

    /**
     * Sets the Division Combo Box values to the divisions within the user-selected country.
     *
     * @param actionEvent - a country is selected
     */
    public void onCountry(ActionEvent actionEvent) {
        Country selected = countryCombo.getSelectionModel().getSelectedItem();
        ObservableList<Division> selectDivision = DivisionDAO.getDivisionsByCountry(selected.getCountryID());
        divisionCombo.setItems(selectDivision);
    }

    /**
     * Creates a new Customer and adds it to the database. All customer information is validated, checking to ensure
     * that there are no empty text fields or unselected combo boxes. Then a new Customer object is created and inserted
     * into the database.
     *
     * @param actionEvent - the save button is clicked
     */
     public void onSave(ActionEvent actionEvent) throws IOException, SQLException {
        int customerID = -1;
        String name = null;
        String phone = null;
        String address = null;
        String zip = null;
        int divisionID = -1;
        boolean valid = true;
        int isSaved = -1;

        try {
            Division selectedDivision = divisionCombo.getValue();
            divisionID = selectedDivision.getDivisionID();
        } catch (NullPointerException e) {
            AlertsManager.missingSelection(" division");
            valid = false;
        }

        if (nameTF.getText().isEmpty()) {
            AlertsManager.missingInfo("name");
            valid = false;
        } else {
            name = nameTF.getText();
        }
        if (phoneTF.getText().isEmpty()) {
            AlertsManager.missingInfo("phone number");
            valid = false;
        } else {
            phone = phoneTF.getText();
        }
        if (addressTF.getText().isEmpty()) {
            AlertsManager.missingInfo("address");
            valid = false;
        } else {
            address = addressTF.getText();
        }
        if (zipTF.getText().isEmpty()) {
            AlertsManager.missingInfo("zip code");
            valid = false;
        } else {
            zip = zipTF.getText();
        }

        if (valid) {
            Customer customer = new Customer(customerID, name, address, zip, phone, divisionID);
            isSaved = CustomerDAO.insertCustomer(customer);
            if (isSaved > 0) {
                AlertsManager.saveSuccess(customer.getCustomerName());
            } else {
                AlertsManager.saveFailed(customer.getCustomerName());
            }
            Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Customers");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Closes the Add Customer form and returns to the Customer Menu.
     *
     * @param actionEvent - the cancel button is clicked
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }
}
