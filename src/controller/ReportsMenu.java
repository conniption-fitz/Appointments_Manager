package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

import java.io.IOException;

public class ReportsMenu {
    public TableColumn appointmentIDCol;
    public TableColumn titleCol;
    public TableColumn typeCol;
    public TableColumn descriptionCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn customerIDCol;
    public ComboBox contactCombo;
    public ComboBox monthCombo;
    public ComboBox customerCombo;
    public Label appointmentTypeNumLabel;
    public Label divisionCustomerNumLabel;
    public Button exitButton;

    public void onContact(ActionEvent actionEvent) {
    }

    public void onMonth(ActionEvent actionEvent) {
    }

    public void onCustomer(ActionEvent actionEvent) {
    }

    public void onExit(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }
}
