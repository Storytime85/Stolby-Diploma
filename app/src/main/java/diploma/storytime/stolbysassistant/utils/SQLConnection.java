package diploma.storytime.stolbysassistant.utils;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
    private static final String LOG = "DEBUG";
    private static String ip = "127.0.0.1";
    private static String port = "3306";
    private static String clas = "com.mysql.jdbc.Driver";
    private static String database = "diploma";
    private static String username = "admin";
    private static String password = "admin";
    public static Connection connect() {
        Connection connection = null;
        String ConnURL = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(clas);
            //ConnURL = "jdbc:mysql://localhost:3306/mkyongcom","root", "password";
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userbase",
                    "root", "");
        } catch (SQLException e) {
            Log.d(LOG, e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.d(LOG, e.getMessage());
        }
        return connection;
    }
}
