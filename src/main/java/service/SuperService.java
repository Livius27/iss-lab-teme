package service;

import model.*;
import model.validators.ValidationException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    public Manager getAccountInfo(int id) throws ServiceException {
        return accountService.getAccount(id);
    }

    public Iterable<Manager> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    public Manager login(String username, String password) throws ServiceException {
        return accountService.login(username, password);
    }

    public Loc getLocInfo(int id) throws ServiceException {
        return locService.getLoc(id);
    }

    public List<Loc> getAllLocuri() {
        List<Loc> locuri = new ArrayList<Loc>();
        locService.getAllLocuri().forEach(locuri::add);
        return locuri;
    }

    public Rezervare getRezervareInfo(int id) throws ServiceException {
        return rezervareService.getRezervare(id);
    }

    public Iterable<Rezervare> getAllRezervari() {
        return rezervareService.getAllRezervari();
    }

    public Iterable<Rezervare> getAllRezervariFromSpectacol(String titluSpectacol) {
        return rezervareService.getAllRezervariFromSpectacol(titluSpectacol);
    }

    public void addNewRezervare(int idSpectator, int idLocRezervat, String titluSpectacol) throws ValidationException, ServiceException {
        rezervareService.saveNewRezervare(idSpectator, idLocRezervat, titluSpectacol);
    }

    public Spectacol getSpectacolInfo(int id) throws ServiceException {
        Spectacol spectacol = spectacolService.getSpectacol(id);

        List<Loc> allLocuri = new ArrayList<Loc>();
        locService.getAllLocuri().forEach(allLocuri::add);
        AtomicInteger locuriNeutilizabile = new AtomicInteger(allLocuri.size() - spectacol.getNrLocuriDisponibile());

        spectacol.setLocuri(allLocuri);
        rezervareService.getAllRezervariFromSpectacol(spectacol.getTitlu()).forEach(rezervare ->
                spectacol.getLocuri().forEach(loc -> {
                    if (rezervare.getIdLocRezervat() == loc.getId()) {
                        loc.setStareLoc(StareLoc.REZERVAT);
                        locuriNeutilizabile.getAndDecrement();
                    }
                }));
        for (int i = allLocuri.size() - 1; i >= 0; i--) {
            if (locuriNeutilizabile.get() > 0) {
                spectacol.getLocuri().get(i).setStareLoc(StareLoc.INDISPONIBIL);
                locuriNeutilizabile.getAndDecrement();
            } else
                break;
        }
        return spectacol;
    }

    public Iterable<Spectacol> getAllSpectacole() {
        Iterable<Spectacol> allSpectacole = spectacolService.getAllSpectacole();

        for (Spectacol spectacol : allSpectacole) {
            List<Loc> allLocuri = new ArrayList<Loc>();
            locService.getAllLocuri().forEach(allLocuri::add);
            AtomicInteger locuriNeutilizabile = new AtomicInteger(allLocuri.size() - spectacol.getNrLocuriDisponibile());

            spectacol.setLocuri(allLocuri);
            rezervareService.getAllRezervariFromSpectacol(spectacol.getTitlu()).forEach(rezervare ->
                    spectacol.getLocuri().forEach(loc -> {
                        if (rezervare.getIdLocRezervat() == loc.getId()) {
                            loc.setStareLoc(StareLoc.REZERVAT);
                            locuriNeutilizabile.getAndDecrement();
                        }
                    }));
            for (int i = allLocuri.size() - 1; i >= 0; i--) {
                if (locuriNeutilizabile.get() > 0) {
                    spectacol.getLocuri().get(i).setStareLoc(StareLoc.INDISPONIBIL);
                    locuriNeutilizabile.getAndDecrement();
                } else
                    break;
            }

        }
        return allSpectacole;
    }

    public void addNewSpectacol(String titlu, String data, int nrLocuriDisponibile) throws ValidationException, ServiceException {
        spectacolService.saveNewSpectacol(titlu, data, nrLocuriDisponibile);
    }

    public void removeExistingSpectacol(int id) throws ServiceException {
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

    public Spectacol getSpectacolByTitlu(String titlu) {
        Spectacol spectacol = spectacolService.getSpectacolByTitlu(titlu);

        List<Loc> allLocuri = new ArrayList<Loc>();
        locService.getAllLocuri().forEach(allLocuri::add);
        AtomicInteger locuriNeutilizabile = new AtomicInteger(allLocuri.size() - spectacol.getNrLocuriDisponibile());

        spectacol.setLocuri(allLocuri);
        rezervareService.getAllRezervariFromSpectacol(spectacol.getTitlu()).forEach(rezervare ->
                spectacol.getLocuri().forEach(loc -> {
                    if (rezervare.getIdLocRezervat() == loc.getId()) {
                        loc.setStareLoc(StareLoc.REZERVAT);
                        locuriNeutilizabile.getAndDecrement();
                    }
                }));
        for (int i = allLocuri.size() - 1; i >= 0; i--) {
            if (locuriNeutilizabile.get() > 0) {
                spectacol.getLocuri().get(i).setStareLoc(StareLoc.INDISPONIBIL);
                locuriNeutilizabile.getAndDecrement();
            } else
                break;
        }
        return spectacol;
    }

    public Spectator getSpectatorInfo(int id) throws ServiceException {
        return spectatorService.getSpectator(id);
    }

    public Iterable<Spectator> getAllSpectatori() {
        return spectatorService.getAllSpectatori();
    }

    public Spectator addNewSpectator(String nume, String prenume, String email) throws ValidationException, ServiceException {
        return spectatorService.saveNewSpectator(nume, prenume, email);
    }
}
