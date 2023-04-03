package controller;

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

    Division selectedDivision = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCombo.setItems(CountryDAO.getAllCountries());
        divisionCombo.setItems(DivisionDAO.getAllDivisions());
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
        int customerID = -1;
        String name = nameTF.getText();
        String phone = phoneTF.getText();
        String address = addressTF.getText();
        String zip = zipTF.getText();

        int divisionID = selectedDivision.getDivisionID();

        //validate data, try/catch
        boolean valid = true;
        int isSaved = -1;

        if (valid) {
            //set customer data
            Customer customer = new Customer(customerID, name, address, zip, phone, divisionID);
            //run INSERT query
            isSaved = CustomerDAO.insertCustomer(customer);

            if (isSaved > 0) {
                //alert
                System.out.println("Customer add successful.");
            }
        }
        else {
            //alert
            System.out.println("Customer add failed.");
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
