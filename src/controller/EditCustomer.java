package controller;

import helper.CountryDAO;
import helper.CustomerDAO;
import helper.DivisionDAO;
import helper.JDBC;
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
    private Division selectedDivision;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {    }

    public void loadCustomer(Customer customer) {
        selectCustomer = customer;

        //set country and division combo boxes
        countryCombo.setItems(CountryDAO.getAllCountries());
        divisionCombo.setItems(DivisionDAO.getAllDivisions());

        selectedDivision = DivisionDAO.getDivisionByID(customer.getDivisionID());
        Country currentCountry = CountryDAO.getCountryByID(selectedDivision.getCountryID());

        //set current country & division
        countryCombo.setValue(currentCountry);
        divisionCombo.setValue(selectedDivision);

        //set divisions for current country
        divisionCombo.setItems(DivisionDAO.getDivisionsByCountry(currentCountry.getCountryID()));

        customerIDTF.setText(Integer.toString(customer.getCustomerID()));
        nameTF.setText(customer.getCustomerName());
        phoneTF.setText(customer.getPhoneNum());
        addressTF.setText(customer.getAddress());
        zipTF.setText(customer.getZipCode());

    }
    public void onCountry(ActionEvent actionEvent) {
        Country selected = countryCombo.getSelectionModel().getSelectedItem();

        ObservableList<Division> selectDivision = DivisionDAO.getDivisionsByCountry(selected.getCountryID());

        divisionCombo.setItems(selectDivision);
    }

    public void onDivision(ActionEvent actionEvent) {
        selectedDivision = divisionCombo.getSelectionModel().getSelectedItem();
    }

    public void onSave(ActionEvent actionEvent) throws IOException, SQLException {
        String name = nameTF.getText();
        String phone = phoneTF.getText();
        String address = addressTF.getText();
        String zip = zipTF.getText();

        int isSaved = -1;

        //country and division

        boolean valid = true;

        if (valid) {
            selectCustomer.setCustomerName(name);
            selectCustomer.setPhoneNum(phone);
            selectCustomer.setAddress(address);
            selectCustomer.setZipCode(zip);
            selectCustomer.setDivisionID(selectedDivision.getDivisionID());

            isSaved = CustomerDAO.updateCustomer(selectCustomer);

            if (isSaved > 0) {
                //alert
                System.out.println("Customer update successful.");
            }
        }
        else {
            //alert
            System.out.println("Customer update failed.");
        }

        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();

    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/CustomerMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Customers");
        stage.setScene(scene);
        stage.show();
    }


}
