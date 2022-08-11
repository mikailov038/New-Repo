package jm.task.core.jdbc.dao;

import com.mysql.cj.jdbc.StatementImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.query.criteria.internal.expression.ConcatExpression;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {

    private Util util;
    Connection connection = util.getConnection();

    private String sql;
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY ," +
                " name VARCHAR(20), " +
                " lastname VARCHAR(20), " +
                " age INTEGER);";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table is created");
            connection.commit();
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void dropUsersTable() {
        sql = "DROP TABLE IF EXISTS users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table is dropped");
            connection.commit();
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        sql = "insert into users (name, lastname, age) values (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        sql = "DELETE FROM users  WHERE id = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            connection.commit();
            connection.rollback();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {

        List<User> a = new ArrayList<>();
        sql = "Select * from users";
        try (Statement statement = connection.createStatement()) {
            ResultSet r = statement.executeQuery(sql);
            while (r.next()) {
                a.add(new User(r.getNString(2),
                        r.getNString(3),
                        r.getByte(4)));
                connection.commit();
                connection.rollback();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return a;
    }

    public void cleanUsersTable() {
        sql = " TRUNCATE TABLE users ";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            connection.commit();
            connection.rollback();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
