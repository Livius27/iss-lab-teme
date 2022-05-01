package repository;

import model.Manager;

public interface IAccountRepository extends ICRUDRepository<Long, Manager> {
    public Manager login(String username, int passwordHash);
}
