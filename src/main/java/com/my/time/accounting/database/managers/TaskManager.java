package com.my.time.accounting.database.managers;

import com.my.time.accounting.entity.Task;
import com.my.time.accounting.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.time.accounting.database.SQLConstance.*;
import static com.my.time.accounting.database.managers.Utils.*;

/**
 * Class that contains instructions of processing information about tasks in data base
 *
 * @author Vadym Aldyk
 * @version 1.0
 */
public class TaskManager {
    private TaskManager() {
    }

    /**
     * Instruction of inserting task to database
     * @param connection - connection with database
     * @param task - data base entity
     * @throws SQLException - possible exception
     */
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

    /**
     * Instruction of getting tasks from database
     * @param connection - connection with database
     * @param id - task id
     * @return task from database
     * @throws SQLException - possible exception
     */
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

    /**
     * Instruction of searching task by name
     * @param connection - connection with database
     * @param name - task name
     * @return task from database
     * @throws SQLException - possible exception
     */
    public static Task searchTaskByName(Connection connection, String name) throws SQLException {
        Task task = new Task();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_FIND_TASK_BY_NAME);

            pstmt.setString(1, "%" + escapeForLike(name) + "%");
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

    /**
     * Instruction of getting all task for user
     * @param connection - connection with database
     * @param id - user id
     * @return list of numbers that belong to user
     * @throws SQLException - possible exception
     */
    public static List<Long> getListOfTasks(Connection connection, long id) throws SQLException {
        List<Long> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = connection.prepareStatement(SQL_GET_USER_HAS_TASK_BY_USER);

            pstmt.setLong(1, id);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getLong("task_id"));
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
        return list;
    }

    /**
     * Instruction of changing parameter of task
     * @param connection - connection with database
     * @param id - task id
     * @param time - mutable parameter
     * @throws SQLException - possible exception
     */
    public static void updateTask(Connection connection, long id, Time time) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQL_UPDATE_TASK);
            int k = 1;
            pstmt.setTime(k++, time);
            pstmt.setLong(k++, id);
            pstmt.executeUpdate();
        } finally {
            close(pstmt);
        }
    }

    /**
     * Instruction of appointing task for user
     * @param connection - connection with database
     * @param task - task that will be appoint
     * @param user - user that will get task
     * @throws SQLException - possible exception
     */
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

    /**
     * Instruction of deleting task from database
     * @param connection - connection with database
     * @param id task id
     * @throws SQLException - possible exception
     */
    public static void deleteTask(Connection connection, long id) throws SQLException{
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQL_DELETE_TASK);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } finally {
            close(pstmt);
        }
    }

    /**
     * Instruction of deleting task from user
     * @param connection - connection with database
     * @param id - user id
     * @throws SQLException - possible exception
     */
    public static void deleteUserHasTask(Connection connection, long id) throws SQLException{
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQL_DELETE_USER_HAS_TASK_TASK);
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } finally {
            close(pstmt);
        }
    }
}
