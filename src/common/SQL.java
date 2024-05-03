package common;
import java.sql.Connection;
import java.sql.DriverManager;

public class SQL {
    public static Connection makeConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/LMS";
        String username = "root";
        String password = "sarthak123";
        Connection c;

            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(url, username, password);

        return c;
    }

}