package fr.hei.devweb.cityexplorer.dao;

import fr.hei.devweb.cityexplorer.daos.CommentDao;
import fr.hei.devweb.cityexplorer.daos.DataSourceProvider;
import fr.hei.devweb.cityexplorer.pojos.Comment;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class CommentDaoTestCase extends AbstractDaoTestCase {

    private CommentDao commentDao = new CommentDao();

    @Override
    public void insertDataSet(Statement statement) throws Exception {
        statement.executeUpdate("INSERT INTO city(id, name, summary) VALUES(1, 'City 1', 'Summary 1')");
        statement.executeUpdate("INSERT INTO city(id, name, summary) VALUES(2, 'City 2', 'Summary 2')");
        statement.executeUpdate("INSERT INTO comment(id, pseudo, creationdate, message, city) VALUES (1, 'pseudo 1', '2017-10-28', 'message 1', 1)");
        statement.executeUpdate("INSERT INTO comment(id, pseudo, creationdate, message, city) VALUES (2, 'pseudo 2', '2017-10-29', 'message 2', 2)");
        statement.executeUpdate("INSERT INTO comment(id, pseudo, creationdate, message, city) VALUES (3, 'pseudo 3', '2017-10-30', 'message 3', 1)");
    }

    @Test
    public void shouldAddComment() throws SQLException {
        // GIVEN
        Comment newComment = new Comment(null, "new pseudo", LocalDate.of(2017, 10, 10), "new message");
        // WHEN
        commentDao.addComment(newComment, 1);
        // THEN
        try(Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM comment WHERE pseudo='new pseudo'")){
            Assertions.assertThat(resultSet.next()).isTrue();
            Assertions.assertThat(resultSet.getInt("id")).isNotNull();
            Assertions.assertThat(resultSet.getString("pseudo")).isEqualTo("new pseudo");
            Assertions.assertThat(resultSet.getDate("creationdate").toLocalDate()).isEqualTo(LocalDate.of(2017, 10, 10));
            Assertions.assertThat(resultSet.getString("message")).isEqualTo("new message");
            Assertions.assertThat(resultSet.getInt("city")).isEqualTo(1);
            Assertions.assertThat(resultSet.next()).isFalse();
        }
    }
}
