package service;

import model.Loc;
import model.validators.LocValidator;
import repository.ILocRepository;

public class LocService {
    private final ILocRepository locRepo;
    private final LocValidator locValidator;

    public LocService(ILocRepository locRepo, LocValidator locValidator) {
        this.locRepo = locRepo;
        this.locValidator = locValidator;
    }

    public Loc getLoc(long id) throws ServiceException {
        Loc found = locRepo.findOne(id);
        if (found == null)
            throw new ServiceException("Loc not found!\n");
        return found;
    }

    public Iterable<Loc> getAllLocuri() {
        return locRepo.findAll();
    }
}
