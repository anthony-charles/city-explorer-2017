package fr.hei.devweb.cityexplorer.daos;

import fr.hei.devweb.cityexplorer.exceptions.CityExplorerRuntimeException;
import fr.hei.devweb.cityexplorer.pojos.Comment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CommentDao {

    public List<Comment> listCommentsByCity(Integer cityId) {
        List<Comment> comments = new ArrayList<>();
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM comment WHERE city=? ORDER BY creationdate DESC")) {
            statement.setInt(1, cityId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    comments.add(new Comment(
                            resultSet.getInt("id"),
                            resultSet.getString("pseudo"),
                            resultSet.getTimestamp("creationdate").toLocalDateTime(),
                            resultSet.getString("message")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new CityExplorerRuntimeException("Error when getting cities", e);
        }
        return comments;
    }

    public void addComment(Comment comment, Integer cityId) {
        try (Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO comment(pseudo, creationdate, message, city) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, comment.getPseudo());
            statement.setTimestamp(2, Timestamp.valueOf(comment.getCreationDate()));
            statement.setString(3, comment.getMessage());
            statement.setInt(4, cityId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new CityExplorerRuntimeException("Error when getting cities", e);
        }

    }
}
