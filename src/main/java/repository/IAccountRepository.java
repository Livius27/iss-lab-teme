package repository;

import model.Manager;

public interface IAccountRepository extends ICRUDRepository<Integer, Manager> {
    Manager login(String username, int passwordHash);
}
