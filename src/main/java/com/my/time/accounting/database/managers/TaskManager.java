package com.my.time.accounting.database.managers;

import com.my.time.accounting.entity.Task;
import com.my.time.accounting.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.time.accounting.database.SQLConstance.*;
import static com.my.time.accounting.database.managers.Utils.*;

public class TaskManager {
    private TaskManager() {
    }

    public static void insertTask(Connection connection, Task task) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_ADD_NEW_TASK, Statement.RETURN_GENERATED_KEYS);

            int k = 1;
            pstmt.setString(k++, task.getName());
            pstmt.setDate(k++, task.getDeadline());
            pstmt.setString(k++, task.getAbout());
            pstmt.setLong(k++, task.getActivityType());
            pstmt.setLong(k++, task.getAdministratorId());

            if (pstmt.executeUpdate() > 0) {
                resultSet = pstmt.getGeneratedKeys();
                if (resultSet.next()) {
                    task.setTaskId(resultSet.getLong(1));
                }
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
    }

    public static Task getTasksForUserById(Connection connection, long id) throws SQLException {
        Task task = new Task();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = connection.prepareStatement(SQL_GET_TASKS_BY_ID);

            pstmt.setLong(1, id);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                task = mapTask(resultSet);
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
        return task;
    }

    public static List<Long> getListOfTasks(Connection connection, User user) throws SQLException {
        List<Long> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = connection.prepareStatement(SQL_GET_USER_HAS_TASK_BY_USER);

            pstmt.setLong(1, user.getUserId());
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getLong(""));
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
        return list;
    }

    public static void setTask(Connection connection, Task task, User user) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQL_ADD_TASK_TO_USER);

            int k = 1;
            pstmt.setLong(k++, user.getUserId());
            pstmt.setLong(k++, task.getTaskId());
            pstmt.executeUpdate();
        } finally {
            close(pstmt);
        }
    }
}
