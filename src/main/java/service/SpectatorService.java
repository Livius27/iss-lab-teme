package service;

import model.Spectator;
import model.validators.SpectatorValidator;
import model.validators.ValidationException;
import repository.ISpectatorRepository;

public class SpectatorService {
    private final ISpectatorRepository spectatorRepo;
    private final SpectatorValidator spectatorValidator;

    public SpectatorService(ISpectatorRepository spectacolRepo, SpectatorValidator spectacolValidator) {
        this.spectatorRepo = spectacolRepo;
        this.spectatorValidator = spectacolValidator;
    }

    public Spectator getSpectator(long id) throws ServiceException {
        Spectator found = spectatorRepo.findOne(id);
        if (found == null)
            throw new ServiceException("Spectator not found!\n");
        return found;
    }

    public Iterable<Spectator> getAllSpectatori() {
        return spectatorRepo.findAll();
    }

    public void saveNewSpectator(String nume, String prenume, String email) throws ValidationException, ServiceException {
        Spectator spectator = new Spectator(nume, prenume, email);
        spectatorValidator.validate(spectator);
        Spectator saved = spectatorRepo.save(spectator);
        if (saved == null)
            throw new ServiceException("Spectator was not saved!\n");
    }
}
