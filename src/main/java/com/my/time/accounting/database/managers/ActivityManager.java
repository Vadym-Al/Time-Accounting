package com.my.time.accounting.database.managers;

import com.my.time.accounting.database.DBException;
import com.my.time.accounting.entity.Activity;
import com.my.time.accounting.entity.Administrator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.time.accounting.database.SQLConstance.*;
import static com.my.time.accounting.database.managers.Utils.*;

public class ActivityManager {
    private ActivityManager(){}

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
}
