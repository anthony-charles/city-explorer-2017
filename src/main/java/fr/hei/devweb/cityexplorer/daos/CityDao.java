package fr.hei.devweb.cityexplorer.daos;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.hei.devweb.cityexplorer.exceptions.CityExplorerRuntimeException;
import fr.hei.devweb.cityexplorer.pojos.City;
import sun.rmi.runtime.Log;

public class CityDao {

	public List<City> listCities() {
		List<City> cities = new ArrayList<City>();

		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM city ORDER BY name")) {
			while (resultSet.next()) {
				cities.add(
						new City(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("summary")));
			}
		} catch (SQLException e) {
			throw new CityExplorerRuntimeException("Error when getting cities", e);
		}

		return cities;
	}

	public City getCity(Integer id) {
		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM city WHERE id = ?")) {
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					return new City(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("summary"));
				}
			}
		} catch (SQLException e) {
			throw new CityExplorerRuntimeException("Error when getting cities", e);
		}
		return null;
	}
	
	public void addCity(City newCity) {
		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
				PreparedStatement statement = connection.prepareStatement("INSERT INTO city(name, summary, picture) VALUES (?, ?, ?)")) {
			statement.setString(1, newCity.getName());
			statement.setString(2, newCity.getSummary());
			statement.setString(3, "C:/DEV/HEI/city-explorer/"+newCity.getName()+".jpg");
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new CityExplorerRuntimeException("Error when getting cities", e);
		}
	}

	public String getImagePath(Integer id) {
		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT picture FROM city WHERE id=?")) {
			statement.setInt(1,id);
			try (ResultSet result = statement.executeQuery()) {
				while (result.next()) {
					if (result.getString("picture") == null ){
						return Paths.get(this.getClass().getClassLoader().getResource("city-no-photo.png").toURI()).toString();
					}else{
						return result.getString("picture");
					}
				}
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
