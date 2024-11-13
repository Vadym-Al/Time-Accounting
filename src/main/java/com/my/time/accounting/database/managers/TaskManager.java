package com.my.time.accounting.database.managers;

import com.my.time.accounting.entity.Task;
import com.my.time.accounting.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.my.time.accounting.database.SQLConstance.*;
import static com.my.time.accounting.database.managers.Utils.*;

public class TaskManager implements Manager<Task, User>{

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

    @Override
    public void insertRaw(Connection connection, Task entity) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = connection.prepareStatement(SQL_ADD_NEW_TASK, Statement.RETURN_GENERATED_KEYS);

            int k = 1;
            pstmt.setString(k++, entity.getName());
            pstmt.setDate(k++, entity.getDeadline());
            pstmt.setString(k++, entity.getAbout());
            pstmt.setLong(k++, entity.getActivityType());
            pstmt.setLong(k++, entity.getAdministratorId());

            if (pstmt.executeUpdate() > 0) {
                resultSet = pstmt.getGeneratedKeys();
                if (resultSet.next()) {
                    entity.setTaskId(resultSet.getLong(1));
                }
            }
        } finally {
            close(resultSet);
            close(pstmt);
        }
    }

    @Override
    public List<Task> getAllActivitiesByRequest(Connection connection, User request) throws SQLException {
        return null;
    }

    @Override
    public Task getEntityById(Connection connection, long id) throws SQLException {
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

    @Override
    public Task getEntityByName(Connection connection, String name) throws SQLException {
        return null;
    }

    @Override
    public void deleteEntity(Connection connection, long id) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(SQL_DELETE_TASK);
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
