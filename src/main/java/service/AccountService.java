package service;

import model.Manager;
import model.validators.AccountValidator;
import model.validators.ValidationException;
import repository.IAccountRepository;

public class AccountService {
    private final IAccountRepository accountRepo;
    private final AccountValidator accountValidator;

    public AccountService(IAccountRepository accountRepo, AccountValidator accountValidator) {
        this.accountRepo = accountRepo;
        this.accountValidator = accountValidator;
    }

    public Manager getAccount(int id) throws ServiceException {
        Manager found = accountRepo.findOne(id);
        if (found == null)
            throw new ServiceException("Account not found!\n");
        return found;
    }

    public Iterable<Manager> getAllAccounts() {
        return accountRepo.findAll();
    }

    public void saveAccount(String username, String password) throws ValidationException, ServiceException {
        int passwordHash = password.hashCode();
        Manager account = new Manager(username, passwordHash);
        accountValidator.validate(account);
        Manager saved = accountRepo.save(account);
        if (saved == null)
            throw new ServiceException("Account was not saved!\n");
    }

    public Manager login(String username, String password) throws ServiceException {
        int passwordHash = password.hashCode();
        Manager found = accountRepo.login(username, passwordHash);
        if (found == null)
            throw new ServiceException("Username or password invalid!\n");
        return found;
    }
}
