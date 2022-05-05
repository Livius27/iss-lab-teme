package repository.database;

import model.Loc;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.ILocRepository;

import java.util.ArrayList;
import java.util.List;

public class LocORMRepository implements ILocRepository {
    private final SessionFactory sessionFactory;

    public LocORMRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Loc findOne(Long id) {
        Loc loc = null;

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                loc = session.get(Loc.class, id);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error finding loc by id! " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return loc;
    }

    @Override
    public Iterable<Loc> findAll() {
        List<Loc> locuri = new ArrayList<Loc>();

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                locuri = session.createQuery("FROM Loc", Loc.class).list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error finding all locuri! " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return locuri;
    }

    @Override
    public Loc save(Loc loc) {
        return null;
    }

    @Override
    public Loc delete(Long id) {
        return null;
    }

    @Override
    public Loc update(Loc loc) {
        return null;
    }
}
