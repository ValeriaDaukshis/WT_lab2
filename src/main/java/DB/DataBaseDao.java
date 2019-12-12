package DB;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseDao {
    private static final Logger log = Logger.getLogger(DataBaseDao.class);
    private static String url;
    private static String username;
    private static String password;
    private static Connection db;

    public static Connection connect() {
        db = null;
        ReadProperties();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            db = DriverManager.getConnection(url, username, password);
        }catch (ClassNotFoundException ex){
            log.error("Connection with db failed: " + ex);
        }
        catch (SQLException ex) {
            log.error("Connection with db failed: " + ex);
        }
        return db;
    }

    public void close() {
        try {
            db.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    private static void ReadProperties(){
        Properties props = new Properties();
        try{
            FileInputStream in = new FileInputStream("./src/main/resources/database.properties");
            props.load(in);
            in.close();
        } catch (IOException ex){
            log.error("Can not find database.properties: " + ex);
        }

        url = props.getProperty("db.url");
        username = props.getProperty("db.user");
        password = props.getProperty("db.password");
    }
}
