package fr.hei.devweb.cityexplorer.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import fr.hei.devweb.cityexplorer.pojos.Country;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import fr.hei.devweb.cityexplorer.daos.CityDao;
import fr.hei.devweb.cityexplorer.daos.DataSourceProvider;
import fr.hei.devweb.cityexplorer.pojos.City;

public class CityDaoTestCase extends AbstractDaoTestCase {
	
	private CityDao cityDao = new CityDao();

	@Override
	public void insertDataSet(Statement statement) throws Exception {
		statement.executeUpdate("INSERT INTO city(id, name, summary, country) VALUES(1, 'City 1', 'Summary 1', 'FR')");
		statement.executeUpdate("INSERT INTO city(id, name, summary, country) VALUES(2, 'City 2', 'Summary 2', 'UK')");
		statement.executeUpdate("INSERT INTO city(id, name, summary, country) VALUES(3, 'City 3', 'Summary 3', 'FR')");
	}

	@Test
	public void shouldListCities() throws Exception {
		// WHEN
		List<City> cities = cityDao.listCities();
		// THEN
		Assertions.assertThat(cities).hasSize(3);
		Assertions.assertThat(cities).extracting("id", "name", "summary", "country").containsOnly(
				Assertions.tuple(1, "City 1", "Summary 1", Country.FR),
				Assertions.tuple(2, "City 2", "Summary 2", Country.UK),
				Assertions.tuple(3, "City 3", "Summary 3", Country.FR)
		);
	}

	@Test
	public void shouldListCitiesByCountry() throws Exception {
		// WHEN
		List<City> cities = cityDao.listCitiesByCountry(Country.FR);
		// THEN
		Assertions.assertThat(cities).hasSize(2);
		Assertions.assertThat(cities).extracting("id", "name", "summary", "country").containsOnly(
				Assertions.tuple(1, "City 1", "Summary 1", Country.FR),
				Assertions.tuple(3, "City 3", "Summary 3", Country.FR)
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
		Assertions.assertThat(city.getCountry()).isEqualTo(Country.FR);
		
	}
	
	@Test
	public void shouldAddCity() throws Exception {
		// GIVEN 
		City newCity = new City(null, "My new city", "Summary for my new city", Country.UK);
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
			Assertions.assertThat(resultSet.getString("country")).isEqualTo("UK");
			Assertions.assertThat(resultSet.next()).isFalse();
		}
	}
}
