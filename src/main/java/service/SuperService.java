package service;

import model.*;
import model.validators.ValidationException;

import java.util.ArrayList;
import java.util.Collection;

public class SuperService {
    private final AccountService accountService;
    private final LocService locService;
    private final RezervareService rezervareService;
    private final SpectacolService spectacolService;
    private final SpectatorService spectatorService;

    public SuperService(AccountService accountService, LocService locService, RezervareService rezervareService,
                        SpectacolService spectacolService, SpectatorService spectatorService) {
        this.accountService = accountService;
        this.locService = locService;
        this.rezervareService = rezervareService;
        this.spectacolService = spectacolService;
        this.spectatorService = spectatorService;
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

    public Loc getLocInfo(long id) throws ServiceException {
        return locService.getLoc(id);
    }

    public Iterable<Loc> getAllLocuri() {
        return locService.getAllLocuri();
    }

    public Rezervare getRezervareInfo(long id) throws ServiceException {
        return rezervareService.getRezervare(id);
    }

    public Iterable<Rezervare> getAllRezervari() {
        return rezervareService.getAllRezervari();
    }

    public void addNewRezervare(long idSpectator, long idLocRezervat, String titluSpectacol) throws ValidationException, ServiceException {
        rezervareService.saveNewRezervare(idSpectator, idLocRezervat, titluSpectacol);
    }

    public Spectacol getSpectacolInfo(long id) throws ServiceException {
        return spectacolService.getSpectacol(id);
    }

    public Iterable<Spectacol> getAllSpectacole() {
        return spectacolService.getAllSpectacole();
    }

    public void addNewSpectacol(String titlu, String data, int nrLocuriDisponibile) throws ValidationException, ServiceException {
        spectacolService.saveNewSpectacol(titlu, data, nrLocuriDisponibile);
    }

    public void removeExistingSpectacol(long id) throws ServiceException {
        spectacolService.deleteExistingSpectacol(id);
    }

    public void modifyExistingSpectacol(Spectacol spectacol) throws ValidationException, ServiceException {
        spectacolService.updateExistingSpectacol(spectacol);
    }

    public Collection<Spectacol> getAllSpectacoleSorted() {
        Collection<Spectacol> spectacole = new ArrayList<Spectacol>();
        spectacolService.getSortedSpectacole().forEach(spectacole::add);
        return spectacole;
    }

    public Spectator getSpectatorInfo(long id) throws ServiceException {
        return spectatorService.getSpectator(id);
    }

    public Iterable<Spectator> getAllSpectatori() {
        return spectatorService.getAllSpectatori();
    }

    public void addNewSpectator(String nume, String prenume, String email) throws ValidationException, ServiceException {
        spectatorService.saveNewSpectator(nume, prenume, email);
    }
}
