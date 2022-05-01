package repository.database;

import model.Spectacol;
import repository.ISpectacolRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SpectacolDBRepository implements ISpectacolRepository {
    private JDBCUtils dbUtils;

    public SpectacolDBRepository(Properties props) {
        this.dbUtils = new JDBCUtils(props);
    }

    @Override
    public Spectacol findOne(Long id) {
        Connection conn = dbUtils.getConnection();
        Spectacol spectacol = null;

        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM Spectacole WHERE SID = (?)")) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String titlu = resultSet.getString("titlu");
                    String data = resultSet.getString("data");
                    int nrLocuriDisponibile = resultSet.getInt("nr_locuri_disponibile");
                    spectacol = new Spectacol(titlu, data, nrLocuriDisponibile);
                    spectacol.setId(id);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error DB" + ex);
        }
        return spectacol;
    }

    @Override
    public Iterable<Spectacol> findAll() {
        Connection conn = dbUtils.getConnection();
        List<Spectacol> spectacole = new ArrayList<Spectacol>();

        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM Spectacole")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long id = resultSet.getLong("SID");
                    String titlu = resultSet.getString("titlu");
                    String data = resultSet.getString("data");
                    int nrLocuriDisponibile = resultSet.getInt("nr_locuri_disponibile");
                    Spectacol spectacol = new Spectacol(titlu, data, nrLocuriDisponibile);
                    spectacol.setId(id);
                    spectacole.add(spectacol);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error DB" + ex);
        }
        return spectacole;
    }

    @Override
    public Spectacol save(Spectacol spectacol) {
        Connection conn = dbUtils.getConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO Spectacole (titlu, data, nr_locuri_disponibile) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, spectacol.getTitlu());
            preparedStatement.setString(2, spectacol.getData());
            preparedStatement.setInt(3, spectacol.getNrLocuriDisponibile());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Error DB" + ex);
        }
        return spectacol;
    }

    @Override
    public Spectacol delete(Long id) {
        Connection conn = dbUtils.getConnection();
        Spectacol deleted = findOne(id);

        try (PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM Spectacole WHERE SID = (?)")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Error DB" + ex);
        }
        return deleted;
    }

    @Override
    public Spectacol update(Spectacol spectacol) {
        Connection conn = dbUtils.getConnection();

        try (PreparedStatement preparedStatement = conn.prepareStatement("UPDATE Spectacole SET titlu = (?), data = (?) WHERE SID = (?)")) {
            preparedStatement.setString(1, spectacol.getTitlu());
            preparedStatement.setString(2, spectacol.getData());
            preparedStatement.setLong(3, spectacol.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Error DB" + ex);
        }
        return spectacol;
    }

    @Override
    public Iterable<Spectacol> sortSpectacoleByNrLocuriDisponibile() {
        Connection conn = dbUtils.getConnection();
        List<Spectacol> spectacole = new ArrayList<Spectacol>();

        try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM Spectacole ORDER BY nr_locuri_disponibile DESC")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long id = resultSet.getLong("SID");
                    String titlu = resultSet.getString("titlu");
                    String data = resultSet.getString("data");
                    int nrLocuriDisponibile = resultSet.getInt("nr_locuri_disponibile");
                    Spectacol spectacol = new Spectacol(titlu, data, nrLocuriDisponibile);
                    spectacol.setId(id);
                    spectacole.add(spectacol);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error DB" + ex);
        }
        return spectacole;
    }
}
