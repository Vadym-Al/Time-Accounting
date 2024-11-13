package com.my.time.accounting.database;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    private final Logger logger = LogManager.getLogger(DBConnection.class);

    private static DBConnection dbManager;
    private final DataSource dataSource;

    private DBConnection() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/TestDB");
        } catch (NamingException ex) {
            logger.error("Can not init DBManager", ex);
            throw new IllegalStateException("Cannot init DBManager", ex);
        }
    }

    public static synchronized DBConnection getInstance() {
        if (dbManager == null) {
            return new DBConnection();
        }
        return dbManager;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
