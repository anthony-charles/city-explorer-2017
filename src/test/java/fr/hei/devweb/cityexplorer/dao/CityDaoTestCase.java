package fr.hei.devweb.cityexplorer.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import fr.hei.devweb.cityexplorer.daos.CityDao;
import fr.hei.devweb.cityexplorer.daos.DataSourceProvider;
import fr.hei.devweb.cityexplorer.pojos.City;

public class CityDaoTestCase extends AbstractDaoTestCase {
	
	private CityDao cityDao = new CityDao();

	@Override
	public void insertDataSet(Statement statement) throws Exception {
		statement.executeUpdate("INSERT INTO city(id, name, summary, deleted) VALUES(1, 'City 1', 'Summary 1', 0)");
		statement.executeUpdate("INSERT INTO city(id, name, summary, deleted) VALUES(2, 'City 2', 'Summary 2', 0)");
		statement.executeUpdate("INSERT INTO city(id, name, summary, deleted) VALUES(3, 'City 3', 'Summary 3', 1)");
	}

	@Test
	public void shouldListCities() throws Exception {
		// WHEN
		List<City> cities = cityDao.listCities();
		// THEN
		Assertions.assertThat(cities).hasSize(2);
		Assertions.assertThat(cities).extracting("id", "name", "summary").containsOnly(
				Assertions.tuple(1, "City 1", "Summary 1"),
				Assertions.tuple(2, "City 2", "Summary 2")
		);
	}

	@Test
	public void shouldGetCity() throws Exception {
		// WHEN
		City city = cityDao.getCity(1);
		// THEN
		Assertions.assertThat(city).isNotNull();
		Assertions.assertThat(city.getId()).isEqualTo(1);
		Assertions.assertThat(city.getName()).isEqualTo("City 1");
		Assertions.assertThat(city.getSummary()).isEqualTo("Summary 1");
	}

	@Test
	public void shouldNotGetDeletedCity() {
		// WHEN
		City city = cityDao.getCity(3);
		// THEN
		Assertions.assertThat(city).isNull();
	}
	
	@Test
	public void shouldAddCity() throws Exception {
		// GIVEN 
		City newCity = new City(null, "My new city", "Summary for my new city");
		// WHEN
		cityDao.addCity(newCity);
		// THEN
		try(Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM city WHERE name='My new city'")){
			Assertions.assertThat(resultSet.next()).isTrue();
			Assertions.assertThat(resultSet.getInt("id")).isNotNull();
			Assertions.assertThat(resultSet.getString("name")).isEqualTo("My new city");
			Assertions.assertThat(resultSet.getString("summary")).isEqualTo("Summary for my new city");
			Assertions.assertThat(resultSet.next()).isFalse();
		}
	}

	@Test
	public void shouldDeleteCity() throws SQLException {
		// WHEN
		cityDao.deleteCity(2);
		// THEN
		try(Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM city WHERE id=2")){
			Assertions.assertThat(resultSet.next()).isTrue();
			Assertions.assertThat(resultSet.getBoolean("deleted")).isTrue();
			Assertions.assertThat(resultSet.next()).isFalse();
		}
	}
}
