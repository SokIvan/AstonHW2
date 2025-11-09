package dao;

import entity.User;
import utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserDao implements UserDaoInterface{

    public Optional<User> findById(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        try {
            User user = session.get(User.class, id);
            return Optional.ofNullable(user);
        } finally {
            session.close(); // ВАЖНО: закрывать сессию
        }
    }

    public List<User> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        try {
            Query<User> query = session.createQuery("from User", User.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    public User save(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Error saving user", e);
        } finally {
            session.close();
        }
    }

    public User update(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Error updating user", e);
        } finally {
            session.close();
        }
    }

    public void delete(Long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Error deleting user", e);
        } finally {
            session.close();
        }
    }

}