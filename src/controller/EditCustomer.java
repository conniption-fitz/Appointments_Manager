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

public class EditCustomer implements Initializable {
    public ComboBox<Country> countryCombo;
    public ComboBox<Division> divisionCombo;
    public Button saveButton;
    public Button cancelButton;
    public TextField nameTF;
    public TextField phoneTF;
    public TextField addressTF;
    public TextField zipTF;
    public TextField customerIDTF;
    private Customer selectCustomer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {    }

    /**
     * Loads the user-selected Customer data into the form.
     *
     * @param customer - the customer to be edited
     */
    public void loadCustomer(Customer customer) {
        selectCustomer = customer;

        countryCombo.setItems(CountryDAO.getAllCountries());
        divisionCombo.setItems(DivisionDAO.getAllDivisions());
        Division selectedDivision = DivisionDAO.getDivisionByID(customer.getDivisionID());
        Country currentCountry = CountryDAO.getCountryByID(selectedDivision.getCountryID());
        countryCombo.setValue(currentCountry);
        divisionCombo.setValue(selectedDivision);
        divisionCombo.setItems(DivisionDAO.getDivisionsByCountry(currentCountry.getCountryID()));
        customerIDTF.setText(Integer.toString(customer.getCustomerID()));
        nameTF.setText(customer.getCustomerName());
        phoneTF.setText(customer.getPhoneNum());
        addressTF.setText(customer.getAddress());
        zipTF.setText(customer.getZipCode());
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
     * Updates the Customer. First, all data is validated. Then the Customer is updated and inserted into the database.
     *
     * @param actionEvent - the save button is clicked
     */
    public void onSave(ActionEvent actionEvent) throws IOException, SQLException {
        boolean valid = true;
        int isSaved = -1;

        try {
            Division selectedDivision = divisionCombo.getValue();
            selectCustomer.setDivisionID(selectedDivision.getDivisionID());
        } catch (NullPointerException e) {
            AlertsManager.missingSelection(" division");
            valid = false;
        }

        if (nameTF.getText().isEmpty()) {
            AlertsManager.missingInfo("name");
            valid = false;
        } else {
            selectCustomer.setCustomerName(nameTF.getText());
        }
        if (phoneTF.getText().isEmpty()) {
            AlertsManager.missingInfo("phone number");
            valid = false;
        } else {
            selectCustomer.setPhoneNum(phoneTF.getText());
        }
        if (addressTF.getText().isEmpty()) {
            AlertsManager.missingInfo("address");
            valid = false;
        } else {
            selectCustomer.setAddress(addressTF.getText());
        }
        if (zipTF.getText().isEmpty()) {
            AlertsManager.missingInfo("zip code");
            valid = false;
        } else {
            selectCustomer.setZipCode(zipTF.getText());
        }
        if (valid) {
            isSaved = CustomerDAO.updateCustomer(selectCustomer);
            if (isSaved > 0) {
                AlertsManager.saveSuccess(selectCustomer.getCustomerName());
            } else {
                AlertsManager.saveFailed(selectCustomer.getCustomerName());
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
     * Closes the Edit Customer form and returns to the Customer Menu.
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
