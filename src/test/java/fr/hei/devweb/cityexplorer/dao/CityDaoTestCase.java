package fr.hei.devweb.cityexplorer.dao;

import java.sql.Statement;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import fr.hei.devweb.cityexplorer.daos.CityDao;
import fr.hei.devweb.cityexplorer.pojos.City;

public class CityDaoTestCase extends AbstractDaoTestCase {
	
	private CityDao cityDao = new CityDao();

	@Override
	public void insertDataSet(Statement statement) throws Exception {
		statement.executeUpdate("INSERT INTO city(id, name, summary) VALUES(1, 'City 1', 'Summary 1')");
		statement.executeUpdate("INSERT INTO city(id, name, summary) VALUES(2, 'City 2', 'Summary 2')");
		statement.executeUpdate("INSERT INTO city(id, name, summary) VALUES(3, 'City 3', 'Summary 3')");
	}

	@Test
	public void shouldListCities() throws Exception {
		// WHEN
		List<City> cities = cityDao.listCities();
		// THEN
		Assertions.assertThat(cities).hasSize(3);
		Assertions.assertThat(cities).extracting("id", "name", "summary").containsOnly(
				Assertions.tuple(1, "City 1", "Summary 1"),
				Assertions.tuple(2, "City 2", "Summary 2"),
				Assertions.tuple(3, "City 3", "Summary 3")
		);
	}

	@Test
	public void shouldGetCities() throws Exception {
		// WHEN
		City city = cityDao.getCity(1);
		// THEN
		Assertions.assertThat(city).isNotNull();
		Assertions.assertThat(city.getId()).isEqualTo(1);
		Assertions.assertThat(city.getName()).isEqualTo("City 1");
		Assertions.assertThat(city.getSummary()).isEqualTo("Summary 1");
		
	}
}
