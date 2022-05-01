package service;

import model.Manager;
import model.Spectacol;
import model.validators.ValidationException;

import java.util.ArrayList;
import java.util.Collection;

public class SuperService {
    private final AccountService accountService;
    private final SpectacolService spectacolService;

    public SuperService(AccountService accountService, SpectacolService spectacolService) {
        this.accountService = accountService;
        this.spectacolService = spectacolService;
    }

    public Manager getAccountInfo(long id) throws ServiceException {
        return accountService.getAccount(id);
    }

    public Iterable<Manager> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    public Manager login(String username, String password) throws ServiceException {
        return accountService.login(username, password);
    }

    public Spectacol getSpectacolInfo(long id) throws ServiceException {
        return spectacolService.getSpectacol(id);
    }

    public Iterable<Spectacol> getAllSpectacole() {
        return spectacolService.getAllSpectacole();
    }

    public void saveNewSpectacol(String titlu, String data, int nrLocuriDisponibile, float pretLoc) throws ValidationException, ServiceException {
        spectacolService.addNewSpectacol(titlu, data, nrLocuriDisponibile, pretLoc);
    }

    public Collection<Spectacol> getAllSpectacoleSorted() {
        Collection<Spectacol> spectacole = new ArrayList<Spectacol>();
        spectacolService.getSortedSpectacole().forEach(spectacole::add);
        return spectacole;
    }
}
