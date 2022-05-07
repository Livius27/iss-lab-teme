package repository;

import model.Spectacol;

public interface ISpectacolRepository extends ICRUDRepository<Integer, Spectacol> {
    Iterable<Spectacol> sortSpectacoleByNrLocuriDisponibile();

    Spectacol getSpectacolByTitlu(String titluSpectacol);
}
