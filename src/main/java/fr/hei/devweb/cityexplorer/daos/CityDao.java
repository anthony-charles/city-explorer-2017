package fr.hei.devweb.cityexplorer.daos;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import fr.hei.devweb.cityexplorer.exceptions.CityExplorerRuntimeException;
import fr.hei.devweb.cityexplorer.pojos.City;
import fr.hei.devweb.cityexplorer.pojos.Country;

public class CityDao {

    public List<City> listCities() {
        List<City> cities = new ArrayList<City>();

        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM city WHERE deleted=false ORDER BY name")) {
            while (resultSet.next()) {
                cities.add(
                        new City(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("summary"),
                                Country.valueOf(resultSet.getString("country")),
				resultSet.getInt("likes"),
				resultSet.getInt("dislikes")
                        ));
            }
        } catch (SQLException e) {
            throw new CityExplorerRuntimeException("Error when getting cities", e);
        }

        return cities;
    }

    public List<City> listCitiesByCountry(Country country) {
        List<City> cities = new ArrayList<>();
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM city WHERE country = ? AND deleted=false ORDER BY name")) {
            statement.setString(1, country.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    cities.add(
                            new City(
                                    resultSet.getInt("id"),
                                    resultSet.getString("name"),
                                    resultSet.getString("summary"),
                                    Country.valueOf(resultSet.getString("country")),
                                    resultSet.getInt("likes"),
                                    resultSet.getInt("dislikes")
                            ));
                }
            }
        } catch (SQLException e) {
            throw new CityExplorerRuntimeException("Error when getting cities", e);
        }

        return cities;
    }

    public City getCity(Integer id) {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM city WHERE id = ? AND deleted=false")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    return new City(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("summary"),
                            Country.valueOf(resultSet.getString("country")),
							resultSet.getInt("likes"),
							resultSet.getInt("dislikes"));
                }
            }
        } catch (SQLException e) {
            throw new CityExplorerRuntimeException("Error when getting cities", e);
        }
        return null;
    }
    
    public Path getPicturePath(Integer cityId) {
		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
			 PreparedStatement statement = connection.prepareStatement("SELECT picture FROM city WHERE id = ? AND deleted=false")) {
			statement.setInt(1, cityId);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					String picturePath = resultSet.getString("picture");
					if (picturePath != null) {
						return Paths.get(picturePath);
					}
				}
			}
		} catch (SQLException e) {
			throw new CityExplorerRuntimeException("Error when getting picture path", e);
		}
		return null;
	}

    public void addCity(City newCity, Path picturePath) {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO city(name, summary, country, likes, dislikes, picture) VALUES (?, ?, ?, ?, ?, ?)")) {
            statement.setString(1, newCity.getName());
            statement.setString(2, newCity.getSummary());
            statement.setString(3, newCity.getCountry().name());
			statement.setInt(4, newCity.getLikes());
			statement.setInt(5, newCity.getDislikes());
			if (picturePath != null) {
				statement.setString(6, picturePath.toString());
			} else {
				statement.setNull(6, Types.VARCHAR);
			}
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new CityExplorerRuntimeException("Error when getting cities", e);
		}
	}

	public void addLike(Integer cityId) {
		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
			 PreparedStatement statement = connection.prepareStatement("UPDATE city SET likes = likes + 1 WHERE id = ? AND deleted=false")) {
			statement.setInt(1, cityId);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new CityExplorerRuntimeException("Error when getting cities", e);
		}
	}

	public void addDislike(Integer cityId) {
		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
			 PreparedStatement statement = connection.prepareStatement("UPDATE city SET dislikes = dislikes + 1 WHERE id = ? AND deleted=false")) {
			statement.setInt(1, cityId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CityExplorerRuntimeException("Error when getting cities", e);
        }
    }
    
    public void deleteCity(Integer cityId) {
		String query = "UPDATE city SET deleted=true WHERE id=?";
		try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
			 PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, cityId);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new CityExplorerRuntimeException("Error when getting cities", e);
		}
	}
}
