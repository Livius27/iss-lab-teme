package repository.database;

import model.Rezervare;
import model.Spectacol;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.ISpectacolRepository;

import java.util.ArrayList;
import java.util.List;

public class SpectacolORMRepository implements ISpectacolRepository {
    private final SessionFactory sessionFactory;

    public SpectacolORMRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Spectacol findOne(Long id) {
        Spectacol spectacol = null;

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                spectacol = session.get(Spectacol.class, id);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error finding spectacol by id! " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return spectacol;
    }

    @Override
    public Iterable<Spectacol> findAll() {
        List<Spectacol> spectacole = new ArrayList<Spectacol>();

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                spectacole = session.createQuery("FROM Spectacol", Spectacol.class).list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error finding all spectacole! " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return spectacole;
    }

    @Override
    public Spectacol save(Spectacol spectacol) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.persist(spectacol);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error saving spectacol! " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return spectacol;
    }

    @Override
    public Spectacol delete(Long id) {
        Spectacol deleted = findOne(id);
        String titluDeleted = deleted.getTitlu();
        List<Rezervare> rezervari;

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.remove(deleted);
                rezervari = session.createQuery("FROM Rezervare WHERE titluSpectacol = :titluSpectacol", Rezervare.class)
                        .setParameter("titluSpectacol", titluDeleted)
                        .list();
                rezervari.forEach(session::remove);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error removing spectacol and associated rezervari! " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return deleted;
    }

    @Override
    public Spectacol update(Spectacol spectacol) {
        Spectacol toUpdate = null;

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                toUpdate = session.load(Spectacol.class, spectacol.getId());
                toUpdate.setTitlu(spectacol.getTitlu());
                toUpdate.setData(spectacol.getData());
                session.update(toUpdate);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error updating spectacol! " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return toUpdate;
    }

    @Override
    public Iterable<Spectacol> sortSpectacoleByNrLocuriDisponibile() {
        List<Spectacol> spectacole = new ArrayList<Spectacol>();

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                spectacole = session.createQuery("FROM Spectacol ORDER BY nrLocuriDisponibile DESC", Spectacol.class)
                        .list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error getting all spectacole sorted by nr_locuri_disponibile! " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return spectacole;
    }
}
