package fr.hei.devweb.cityexplorer.services;

import java.util.List;

import fr.hei.devweb.cityexplorer.daos.CityDao;
import fr.hei.devweb.cityexplorer.pojos.City;

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
	
	public List<City> listAllCities() {
		return cityDao.listCities();
	}
	
	public City getCity(Integer id) {
		if(id == null) {
			throw new IllegalArgumentException("City id must be provided.");
		}
		return cityDao.getCity(id);
	}

}
