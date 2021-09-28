package com.my.time.accounting.database.managers;

import com.my.time.accounting.entity.Administrator;
import com.my.time.accounting.entity.Request;
import com.my.time.accounting.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.time.accounting.database.SQLConstance.*;
import static com.my.time.accounting.database.managers.Utils.*;

/**
 * Class that contains instructions of processing information about requests in data base
 *
 * @author Vadym Aldyk
 * @version 1.0
 */
public class RequestManager {
    /**
     * private constructor
     * @see RequestManager#RequestManager()
     */
    private RequestManager(){}

    /**
     * Instruction of inserting request to database
     * @param connection - connection with database
     * @param request - data base entity
     * @throws SQLException - possible exception
     */
    public static void insertRequest(Connection connection, Request request) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_ADD_NEW_REQUEST, Statement.RETURN_GENERATED_KEYS);

            int k = 1;
            pstmt.setString(k++, request.getAbout());
            pstmt.setLong(k++, request.getActivityId());
            pstmt.setLong(k++, request.getAdministratorId());
            pstmt.setLong(k++, request.getUserId());

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

    /**
     * Instruction of getting all requests from database
     * @param connection - connection with database
     * @param user - person that take data
     * @return list of requests for user
     * @throws SQLException - possible exception
     */
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

    /**
     * Instruction of getting all requests from database
     * @param connection - connection with database
     * @param administrator - person that take data
     * @return list of requests for administrator
     * @throws SQLException - possible exception
     */
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

    /**
     * Instruction of searching request by his id
     * @param connection - connection with database
     * @param id - activity id
     * @return request from database
     * @throws SQLException - possible exception
     */
    public static Request searchRequestById(Connection connection, long id) throws SQLException {
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

    /**
     * Instruction of deleting request from database
     * @param connection - connection with database
     * @param id - request id
     * @throws SQLException - possible exception
     */
    public static void deleteRequest(Connection connection, long id) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQL_DELETE_REQUEST);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } finally {
            close(pstmt);
        }
    }

    /**
     * Instruction of deleting request from database for user
     * @param connection - connection with database
     * @param id - request id
     * @throws SQLException - possible exception
     */
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
}
