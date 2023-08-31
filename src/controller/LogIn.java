/**
 *
 * @author Tory Fitzgerald, id: 000559078
 */

package controller;

import helper.AlertsManager;
import helper.UserDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class LogIn implements Initializable {
    public PasswordField passwordTF;
    public TextField usernameTF;
    public Button loginButton;
    public Label promptLabel;
    public Label timeZoneLabel;
    private ResourceBundle rb;
    private String fileName;
    private FileWriter fileWriter;
    private PrintWriter outputFile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //create login activity file
        fileName = "src/files/login_activity.txt";
        try {
            fileWriter = new FileWriter(fileName, true);
            outputFile = new PrintWriter(fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            rb = ResourceBundle.getBundle("resources/Translate", Locale.getDefault());
            //set labels
            promptLabel.setText(rb.getString("prompt"));
            usernameTF.setPromptText(rb.getString("username"));
            passwordTF.setPromptText(rb.getString("password"));
            loginButton.setText(rb.getString("login"));
            timeZoneLabel.setText(ZoneId.systemDefault().getDisplayName(TextStyle.SHORT, Locale.getDefault()));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Logs the user into the application. First, the user information is validated, then the user is logged into the
     * application and the user login status is updated.
     *
     * @param actionEvent - the login button is clicked
     */
    public void onLogIn(ActionEvent actionEvent) throws IOException, SQLException {
        boolean valid = false;
        boolean notEmpty = true;
        String loginActivity;
        //check user credentials
        ObservableList<User> allUsers = UserDAO.getAllUsers();
        String username = usernameTF.getText();
        String password = passwordTF.getText();

        //check if user info is empty
        if(username.isEmpty()) {
            notEmpty = false;
            AlertsManager.errorMessage(rb.getString("missingInfoTitle"), rb.getString("missingUsernameMessage"));
        }
        if(password.isEmpty()) {
            notEmpty = false;
            AlertsManager.errorMessage(rb.getString("missingInfoTitle"), rb.getString("missingPasswordMessage"));
        }

        if(notEmpty) {
            //validate username and password
            for (User current : allUsers) {
                if (username.equals(current.getUserName()) && password.equals(current.getPassword())) {
                    valid = true;
                    current.setLoggedInStatus(true);
                }
            }
            if (!valid) {
                AlertsManager.errorMessage(rb.getString("invalidTitle"), rb.getString("invalidMessage"));
            }
            //record login activity
            Instant time = Instant.now();
            loginActivity = "LOGIN ATTEMPT: " + username + " | " + password + " | " + time + " | SUCCESS = " + valid;
            outputFile.println(loginActivity);
        }

        if (valid) {
            //close file
            outputFile.close();
            //load next scene
            Parent root = FXMLLoader.load(getClass().getResource("/view/Dashboard.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Dashboard");
            stage.setScene(scene);
            stage.show();
        }
    }
}
