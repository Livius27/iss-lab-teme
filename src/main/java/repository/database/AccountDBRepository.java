package repository.database;

import model.Manager;
import repository.IAccountRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AccountDBRepository implements IAccountRepository {
    private JDBCUtils dbUtils;

    public AccountDBRepository(Properties props) {
        this.dbUtils = new JDBCUtils(props);
    }

    @Override
    public Manager findOne(Long id) {
        Connection conn = dbUtils.getConnection();
        Manager account = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM Accounts WHERE AID = (?)")) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String username = resultSet.getString("username");
                    int passwordHash = resultSet.getInt("password_hash");
                    account = new Manager(username, passwordHash);
                    account.setId(id);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error DB" + ex);
        }
        return account;
    }

    @Override
    public Iterable<Manager> findAll() {
        Connection conn = dbUtils.getConnection();
        List<Manager> accounts = new ArrayList<Manager>();

        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM Accounts")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long id = resultSet.getLong("AID");
                    String username = resultSet.getString("username");
                    int passwordHash = resultSet.getInt("password_hash");
                    Manager account = new Manager(username, passwordHash);
                    account.setId(id);
                    accounts.add(account);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error DB" + ex);
        }
        return accounts;
    }

    @Override
    public Manager save(Manager account) {
        return null;
    }

    @Override
    public Manager delete(Long id) {
        return null;
    }

    @Override
    public Manager update(Manager account) {
        return null;
    }

    @Override
    public Manager login(String username, int passwordHash) {
        Connection conn = dbUtils.getConnection();
        Manager account = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM Accounts WHERE username = (?) AND password_hash = (?)")) {
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, passwordHash);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("AID");
                    account = new Manager(username, passwordHash);
                    account.setId(id);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error DB" + ex);
        }
        return account;
    }
}
