package fr.hei.devweb.cityexplorer.services;

import java.util.List;

import fr.hei.devweb.cityexplorer.daos.CityDao;
import fr.hei.devweb.cityexplorer.pojos.City;
import fr.hei.devweb.cityexplorer.pojos.Country;

public class CityService {
	
	private CityDao cityDao = new CityDao();
	
	private static class CityServiceHolder {
		private static CityService instance = new CityService();
	}
	
	public static CityService getInstance() {
		return CityServiceHolder.instance;
	}

	private CityService() {
	}
	
	public List<City> listAllCities(Country filter) {
		if (filter == null) {
			return cityDao.listCities();
		} else {
			return cityDao.listCitiesByCountry(filter);
		}
	}
	
	public City getCity(Integer id) {
		if(id == null) {
			throw new IllegalArgumentException("City id must be provided.");
		}
		return cityDao.getCity(id);
	}
	
	public void addCity(City newCity) {
		if(newCity == null){
			throw new IllegalArgumentException("A city must be provided.");
		}
		if(newCity.getName() == null || "".equals(newCity.getName())) {
			throw new IllegalArgumentException("A city must have a name.");
		}
		if(newCity.getSummary() == null || "".equals(newCity.getSummary())) {
			throw new IllegalArgumentException("A city must have a summary.");
		}
		cityDao.addCity(newCity);
	}

}
