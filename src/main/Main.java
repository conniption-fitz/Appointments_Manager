package main; /**
 *
 * @author Tory Fitzgerald, id: 000559078
 * Javadocs location: \Fitzgerald_V_C195_V2\javadoc
 */

import helper.JDBC;
import helper.UserDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LogIn.fxml"));
        stage.setTitle("Log In");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        //initialize users
        UserDAO.setAllUsers();
        launch(args);
        JDBC.closeConnection();
        //log out all users on exit
    }
}
