package repository.database;

import model.Manager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.IAccountRepository;

import java.util.ArrayList;
import java.util.List;

public class AccountORMRepository implements IAccountRepository {
    private final SessionFactory sessionFactory;

    public AccountORMRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Manager findOne(Long id) {
        Manager account = null;

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                account = session.get(Manager.class, id);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error finding manager by id! " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return account;
    }

    @Override
    public Iterable<Manager> findAll() {
        List<Manager> accounts = new ArrayList<Manager>();

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                accounts = session.createQuery("FROM Manager", Manager.class).list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error finding all managers! " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return accounts;
    }

    @Override
    public Manager save(Manager account) {
        return null;
    }

    @Override
    public Manager delete(Long id) {
        return null;
    }

    @Override
    public Manager update(Manager account) {
        return null;
    }

    @Override
    public Manager login(String username, int passwordHash) {
        Manager account = null;

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                account = session
                        .createQuery("FROM Manager WHERE username = :username AND passwordHash = :passwordHash", Manager.class)
                        .setParameter("username", username)
                        .setParameter("passwordHash", passwordHash)
                        .uniqueResult();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error finding manager by credentials for login! " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return account;
    }
}
