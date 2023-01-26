package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
//import org.hibernate.procedure.internal.Util;

import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl extends Util implements UserDao {

    public UserDaoHibernateImpl() {

         }
    Transaction transaction = null;
    @Override
    public void createUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                    "(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "lastName VARCHAR(100) NOT NULL, " +
                    "age INT NOT NULL);").executeUpdate();
            transaction.commit();

            transaction = session.beginTransaction();
            session.createSQLQuery("ALTER TABLE users AUTO_INCREMENT = 1;").executeUpdate();
            transaction.commit();

            System.out.println("Table has been created");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();

            transaction.commit();
            System.out.println("Table has been deleted");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(new User(name, lastName, age));

            transaction.commit();
            System.out.println("Created User: " + name + " " + lastName + " " + age);
        } catch (HibernateException e) {
            e.printStackTrace();
           if (transaction != null) {
              transaction.rollback();
           }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            //User user = session.load(User.class, id);
            session.delete(user);

            transaction.commit();
            //session.getTransaction().commit();
            System.out.println("User " + id + " has been deleted");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> list = new ArrayList<>();

        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            list = session.createQuery("from User").getResultList();
            //list = session.createQuery("from User", User.class).list();

            transaction.commit();
            System.out.println('\n' + "Users list:");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {

        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery("DELETE FROM users").executeUpdate();

            System.out.println('\n' + "Table is empty");
            //session.getTransaction().commit();
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
