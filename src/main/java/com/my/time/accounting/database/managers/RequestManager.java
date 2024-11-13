package com.my.time.accounting.database.managers;

import com.my.time.accounting.entity.Administrator;
import com.my.time.accounting.entity.Request;
import com.my.time.accounting.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.time.accounting.database.SQLConstance.*;
import static com.my.time.accounting.database.managers.Utils.*;

public class RequestManager implements Manager<Request, User>{

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


    public static void deleteRequestFoUser(Connection connection, long id) throws SQLException{
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQL_DELETE_REQUEST_FOR_USER);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } finally {
            close(pstmt);
        }
    }

    @Override
    public void insertRaw(Connection connection, Request entity) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_ADD_NEW_REQUEST, Statement.RETURN_GENERATED_KEYS);

            int k = 1;
            pstmt.setString(k++, entity.getAbout());
            pstmt.setLong(k++, entity.getActivityId());
            pstmt.setLong(k++, entity.getAdministratorId());
            pstmt.setLong(k++, entity.getUserId());

            if (pstmt.executeUpdate() > 0) {
                resultSet = pstmt.getGeneratedKeys();
                if (resultSet.next()) {
                    entity.setActivityId(resultSet.getLong(1));
                }
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
    }

    @Override
    public List<Request> getAllActivitiesByRequest(Connection connection, User request) throws SQLException {
        List<Request> requests = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_GET_REQUESTS_BY_USER);

            pstmt.setLong(1, request.getUserId());
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

    @Override
    public Request getEntityById(Connection connection, long id) throws SQLException {
        Request request = new Request();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_GET_REQUEST_BY_ID);

            pstmt.setLong(1, id);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                request = mapRequest(resultSet);
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
        return request;
    }

    @Override
    public Request getEntityByName(Connection connection, String name) throws SQLException {
        return null;
    }

    @Override
    public void deleteEntity(Connection connection, long id) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQL_DELETE_REQUEST);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } finally {
            close(pstmt);
        }
    }

    @Override
    public String getErrorInfo() {
        return "Request";
    }
}
