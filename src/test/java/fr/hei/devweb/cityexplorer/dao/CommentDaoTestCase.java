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
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class CommentDaoTestCase extends AbstractDaoTestCase {

    private CommentDao commentDao = new CommentDao();

    @Override
    public void insertDataSet(Statement statement) throws Exception {
        statement.executeUpdate("INSERT INTO city(id, name, summary) VALUES(1, 'City 1', 'Summary 1')");
        statement.executeUpdate("INSERT INTO city(id, name, summary) VALUES(2, 'City 2', 'Summary 2')");
        statement.executeUpdate("INSERT INTO comment(id, pseudo, creationdate, message, city) VALUES (1, 'pseudo 1', '2017-10-28 11:11:11', 'message 1', 1)");
        statement.executeUpdate("INSERT INTO comment(id, pseudo, creationdate, message, city) VALUES (2, 'pseudo 2', '2017-10-29 12:13:14', 'message 2', 2)");
        statement.executeUpdate("INSERT INTO comment(id, pseudo, creationdate, message, city) VALUES (3, 'pseudo 3', '2017-10-30 15:16:17', 'message 3', 1)");
    }

    @Test
    public void shouldListCommentsByCity() {
        // WHEN
        List<Comment> comments = commentDao.listCommentsByCity(1);
        // THEN
        assertThat(comments).hasSize(2);
        assertThat(comments).extracting("id", "pseudo", "creationDate", "message").containsExactly(
                tuple(3, "pseudo 3", LocalDateTime.of(2017, 10, 30, 15, 16 , 17), "message 3"),
                tuple(1, "pseudo 1", LocalDateTime.of(2017, 10, 28, 11, 11, 11), "message 1")
        );
    }

    @Test
    public void shouldAddComment() throws SQLException {
        // GIVEN
        Comment newComment = new Comment(null, "new pseudo", LocalDateTime.of(2017, 10, 10, 15, 11, 3), "new message");
        // WHEN
        commentDao.addComment(newComment, 1);
        // THEN
        try(Connection connection = DataSourceProvider.getInstance().getDataSource().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM comment WHERE pseudo='new pseudo'")){
            assertThat(resultSet.next()).isTrue();
            assertThat(resultSet.getInt("id")).isNotNull();
            assertThat(resultSet.getString("pseudo")).isEqualTo("new pseudo");
            assertThat(resultSet.getDate("creationdate").toLocalDate()).isEqualTo(LocalDate.of(2017, 10, 10));
            assertThat(resultSet.getString("message")).isEqualTo("new message");
            assertThat(resultSet.getInt("city")).isEqualTo(1);
            assertThat(resultSet.next()).isFalse();
        }
    }
}
