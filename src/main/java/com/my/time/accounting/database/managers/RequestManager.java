package com.my.time.accounting.database.managers;

import com.my.time.accounting.entity.Administrator;
import com.my.time.accounting.entity.Request;
import com.my.time.accounting.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.time.accounting.database.SQLConstance.*;
import static com.my.time.accounting.database.managers.Utils.*;

public class RequestManager {
    private RequestManager(){}

    public static void insertRequest(Connection connection, Request request) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_ADD_NEW_REQUEST, Statement.RETURN_GENERATED_KEYS);

            int k = 1;
            pstmt.setString(k++, request.getAbout());
            pstmt.setLong(k++, request.getActivityId());
            pstmt.setLong(k++, request.getAdministratorId());

            if (pstmt.executeUpdate() > 0) {
                resultSet = pstmt.getGeneratedKeys();
                if (resultSet.next()) {
                    request.setActivityId(resultSet.getLong(1));
                }
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
    }

    public static List<Request> getAllRequestsForUser(Connection connection, User user) throws SQLException {
        List<Request> requests = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_GET_REQUESTS_BY_USER);

            pstmt.setLong(1, user.getUserId());
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                requests.add(mapRequest(resultSet));
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
        return requests;
    }

    public static List<Request> getAllRequestsForAdmin(Connection connection, Administrator administrator) throws SQLException {
        List<Request> requests = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_GET_REQUESTS_BY_ADMIN);

            pstmt.setLong(1, administrator.getAdminId());
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                requests.add(mapRequest(resultSet));
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
        return requests;
    }
}
