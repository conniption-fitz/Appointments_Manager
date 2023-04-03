import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

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



        //test translation settings
        //no forward slash in resource bundles, need a default language
        //global language - static variable RB to hold locale
        Locale.setDefault(new Locale("fr", "FR"));

        //try {
            ResourceBundle rb = ResourceBundle.getBundle("resources/Translate", Locale.getDefault());

            if (Locale.getDefault().getLanguage().equals("fr")) {
                System.out.println(rb.getString("hello") + " " + rb.getString("world"));
            }
        /*}
        catch (Exception e) {
            System.out.println("hello world");
        }

         */


        launch(args);


        JDBC.closeConnection();
    }
}
