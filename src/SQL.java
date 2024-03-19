

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
public class SQL {
    public static Connection makeConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/SIS";
        String username = "root";
        String password = "sarthak123";
        Connection c;

            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(url, username, password);

        return c;
    }

}