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
    boolean needRollback = true;

    private Util util;
    private String sql;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {

        Connection connection = util.getConnection();

        sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY ," +
                " name VARCHAR(20), " +
                " lastname VARCHAR(20), " +
                " age INTEGER);";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table is created");
            connection.commit();
            needRollback = false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (needRollback) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void dropUsersTable() {
        Connection connection = util.getConnection();

        sql = "DROP TABLE IF EXISTS users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Table is dropped");
            connection.commit();
            needRollback = false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (needRollback) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = util.getConnection();

        sql = "insert into users (name, lastname, age) values (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            needRollback = false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (needRollback) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void removeUserById(long id) {
        Connection connection = util.getConnection();

        sql = "DELETE FROM users  WHERE id = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            connection.commit();
            needRollback = false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (needRollback) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public List<User> getAllUsers() {
        Connection connection = util.getConnection();

        List<User> a = new ArrayList<>();
        sql = "Select * from users";
        try (Statement statement = connection.createStatement()) {
            ResultSet r = statement.executeQuery(sql);
            while (r.next()) {
                a.add(new User(r.getNString(2),
                        r.getNString(3),
                        r.getByte(4)));
                connection.commit();
                needRollback = false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (needRollback) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return a;
    }

    public void cleanUsersTable() {
        Connection connection = util.getConnection();

        sql = " TRUNCATE TABLE users ";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            connection.commit();
            needRollback = false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (needRollback) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
