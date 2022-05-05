package repository.database;

import model.Rezervare;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.IRezervareRepository;

import java.util.ArrayList;
import java.util.List;

public class RezervareORMRepository implements IRezervareRepository {
    private final SessionFactory sessionFactory;

    public RezervareORMRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Rezervare findOne(Long id) {
        Rezervare rezervare = null;

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                rezervare = session.get(Rezervare.class, id);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error finding rezervare by id! " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return rezervare;
    }

    @Override
    public Iterable<Rezervare> findAll() {
        List<Rezervare> rezervari = new ArrayList<Rezervare>();

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                rezervari = session.createQuery("FROM Rezervare ", Rezervare.class).list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Error finding all rezervari! " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return rezervari;
    }

    @Override
    public Rezervare save(Rezervare rezervare) {
        return null;
    }

    @Override
    public Rezervare delete(Long id) {
        return null;
    }

    @Override
    public Rezervare update(Rezervare rezervare) {
        return null;
    }
}
