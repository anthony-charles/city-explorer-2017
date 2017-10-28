package fr.hei.devweb.cityexplorer.daos;

import fr.hei.devweb.cityexplorer.exceptions.CityExplorerRuntimeException;
import fr.hei.devweb.cityexplorer.pojos.Comment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommentDao {

    public void addComment(Comment comment, Integer cityId) {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO comment(pseudo, creationdate, message, city) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, comment.getPseudo());
            statement.setDate(2, Date.valueOf(comment.getCreationDate()));
            statement.setString(3, comment.getMessage());
            statement.setInt(4, cityId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CityExplorerRuntimeException("Error when getting cities", e);
        }

    }
}
