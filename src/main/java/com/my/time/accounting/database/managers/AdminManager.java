package com.my.time.accounting.database.managers;

import com.my.time.accounting.entity.Administrator;
import com.my.time.accounting.entity.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.time.accounting.database.SQLConstance.*;
import static com.my.time.accounting.database.managers.Utils.*;

public class AdminManager {
    private AdminManager() {}

    public static void insertAdministrator(Connection connection, Administrator administrator) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = connection.prepareStatement(SQL_ADD_NEW_ADMINISTRATOR, Statement.RETURN_GENERATED_KEYS);

            int k = 1;
            pstmt.setString(k++, administrator.getName());
            pstmt.setString(k++, administrator.getLastName());
            pstmt.setString(k++, administrator.getPassword());
            pstmt.setString(k++, administrator.getEmail());
            pstmt.setString(k++, administrator.getPhoneNumber());
            pstmt.setString(k++, administrator.getCompany());

            if (pstmt.executeUpdate() > 0) {
                resultSet = pstmt.getGeneratedKeys();
                if (resultSet.next()) {
                    administrator.setAdminId(resultSet.getLong(1));
                }
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
    }

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

    public static Administrator searchAdminByEmail(Connection connection, String email) throws SQLException {
        Administrator administrator = new Administrator();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_FIND_ADMINISTRATOR_BY_EMAIL);

            pstmt.setString(1, "%" + escapeForLike(email) + "%");
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
}
