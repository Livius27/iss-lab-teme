package repository;

import model.Rezervare;

public interface IRezervareRepository extends ICRUDRepository<Integer, Rezervare> {
    Iterable<Rezervare> getAllRezervariFromSpectacol(String titlu);
}
