package repository.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {
    private Properties jdbcProps;
    private Connection instance = null;

    public JDBCUtils(Properties props) {
        jdbcProps = props;
    }

    private Connection getNewConnection() {
        String url = jdbcProps.getProperty("jdbc.url");
        String user = jdbcProps.getProperty("jdbc.user");
        String pass = jdbcProps.getProperty("jdbc.pass");

        Connection conn = null;
        try {
            if (user != null && pass != null)
                conn = DriverManager.getConnection(url, user, pass);
            else
                conn = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            System.out.println("Error getting connection " + ex);
        }
        return conn;
    }

    public Connection getConnection() {
        try {
            if (instance == null || instance.isClosed())
                instance = getNewConnection();

        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
        return instance;
    }
}
