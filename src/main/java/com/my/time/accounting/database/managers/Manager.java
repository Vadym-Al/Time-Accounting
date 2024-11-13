package com.my.time.accounting.database.managers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Manager<T, R> {

    void insertRaw(Connection connection, T entity) throws SQLException;

    List<T> getAllActivitiesByRequest(Connection connection, R request) throws SQLException;

    T getEntityById(Connection connection, long id) throws SQLException;

    T getEntityByName(Connection connection, String name) throws SQLException;

    void deleteEntity(Connection connection, long id) throws SQLException;

    String getErrorInfo();
}
