package repository.database;

import model.Spectator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.ISpectatorRepository;

import java.util.ArrayList;
import java.util.List;

public class SpectatorORMRepository implements ISpectatorRepository {
    private final SessionFactory sessionFactory;

    public SpectatorORMRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Spectator findOne(Long id) {
        Spectator spectator = null;

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                spectator = session.get(Spectator.class, id);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error finding spectator by id! " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return spectator;
    }

    @Override
    public Iterable<Spectator> findAll() {
        List<Spectator> spectatori = new ArrayList<Spectator>();

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                spectatori = session.createQuery("FROM Spectator", Spectator.class).list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error finding all spectatori! " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return spectatori;
    }

    @Override
    public Spectator save(Spectator spectator) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.persist(spectator);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error saving spectator! " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return spectator;
    }

    @Override
    public Spectator delete(Long id) {
        return null;
    }

    @Override
    public Spectator update(Spectator spectator) {
        return null;
    }
}
