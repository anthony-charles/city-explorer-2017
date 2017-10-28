package fr.hei.devweb.cityexplorer.dao;

import java.sql.Connection;
import java.sql.Statement;

import org.junit.Before;

import fr.hei.devweb.cityexplorer.daos.DataSourceProvider;

public abstract class AbstractDaoTestCase {

	@Before
	public void initDatabase() throws Exception {
		try(
	        Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
	        Statement statement = connection.createStatement()){
			statement.executeUpdate("DELETE FROM comment");
			statement.executeUpdate("DELETE FROM city");

	        this.insertDataSet(statement);
		}
	}
	
	public abstract void insertDataSet(Statement statement) throws Exception;
}
