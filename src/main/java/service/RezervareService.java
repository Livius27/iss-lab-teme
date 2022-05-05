package service;

import model.Rezervare;
import model.validators.RezervareValidator;
import model.validators.ValidationException;
import repository.IRezervareRepository;

public class RezervareService {
    private final IRezervareRepository rezervareRepo;
    private final RezervareValidator rezervareValidator;

    public RezervareService(IRezervareRepository rezervareRepo, RezervareValidator rezervareValidator) {
        this.rezervareRepo = rezervareRepo;
        this.rezervareValidator = rezervareValidator;
    }

    public Rezervare getRezervare(long id) throws ServiceException {
        Rezervare found = rezervareRepo.findOne(id);
        if (found == null)
            throw new ServiceException("Rezervare not found!\n");
        return found;
    }

    public Iterable<Rezervare> getAllRezervari() {
        return rezervareRepo.findAll();
    }

    public void saveNewRezervare(long idSpectator, long idLocRezervat, String titluSpectacol) throws ValidationException, ServiceException {
        Rezervare rezervare = new Rezervare(idSpectator, idLocRezervat, titluSpectacol);
        rezervareValidator.validate(rezervare);
        Rezervare saved = rezervareRepo.save(rezervare);
        if (saved == null)
            throw new ServiceException("Rezervare was not saved!\n");
    }
}
