package fr.hei.devweb.cityexplorer.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import fr.hei.devweb.cityexplorer.daos.CityDao;
import fr.hei.devweb.cityexplorer.pojos.City;

import javax.servlet.http.Part;

public class CityService {

	private static final String IMAGE_DIRECTORY_PATH = "C:/HEI/data/city-explorer";

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
	
	public void addCity(City newCity, Part picture) {
		if(newCity == null){
			throw new IllegalArgumentException("A city must be provided.");
		}
		if(newCity.getName() == null || "".equals(newCity.getName())) {
			throw new IllegalArgumentException("A city must have a name.");
		}
		if(newCity.getSummary() == null || "".equals(newCity.getSummary())) {
			throw new IllegalArgumentException("A city must have a summary.");
		}

		Path picturePath = null;
		if(picture != null) {
			String filename = UUID.randomUUID().toString().substring(0,8) + "-" + picture.getSubmittedFileName();
			picturePath = Paths.get(IMAGE_DIRECTORY_PATH, filename);
			try {
				Files.copy(picture.getInputStream(), picturePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		cityDao.addCity(newCity, picturePath);
	}

	public Path getCityPicturePath(Integer cityId) {
		Path picturePath = cityDao.getPicturePath(cityId);
		if (picturePath == null) {
			try {
				picturePath = Paths.get(this.getClass().getClassLoader().getResource("city-no-photo.png").toURI());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

		}
		return picturePath;
	}

}
