package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CountryDAO {
    //read only
    public static ObservableList<Country> allCountries;

    public static ObservableList<Country> getAllCountries() {
        allCountries = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM countries";

            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Country_ID");
                String name = rs.getString("Country");

                Country country = new Country(id, name);

                allCountries.add(country);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return allCountries;
    }

    public static Country getCountryByID(int countryID) {
        //check if allCountries is initialized
        if (allCountries == null) {
            getAllCountries();
        }

        Country selected = null;

        for (int i = 0; i < allCountries.size(); i++) {
            Country current = allCountries.get(i);
            if (current.getCountryID() == countryID) {
                selected = current;
            }
        }
        return selected;
    }
}
