import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionUtils {
    // JDBC variables
    private static final String DB_URL = "jdbc:mysql://localhost:3306/StudentManagementDB";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String jdbcDriver = "com.mysql.jdbc.Driver";

        // Method to establish a connection to the database
    public static Connection getConnection() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class.forName(jdbcDriver).newInstance();
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}
