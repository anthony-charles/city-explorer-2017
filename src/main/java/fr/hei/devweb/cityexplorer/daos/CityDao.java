package fr.hei.devweb.cityexplorer.daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.hei.devweb.cityexplorer.exceptions.CityExplorerRuntimeException;
import fr.hei.devweb.cityexplorer.pojos.City;

public class CityDao {

	public List<City> listCities() {
		List<City> cities = new ArrayList<City>();
		
		try(Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM city ORDER BY name")) {
			while(resultSet.next()) {
				cities.add(new City(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("summary")));
			}
		} catch (SQLException e) {
			throw new CityExplorerRuntimeException("Error when getting cities", e);
		}
		
		return cities;
	}
}
