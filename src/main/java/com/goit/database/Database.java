package com.goit.database;

import com.goit.prefs.Prefs;
import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final Database INSTANCE = new Database();
    private Connection connection;

    private Database(){
        try {
            String connectionUrl = new Prefs().getString(Prefs.JDBC_CONNECTION_URL);
            connection = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Database getInstance() {
        return INSTANCE;
    }

    public Connection getConnection(){
        return  connection;
    }

    public void migrate() {
        String url = new Prefs().getString(Prefs.JDBC_CONNECTION_URL);
        Flyway flyway = Flyway.configure().dataSource(url, null, null).load();
        flyway.migrate();
    }
}
