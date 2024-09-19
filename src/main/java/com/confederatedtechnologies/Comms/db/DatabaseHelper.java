package com.confederatedtechnologies.Comms.db;

import com.gluonhq.attach.storage.StorageService;
import com.gluonhq.attach.util.Platform;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseHelper {
    private static String DB_URL = "jdbc:h2:";
    private static Logger LOGGER = Logger.getLogger(DatabaseHelper.class.getName());

    public static Collection<String> initialize() {
        LOGGER.log(Level.INFO, "Initializing database");
        Optional<StorageService> storageService = StorageService.create();
        if (storageService.isPresent()) {
            StorageService service = storageService.get();
            Optional<File> privateStoragePath = service.getPrivateStorage();

            if (privateStoragePath.isPresent()) {
                try {
                     LOGGER.log(Level.INFO, "Private storage path: {0}", privateStoragePath.get());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                DB_URL += privateStoragePath.get() + "comms.db";
                LOGGER.log(Level.INFO, "Database URL: {0}", DB_URL);

                try (Connection conn = DriverManager.getConnection(DB_URL)) {

                    if (conn != null) {
                        createUsersTable(conn);
                        insertDefaultUsers(conn);
                    }
                    LOGGER.log(Level.INFO, "Database initialized");
                    return getUsers(conn);
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, "Exception: {0}", e);
                }
            } else {
                LOGGER.log(Level.WARNING, "Private storage path not available");
            }
        } else {
            LOGGER.log(Level.WARNING, "Storage service not available");
        }

        return new ArrayList<>();
    }

    private static void createUsersTable(Connection conn) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, username TEXT, password TEXT)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        }
    }

    private static void insertDefaultUsers(Connection conn) throws SQLException {
        String insertUserSQL = "INSERT INTO users (id, username, password) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertUserSQL)) {
            pstmt.setInt(1, 1);
            pstmt.setString(2, "user1");
            pstmt.setString(3, "password1");
            pstmt.execute();

            pstmt.setInt(1, 2);
            pstmt.setString(2, "user2");
            pstmt.setString(3, "password2");
            pstmt.execute();
        }
    }

    public static Collection<String> getUsers(Connection conn) {
        List<String> users = new ArrayList<>();
        String selectSQL = "SELECT * FROM users";

        try (Statement stmt = conn.createStatement();
             ResultSet results = stmt.executeQuery(selectSQL)) {

            while (results.next()) {
                users.add(results.getString("username"));
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        return users;
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Exception: {0}", e);
        }
        return null;
    }
}
