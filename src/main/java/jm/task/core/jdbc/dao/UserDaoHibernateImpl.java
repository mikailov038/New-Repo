package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        String sql = "CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY ," +
                " name VARCHAR(20), " +
                " lastname VARCHAR(20), " +
                " age INTEGER);";

        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(sql, User.class).executeUpdate();
            transaction.commit();
        }


    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(sql, User.class).executeUpdate();
            transaction.commit();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into users ( name, lastname, age) values (:name, :lastname, :age)";
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(sql, User.class)
                .setParameter("name", name)
                .setParameter("lastname", lastName)
                .setParameter("age", age).executeUpdate();
            transaction.commit();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "Select * from users";
        try (Session session = Util.getSessionFactory().openSession()) {
            users = session.createNativeQuery(sql, User.class).list();
        }
        return users;
    }


    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM users  WHERE id = :id";
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(sql, User.class).setParameter("id", id).executeUpdate();
            transaction.commit();
        }

    }


    @Override
    public void cleanUsersTable() {
      String sql = " TRUNCATE TABLE users ";
        try (Session session = Util.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createNativeQuery(sql, User.class).executeUpdate();
            transaction.commit();
        }
    }
}
