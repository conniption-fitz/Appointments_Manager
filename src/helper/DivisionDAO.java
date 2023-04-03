package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DivisionDAO {
    public static ObservableList<Division> allDivisions;
    //read only
    public static ObservableList<Division> getAllDivisions() {
        allDivisions = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM first_level_divisions";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String name = rs.getString("Division");
                int countryID = rs.getInt("Country_ID");

                Division division = new Division(divisionID, name, countryID);

                allDivisions.add(division);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return allDivisions;
    }

    public static ObservableList<Division> getDivisionsByCountry(int selectCountryID) {
        ObservableList<Division> divisionsUS = FXCollections.observableArrayList();
        ObservableList<Division> divisionsUK = FXCollections.observableArrayList();
        ObservableList<Division> divisionsCanada = FXCollections.observableArrayList();

        //check if allDivisions is initialized
        if (allDivisions == null) {
            getAllDivisions();
        }

        for (Division allDivision : allDivisions) {
            if (allDivision.getCountryID() == 1) {
                divisionsUS.add(allDivision);
            }
            else if (allDivision.getCountryID() == 2) {
                divisionsUK.add(allDivision);
            }
            else if (allDivision.getCountryID() == 3) {
                divisionsCanada.add(allDivision);
            }
        }

        if (selectCountryID == 1) {
            return divisionsUS;
        }
        else if (selectCountryID == 2) {
            return divisionsUK;
        }
        else if (selectCountryID == 3) {
            return divisionsCanada;
        }
        else return null;
    }

    public static Division getDivisionByID(int divisionID) {
        //check if allDivisions is initialized
        if (allDivisions == null) {
            getAllDivisions();
        }

        for (Division allDivision : allDivisions) {
            if (allDivision.getDivisionID() == divisionID) {
                return allDivision;
            }
        }
        return null;
    }
}

