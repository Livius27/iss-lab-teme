package repository;

import model.Spectacol;

public interface ISpectacolRepository extends ICRUDRepository<Long, Spectacol> {
    Iterable<Spectacol> sortSpectacoleByNrLocuriDisponibile();
}
