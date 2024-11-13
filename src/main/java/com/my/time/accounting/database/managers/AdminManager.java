package com.my.time.accounting.database.managers;

import com.my.time.accounting.entity.Administrator;
import com.my.time.accounting.entity.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.time.accounting.database.SQLConstance.*;
import static com.my.time.accounting.database.managers.Utils.*;

public class AdminManager implements Manager<Administrator, Task>{

    public static List<Task> getAllTasksForAdmin(Connection connection, Administrator administrator) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_GET_TASKS_BY_ADMIN);

            pstmt.setLong(1, administrator.getAdminId());
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                tasks.add(mapTask(resultSet));
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
        return tasks;
    }

    public static List<Task> getAllTasksForAdminSorted(Connection connection, Administrator administrator) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_GET_ALL_TASKS_BY_ADMIN_SORT);

            pstmt.setLong(1, administrator.getAdminId());
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                tasks.add(mapTask(resultSet));
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
        return tasks;
    }

    @Override
    public void insertRaw(Connection connection, Administrator entity) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = connection.prepareStatement(SQL_ADD_NEW_ADMINISTRATOR, Statement.RETURN_GENERATED_KEYS);

            int k = 1;
            pstmt.setString(k++, entity.getName());
            pstmt.setString(k++, entity.getLastName());
            pstmt.setString(k++, entity.getPassword());
            pstmt.setString(k++, entity.getEmail());
            pstmt.setString(k++, entity.getPhoneNumber());
            pstmt.setString(k++, entity.getCompany());

            if (pstmt.executeUpdate() > 0) {
                resultSet = pstmt.getGeneratedKeys();
                if (resultSet.next()) {
                    entity.setAdminId(resultSet.getLong(1));
                }
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
    }

    @Override
    public List<Administrator> getAllActivitiesByRequest(Connection connection, Task request) throws SQLException {
        return null;
    }

    @Override
    public Administrator getEntityById(Connection connection, long id) throws SQLException {
        Administrator administrator = new Administrator();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = connection.prepareStatement(SQL_FIND_ADMINISTRATOR_BY_ID);

            pstmt.setLong(1, id);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                administrator = mapAdministrator(resultSet);
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
        return administrator;
    }

    @Override
    public Administrator getEntityByName(Connection connection, String name) throws SQLException {
        Administrator administrator = new Administrator();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_FIND_ADMINISTRATOR_BY_EMAIL);

            pstmt.setString(1, "%" + escapeForLike(name) + "%");
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                administrator = mapAdministrator(resultSet);
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
        return administrator;
    }

    @Override
    public void deleteEntity(Connection connection, long id) throws SQLException {

    }

    @Override
    public String getErrorInfo() {
        return "Admin";
    }
}
