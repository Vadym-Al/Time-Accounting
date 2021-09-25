package com.my.time.accounting.database.managers;

import com.my.time.accounting.entity.Administrator;
import com.my.time.accounting.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.time.accounting.database.SQLConstance.*;
import static com.my.time.accounting.database.managers.Utils.*;

public class UserManager {
    private UserManager(){}

    public static void insertUser(Connection connection, User user) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_ADD_NEW_USER, Statement.RETURN_GENERATED_KEYS);

            int k = 1;
            pstmt.setString(k++, user.getName());
            pstmt.setString(k++, user.getLastName());
            pstmt.setString(k++, user.getPassword());
            pstmt.setString(k++, user.getEmail());
            pstmt.setString(k++, user.getPhoneNumber());
            pstmt.setString(k++, user.getCompany());
            pstmt.setLong(k++, user.getTeamId());
            pstmt.setLong(k++, user.getAdministratorId());

            if (pstmt.executeUpdate() > 0) {
                resultSet = pstmt.getGeneratedKeys();
                if (resultSet.next()) {
                    user.setUserId(resultSet.getLong(1));
                }
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
    }

    public static List<User> getAllUsersForAdmin(Connection connection, Administrator administrator) throws SQLException {
        List<User> users = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_GET_USERS_BY_ADMIN);

            pstmt.setLong(1, administrator.getAdminId());
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
    public static User searchUserByName(Connection connection, String name) throws SQLException {
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

    public static void deleteUser(Connection connection, long id) throws SQLException{
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQL_DELETE_USER);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } finally {
            close(pstmt);
        }
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
}
