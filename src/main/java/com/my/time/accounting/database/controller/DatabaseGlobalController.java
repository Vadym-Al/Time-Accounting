package com.my.time.accounting.database.controller;

import com.my.time.accounting.database.DBConnection;
import com.my.time.accounting.database.DBException;
import com.my.time.accounting.database.managers.Manager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.my.time.accounting.database.managers.Utils.close;

public class DatabaseGlobalController <T, R>{
    private final Logger logger = LogManager.getLogger(DatabaseGlobalController.class);
    private final DBConnection dbManager;
    private final Manager<T, R> manager;

    public DatabaseGlobalController(Manager<T, R> manager) {
        this.manager = manager;
        dbManager = DBConnection.getInstance();
    }

    public T searchForEntityByName(String name) throws DBException {
        T entity;
        Connection connection = null;
        try {
            connection = dbManager.getConnection();
            entity = manager.getEntityByName(connection, name);
        } catch (SQLException ex) {
            logger.error("Can not find " + manager.getErrorInfo() + " by name", ex);
            throw new DBException("Can not find " + manager.getErrorInfo() + " by name", ex);
        } finally {
            close(connection);
        }
        return entity;
    }

    public T searchForEntityById(long id) throws DBException {
        T entity;
        Connection connection = null;
        try {
            connection = dbManager.getConnection();
            entity = manager.getEntityById(connection, id);
        } catch (SQLException ex) {
            logger.error("Can not find " + manager.getErrorInfo() + " by name", ex);
            throw new DBException("Can not find " + manager.getErrorInfo() + " by name", ex);
        } finally {
            close(connection);
        }
        return entity;
    }

    public void insertEntity(T entity) throws DBException {
        Connection connection = null;
        try {
            connection = dbManager.getConnection();
            manager.insertRaw(connection, entity);
            connection.commit();
        } catch (SQLException ex) {
            logger.error("Can not insert " + manager.getErrorInfo(), ex);
            throw new DBException("Can not insert " + manager.getErrorInfo(), ex);
        } finally {
            close(connection);
        }
    }

    public List<T> getAllActivitiesByRequest(R request) throws DBException {
        List<T> entities;
        Connection connection = null;
        try {
            connection = dbManager.getConnection();
            entities = manager.getAllActivitiesByRequest(connection, request);
        } catch (SQLException ex) {
            logger.error("Can not get all " + manager.getErrorInfo(), ex);
            throw new DBException("Can not get all " + manager.getErrorInfo(), ex);
        } finally {
            close(connection);
        }
        return entities;
    }

    public void deleteEntity(long id) throws DBException {
        Connection connection = null;
        try {
            connection = dbManager.getConnection();
            manager.deleteEntity(connection, id);
            connection.commit();
        } catch (SQLException ex) {
            logger.error("Can not delete " + manager.getErrorInfo(), ex);
            throw new DBException("Can not delete " + manager.getErrorInfo(), ex);
        } finally {
            close(connection);
        }
    }
}
