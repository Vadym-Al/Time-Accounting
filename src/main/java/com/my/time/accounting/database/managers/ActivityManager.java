package com.my.time.accounting.database.managers;

import com.my.time.accounting.entity.Activity;
import com.my.time.accounting.entity.Administrator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.time.accounting.database.SQLConstance.*;
import static com.my.time.accounting.database.managers.Utils.*;


public class ActivityManager implements Manager<Activity, Administrator> {

    @Override
    public void insertRaw(Connection connection, Activity entity) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_ADD_NEW_ACTIVITY_TYPE, Statement.RETURN_GENERATED_KEYS);

            int k = 1;
            pstmt.setString(k++, entity.getName());
            pstmt.setString(k++, entity.getAbout());
            pstmt.setLong(k++, entity.getAdministratorId());

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
    public List<Activity> getAllActivitiesByRequest(Connection connection, Administrator request) throws SQLException {
        List<Activity> activities = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_GET_ACTIVITIES_BY_ADMIN);

            pstmt.setLong(1, request.getAdminId());
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                activities.add(mapActivity(resultSet));
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
        return activities;
    }

    @Override
    public Activity getEntityById(Connection connection, long id) throws SQLException {
        Activity activity = new Activity();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_FIND_ACTIVITY_BY_ID);

            pstmt.setLong(1, id);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                activity = mapActivity(resultSet);
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
        return activity;
    }

    @Override
    public Activity getEntityByName(Connection connection, String name) throws SQLException {
        Activity activity = new Activity();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_FIND_ACTIVITY_BY_NAME);

            pstmt.setString(1, "%" + escapeForLike(name) + "%");
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                activity = mapActivity(resultSet);
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
        return activity;
    }

    @Override
    public void deleteEntity(Connection connection, long id) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQL_DELETE_ACTIVITY);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } finally {
            close(pstmt);
        }
    }

    @Override
    public String getErrorInfo() {
        return "Activity";
    }
}
