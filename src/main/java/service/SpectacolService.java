package service;

import model.Spectacol;
import model.validators.SpectacolValidator;
import model.validators.ValidationException;
import repository.ISpectacolRepository;

import java.util.Objects;

public class SpectacolService {
    private final ISpectacolRepository spectacolRepo;
    private final SpectacolValidator spectacolValidator;

    public SpectacolService(ISpectacolRepository spectacolRepo, SpectacolValidator spectacolValidator) {
        this.spectacolRepo = spectacolRepo;
        this.spectacolValidator = spectacolValidator;
    }

    public Spectacol getSpectacol(int id) throws ServiceException {
        Spectacol found = spectacolRepo.findOne(id);
        if (found == null)
            throw new ServiceException("Spectacol not found!\n");
        return found;
    }

    public Iterable<Spectacol> getAllSpectacole() {
        return spectacolRepo.findAll();
    }

    public void saveNewSpectacol(String titlu, String data, int nrLocuriDisponibile) throws ValidationException, ServiceException {
        Spectacol spectacol = new Spectacol(titlu, data, nrLocuriDisponibile);
        spectacolValidator.validate(spectacol);
        Spectacol saved = spectacolRepo.save(spectacol);
        if (saved == null)
            throw new ServiceException("Spectacol was not saved!\n");
    }

    public void deleteExistingSpectacol(int id) throws ServiceException {
        Spectacol found = spectacolRepo.findOne(id);
        if (found == null)
            throw new ServiceException("There is no spectacol with the given id to remove!\n");
        spectacolRepo.delete(id);
    }

    public void updateExistingSpectacol(Spectacol spectacol) throws ValidationException, ServiceException {
        spectacolValidator.validate(spectacol);
        Spectacol updated = spectacolRepo.update(spectacol);
        if (updated == null || !Objects.equals(updated.getTitlu(), spectacol.getTitlu())
                && !Objects.equals(updated.getData(), spectacol.getData()))
            throw new ServiceException("There is no spectacol with the given id to update!\n");
    }

    public Iterable<Spectacol> getSortedSpectacole() {
        return spectacolRepo.sortSpectacoleByNrLocuriDisponibile();
    }
}
