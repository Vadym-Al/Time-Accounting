package com.my.time.accounting.database;

import static com.my.time.accounting.database.SQLConstance.*;
import static com.my.time.accounting.database.managers.Utils.*;

import com.my.time.accounting.database.managers.*;
import com.my.time.accounting.entity.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private final Logger logger = LogManager.getLogger(DBManager.class);
    private static DBManager dbManager;
    private final DataSource dataSource;

    private DBManager() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/TestDB");
        } catch (NamingException ex) {
            logger.error("Can not init DBManager", ex);
            throw new IllegalStateException("Cannot init DBManager", ex);
        }
    }

    public static synchronized DBManager getInstance() {
        if (dbManager == null) {
            return new DBManager();
        }
        return dbManager;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public Administrator searchAdminByEmail(String email) throws DBException {
        Administrator administrator;
        Connection connection = null;

        try {
            connection = getConnection();
            administrator = AdminManager.searchAdminByEmail(connection, email);
        } catch (SQLException ex) {
            logger.error("Can not find Administrator", ex);
            throw new DBException("Can not find Administrator", ex);
        } finally {
            close(connection);
        }
        return administrator;
    }

    public User searchUserByEmail(String email) throws DBException {
        User user;
        Connection connection = null;

        try {
            connection = getConnection();
            user = UserManager.searchUserByEmail(connection, email);
        } catch (SQLException ex) {
            logger.error("Can not find User", ex);
            throw new DBException("Can not find USer", ex);
        } finally {
            close(connection);
        }
        return user;
    }

    public Team searchTeamByName(String name) throws DBException {
        Team team;
        Connection connection = null;

        try {
            connection = getConnection();
            team = TeamManager.searchTeamByName(connection, name);
        } catch (SQLException ex) {
            logger.error("Can not find User", ex);
            throw new DBException("Can not find User", ex);
        } finally {
            close(connection);
        }
        return team;
    }

    public Activity searchActivityByName(String name) throws DBException {
        Activity activity;
        Connection connection = null;

        try {
            activity = ActivityManager.searchActivityByName(connection, name);
        } catch (SQLException ex) {
            logger.error("Can not find User", ex);
            throw new DBException("Can not find User", ex);
        } finally {
            close(connection);
        }
        return activity;
    }

    public void insertAdministrator(Administrator administrator) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            AdminManager.insertAdministrator(connection, administrator);
            connection.commit();
        } catch (SQLException ex) {
            logger.error("Can not insert Admin", ex);
            throw new DBException("Can not insert Admin", ex);
        } finally {
            close(connection);
        }
    }

    public void insertActivity(Activity activity) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();
            ActivityManager.insertActivity(connection, activity);
            connection.commit();
        } catch (SQLException ex) {
            logger.error("Can not insert Activity", ex);
            throw new DBException("Can not insert Activity", ex);
        } finally {
            close(connection);
        }
    }

    public void insertRequest(Request request) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();
            RequestManager.insertRequest(connection, request);
            connection.commit();
        } catch (SQLException ex) {
            logger.error("Can not insert Request", ex);
            throw new DBException("Can not insert Request", ex);
        } finally {
            close(connection);
        }
    }

    public void insertTask(Task task) throws DBException {
        Connection connection = null;

        try {
            connection = getConnection();
            TaskManager.insertTask(connection, task);
            connection.commit();
        } catch (SQLException ex) {
            logger.error("Can not insert Task", ex);
            throw new DBException("Can not insert Task", ex);
        } finally {
            close(connection);
        }
    }

    public void setTask(Task task, User...users) throws DBException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(SQL_ADD_TASK_TO_USER);

            for (User user: users){
                pstmt.setLong(1, user.getUserId());
                pstmt.setLong(2, task.getTaskId());
                pstmt.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            logger.error("Can not insert Task", ex);
            throw new DBException("Can not insert Task", ex);
        } finally {
            close(resultSet);
            close(pstmt);
            close(connection);
        }
    }

    public void addAndUpdateTask(Task task, String name) throws DBException {
        try {
            insertTask(task);
            setTask(task, searchUserByName(name));
        } catch (DBException ex) {
            logger.error("Can not insert Task", ex);
            throw new DBException("Can not insert Task", ex);
        }
    }

    public void insertTeam(Team team) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            TeamManager.insertTeam(connection, team);
            connection.commit();
        } catch (SQLException ex) {
            logger.error("Can not insert Team", ex);
            throw new DBException("Can not insert Team", ex);
        } finally {
            close(connection);
        }
    }

    public void insertUser(User user) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            UserManager.insertUser(connection, user);
            connection.commit();
        } catch (SQLException ex) {
            logger.error("Can not insert User", ex);
            throw new DBException("Can not insert User", ex);
        } finally {
            close(connection);
        }
    }

    public List<Activity> getAllActivitiesForAdmin(String email) throws DBException {
        List<Activity> activities;
        Connection connection = null;

        try {
            connection = getConnection();
            Administrator administrator = searchAdminByEmail(email);
            activities = ActivityManager.getAllActivitiesForAdmin(connection, administrator);
        } catch (SQLException ex) {
            logger.error("Can not get all Activities", ex);
            throw new DBException("Can not get all Activities", ex);
        } finally {
            close(connection);
        }
        return activities;
    }

    public List<Request> getAllRequestsForAdmin(String email) throws DBException {
        List<Request> requests;
        Connection connection = null;

        try {
            connection = getConnection();
            Administrator administrator = searchAdminByEmail(email);
            requests = RequestManager.getAllRequestsForAdmin(connection, administrator);
        } catch (SQLException ex) {
            logger.error("Can not get all Requests", ex);
            throw new DBException("Can not get all Requests", ex);
        } finally {
            close(connection);
        }
        return requests;
    }

    public List<Task> getAllTasksForUser(String email) throws DBException {
        List<Task> tasks = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(SQL_GET_TASKS_BY_USER_HAS_TASK);

            pstmt.setLong(1, searchUserByEmail(email).getUserId());
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                tasks.add(getIdTasksForUser(resultSet.getLong("task_id")));
            }
        } catch (SQLException ex) {
            logger.error("Can not get all Tasks ID", ex);
            throw new DBException("Can not get all Tasks ID", ex);
        } finally {
            close(resultSet);
            close(pstmt);
            close(connection);
        }
        return tasks;
    }

    public Task getIdTasksForUser(long id) throws DBException {
        Task task = new Task();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(SQL_GET_TASKS_BY_TASK_ID);

                pstmt.setLong(1, id);
                resultSet = pstmt.executeQuery();
                while (resultSet.next()) {
                    task = mapTask(resultSet);
                }
        } catch (SQLException ex) {
            logger.error("Can not get all Tasks", ex);
            throw new DBException("Can not get all Tasks", ex);
        } finally {
            close(resultSet);
            close(pstmt);
            close(connection);
        }
        return task;
    }

    public List<Task> getAllTasksForAdmin(String email) throws DBException {
        List<Task> tasks;
        Connection connection = null;

        try {
            connection = getConnection();
            Administrator administrator = searchAdminByEmail(email);
            tasks = AdminManager.getAllTasksForAdmin(connection, administrator);
        } catch (SQLException ex) {
            logger.error("Can not get all Tasks for admin", ex);
            throw new DBException("Can not get all Tasks for admin", ex);
        } finally {
            close(connection);
        }
        return tasks;
    }

    public List<Team> getAllTeamsForAdmin(String email) throws DBException {
        List<Team> teams;
        Connection connection = null;

        try {
            connection = getConnection();
            Administrator administrator = searchAdminByEmail(email);
            teams = TeamManager.getAllTeamsForAdmin(connection, administrator);
        } catch (SQLException ex) {
            logger.error("Can not get all Teams", ex);
            throw new DBException("Can not get all Teams", ex);
        } finally {
            close(connection);
        }
        return teams;
    }

    public List<User> getAllUsersForAdmin(String email) throws DBException {
        List<User> users;
        Connection connection = null;

        try {
            connection = getConnection();
            Administrator administrator = searchAdminByEmail(email);
            users = UserManager.getAllUsersForAdmin(connection, administrator);
        } catch (SQLException ex) {
            logger.error("Can not get all Users", ex);
            throw new DBException("Can not get all Users", ex);
        } finally {
            close(connection);
        }
        return users;
    }

    public List<Team> getAllTeamsForUser(String email) throws DBException {
        List<Team> teams;
        Connection connection = null;

        try {
            connection = getConnection();
            User user = searchUserByEmail(email);
            teams = TeamManager.getAllTeamsForUser(connection, user);
        } catch (SQLException ex) {
            logger.error("Can not get all Teams for user", ex);
            throw new DBException("Can not get all Teams for user", ex);
        } finally {
            close(connection);
        }
        return teams;
    }

    public List<Request> getAllRequestsForUser(String email) throws DBException {
        List<Request> requests;
        Connection connection = null;

        try {
            connection = getConnection();
            User user = searchUserByEmail(email);
            requests = RequestManager.getAllRequestsForUser(connection, user);
        } catch (SQLException ex) {
            logger.error("Can not get all Requests for user", ex);
            throw new DBException("Can not get all Requests for user", ex);
        } finally {
            close(connection);
        }
        return requests;
    }

    public User searchUserByName(String name) throws DBException {
        User user = new User();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(SQL_FIND_USER_BY_NAME);

            pstmt.setString(1, "%" + escapeForLike(name) + "%");
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                user = mapUser(resultSet);
            }
        } catch (SQLException ex) {
            logger.error("Can not find User", ex);
            throw new DBException("Can not find User", ex);
        } finally {
            close(resultSet);
            close(pstmt);
            close(connection);
        }
        return user;
    }
}
