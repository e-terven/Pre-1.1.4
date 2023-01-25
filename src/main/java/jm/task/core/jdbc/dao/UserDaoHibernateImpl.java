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
    //private final Util util;
    public UserDaoHibernateImpl() {

         }
    Transaction transaction = null;
    @Override
    public void createUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (\n" +
                    "id INT IDENTITY NOT NULL,\n" +
                    "name TEXT NOT NULL,\n" +
                    "lastName TEXT NOT NULL,\n" +
                    "age INT  null,\n" +
                    "CONSTRAINT users_pk PRIMARY KEY (id)\n" +
                    ");"
            ).executeUpdate();

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

            session.delete(User.class);

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

        User user = new User(name, lastName, age);

        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(user);

            transaction.commit();
            System.out.println("Created User: " + name + " " + lastName + " " + age);
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            //User user = session.load(User.class, id);
            User user = session.get(User.class, id);
            session.delete(user);
            System.out.println("User " + id + " has been deleted");
            transaction.commit();

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

            //list = session.createQuery("from User", User.class).list();
            list = session.createQuery("from User").getResultList();

            session.getTransaction().commit();
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

        List<User> list = new ArrayList<>();

        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            list = session.createQuery("from User", User.class).list();
            session.delete(list);

            System.out.println('\n' + "Table is empty");
            session.getTransaction().commit();

        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
