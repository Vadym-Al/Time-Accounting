package com.my.time.accounting.database.managers;

import com.my.time.accounting.entity.Activity;
import com.my.time.accounting.entity.Administrator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.time.accounting.database.SQLConstance.*;
import static com.my.time.accounting.database.managers.Utils.*;

/**
 * Class that contains instructions of processing information about activities in data base
 *
 * @author Vadym Aldyk
 * @version 1.0
 */
public class ActivityManager {
    /**
     * private constructor
     * @see ActivityManager#ActivityManager()
     */
    private ActivityManager(){}

    /**
     * Instruction of inserting activity to database
     * @param connection - connection with database
     * @param activity - data base entity
     * @throws SQLException - possible exception
     */
    public static void insertActivity(Connection connection, Activity activity) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_ADD_NEW_ACTIVITY_TYPE, Statement.RETURN_GENERATED_KEYS);

            int k = 1;
            pstmt.setString(k++, activity.getName());
            pstmt.setString(k++, activity.getAbout());
            pstmt.setLong(k++, activity.getAdministratorId());

            if (pstmt.executeUpdate() > 0) {
                resultSet = pstmt.getGeneratedKeys();
                if (resultSet.next()) {
                    activity.setActivityId(resultSet.getLong(1));
                }
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
    }

    /**
     * Instruction of getting all activities from database for admin
     * @param connection - connection with database
     * @param administrator - person that take data
     * @return list of activities for administrator
     * @throws SQLException - possible exception
     */
    public static List<Activity> getAllActivitiesForAdmin(Connection connection, Administrator administrator) throws SQLException {
        List<Activity> activities = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_GET_ACTIVITIES_BY_ADMIN);

            pstmt.setLong(1, administrator.getAdminId());
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

    /**
     * Instruction of searching activity by his id
     * @param connection - connection with database
     * @param id - activity id
     * @return activity from database
     * @throws SQLException - possible exception
     */
    public static Activity searchActivityById(Connection connection, long id) throws SQLException {
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

    /**
     * Instruction of searching activity by name
     * @param connection - connection with database
     * @param name - name of activity
     * @return activity from database
     * @throws SQLException - possible exception
     */
    public static Activity searchActivityByName(Connection connection, String name) throws SQLException {
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

    /**
     * Instruction of deleting activity from database
     * @param connection - connection with database
     * @param id - activity id
     * @throws SQLException - possible exception
     */
    public static void deleteActivity(Connection connection, long id) throws SQLException{
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQL_DELETE_ACTIVITY);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } finally {
            close(pstmt);
        }
    }
}
