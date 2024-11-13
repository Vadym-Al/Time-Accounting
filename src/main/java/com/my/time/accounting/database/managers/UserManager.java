package com.my.time.accounting.database.managers;

import com.my.time.accounting.entity.Administrator;
import com.my.time.accounting.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.time.accounting.database.SQLConstance.*;
import static com.my.time.accounting.database.managers.Utils.*;

public class UserManager implements Manager<User, Administrator>{

    public static User searchUserByEmail(Connection connection, String email) throws SQLException {
        User user = new User();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_FIND_USER_BY_EMAIL);

            pstmt.setString(1, "%" + escapeForLike(email) + "%");
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                user = mapUser(resultSet);
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
        return user;
    }

    public static List<User> searchUserByTeamId(Connection connection, long id) throws SQLException {
        List<User> users = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = connection.prepareStatement(SQL_FIND_USER_BY_TEAM_ID);

            pstmt.setLong(1, id);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                users.add(mapUser(resultSet));
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
        return users;
    }


    public static void deleteUserHasTask(Connection connection, long id) throws SQLException{
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQL_DELETE_USER_HAS_TASK_USER);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } finally {
            close(pstmt);
        }
    }

    @Override
    public void insertRaw(Connection connection, User entity) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_ADD_NEW_USER, Statement.RETURN_GENERATED_KEYS);

            int k = 1;
            pstmt.setString(k++, entity.getName());
            pstmt.setString(k++, entity.getLastName());
            pstmt.setString(k++, entity.getPassword());
            pstmt.setString(k++, entity.getEmail());
            pstmt.setString(k++, entity.getPhoneNumber());
            pstmt.setString(k++, entity.getCompany());
            pstmt.setLong(k++, entity.getTeamId());
            pstmt.setLong(k++, entity.getAdministratorId());

            if (pstmt.executeUpdate() > 0) {
                resultSet = pstmt.getGeneratedKeys();
                if (resultSet.next()) {
                    entity.setUserId(resultSet.getLong(1));
                }
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
    }

    @Override
    public List<User> getAllActivitiesByRequest(Connection connection, Administrator request) throws SQLException {
        List<User> users = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_GET_USERS_BY_ADMIN);

            pstmt.setLong(1, request.getAdminId());
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                users.add(mapUser(resultSet));
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
        return users;
    }

    @Override
    public User getEntityById(Connection connection, long id) throws SQLException {
        User user = new User();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = connection.prepareStatement(SQL_FIND_USER_BY_ID);

            pstmt.setLong(1, id);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                user = mapUser(resultSet);
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
        return user;
    }

    @Override
    public User getEntityByName(Connection connection, String name) throws SQLException {
        User user = new User();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = connection.prepareStatement(SQL_FIND_USER_BY_NAME);

            pstmt.setString(1, "%" + escapeForLike(name) + "%");
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                user = mapUser(resultSet);
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
        return user;
    }

    @Override
    public void deleteEntity(Connection connection, long id) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQL_DELETE_USER);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } finally {
            close(pstmt);
        }
    }

    @Override
    public String getErrorInfo() {
        return "Task";
    }
}
