package service;

import model.Spectacol;
import model.validators.SpectacolValidator;
import model.validators.ValidationException;
import repository.ISpectacolRepository;

public class SpectacolService {
    private final ISpectacolRepository spectacolRepo;
    private final SpectacolValidator spectacolValidator;

    public SpectacolService(ISpectacolRepository spectacolRepo, SpectacolValidator spectacolValidator) {
        this.spectacolRepo = spectacolRepo;
        this.spectacolValidator = spectacolValidator;
    }

    public Spectacol getSpectacol(long id) throws ServiceException {
        Spectacol found = spectacolRepo.findOne(id);
        if (found == null)
            throw new ServiceException("Spectacol not found!\n");
        return found;
    }

    public Iterable<Spectacol> getAllSpectacole() {
        return spectacolRepo.findAll();
    }

    public void addNewSpectacol(String titlu, String data, int nrLocuriDisponibile, float pretLoc) throws ValidationException, ServiceException {
        Spectacol spectacol = new Spectacol(titlu, data, nrLocuriDisponibile);
        spectacolValidator.validate(spectacol);
        if (pretLoc <= 0)
            throw new ServiceException("Pretul locului trebuie sa fie un numar real, pozitiv, diferit de 0!\n");
        Spectacol saved = spectacolRepo.save(spectacol);
        if (saved == null)
            throw new ServiceException("Spectacol was not saved!\n");
    }

    public Iterable<Spectacol> getSortedSpectacole() {
        return spectacolRepo.sortSpectacoleByNrLocuriDisponibile();
    }
}
