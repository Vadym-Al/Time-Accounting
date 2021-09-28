package com.my.time.accounting.database;

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
/**
 * Class that realise Singleton pattern and works with data base
 *
 * @author Vadym Aldyk
 * @version 1.0
 */
public class DBManager {
    private final Logger logger = LogManager.getLogger(DBManager.class);
    private static DBManager dbManager;
    private final DataSource dataSource;

    /**
     * private constructor that initialize datasource
     * @see DBManager#DBManager()
     */
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

    /**
     * Method of getting db manager instance
     * @return synchronized manager instance
     */
    public static synchronized DBManager getInstance() {
        if (dbManager == null) {
            return new DBManager();
        }
        return dbManager;
    }

    /**
     * Method getting connection from datasource
     * @return connection
     * @throws SQLException - possible exception
     */
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
            logger.error("Can not find User by email", ex);
            throw new DBException("Can not find USer by email", ex);
        } finally {
            close(connection);
        }
        return user;
    }

    public User searchUserById(long id) throws DBException {
        User user;
        Connection connection = null;
        try {
            connection = getConnection();
            user = UserManager.searchUserById(connection, id);
        } catch (SQLException ex) {
            logger.error("Can not find User by id", ex);
            throw new DBException("Can not find User by id", ex);
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
            logger.error("Can not find User by name", ex);
            throw new DBException("Can not find User by name", ex);
        } finally {
            close(connection);
        }
        return team;
    }

    public Activity searchActivityByName(String name) throws DBException {
        Activity activity;
        Connection connection = null;
        try {
            connection = getConnection();
            activity = ActivityManager.searchActivityByName(connection, name);
        } catch (SQLException ex) {
            logger.error("Can not find Activity by name", ex);
            throw new DBException("Can not find Activity by name", ex);
        } finally {
            close(connection);
        }
        return activity;
    }

    public Activity searchActivityById(long id) throws DBException {
        Activity activity;
        Connection connection = null;
        try {
            connection = getConnection();
            activity = ActivityManager.searchActivityById(connection, id);
        } catch (SQLException ex) {
            logger.error("Can not find Activity by id", ex);
            throw new DBException("Can not find Activity by io", ex);
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

    /**
     * Transaction of inserting new task for user
     * @param task - task
     * @param name - name of user
     * @throws DBException - possible exception
     */
    public void insertTaskForUser(Task task, String name) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            TaskManager.insertTask(connection, task);
            User user = searchUserByName(name);
            TaskManager.setTask(connection, task, user);
            connection.commit();
        } catch (SQLException ex) {
            logger.error("Can not insert Task", ex);
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException e) {
                logger.error("Can not roll back connection", e);
            }
            throw new DBException("Can not insert Task", ex);
        } finally {
            close(connection);
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

    public List<Task> getAllTasksForUser(String email) throws DBException {
        List<Task> tasks = new ArrayList<>();
        Connection connection = null;
        try {
            connection = getConnection();
            User user = searchUserByEmail(email);
            for (long id : getListOfTasks(user)){
                tasks.add(getTasksById(id));
            }
        } catch (SQLException ex) {
            logger.error("Can not get all Tasks for user", ex);
            throw new DBException("Can not get all Tasks for user", ex);
        } finally {
            close(connection);
        }
        return tasks;
    }
    
    public Task getTasksById(long id) throws DBException {
        Task task;
        Connection connection = null;
        try {
            connection = getConnection();
            task = TaskManager.getTasksForUserById(connection, id);
        } catch (SQLException ex) {
            logger.error("Can not get all Tasks by id", ex);
            throw new DBException("Can not get all Tasks by id", ex);
        } finally {
            close(connection);
        }
        return task;
    }
    
    public List<Long> getListOfTasks(User user) throws DBException {
        List<Long> list;
        Connection connection = null;
        try {
            connection = getConnection();
            list = TaskManager.getListOfTasks(connection, user.getUserId());
        } catch (SQLException ex) {
            logger.error("Can not get list of tasks", ex);
            throw new DBException("Can not get list of tasks", ex);
        } finally {
            close(connection);
        }
        return list;
    }
    public User searchUserByName(String name) throws DBException {
        User user;
        Connection connection = null;
        try {
            connection = getConnection();
            user = UserManager.searchUserByName(connection, name);
        } catch (SQLException ex) {
            logger.error("Can not find User", ex);
            throw new DBException("Can not find User", ex);
        } finally {
            close(connection);
        }
        return user;
    }

    public Administrator searchAdminById(long id) throws DBException {
        Administrator administrator;
        Connection connection = null;
        try {
            connection = getConnection();
            administrator = AdminManager.searchAdminById(connection, id);
        } catch (SQLException ex) {
            logger.error("Can not find Admin by id", ex);
            throw new DBException("Can not find Admin by id", ex);
        } finally {
            close(connection);
        }
        return administrator;
    }

    public Request searchRequestById(long id) throws DBException {
        Request request;
        Connection connection = null;
        try {
            connection = getConnection();
            request = RequestManager.searchRequestById(connection, id);
        } catch (SQLException ex) {
            logger.error("Can not get Requests by id", ex);
            throw new DBException("Can not get Requests by id", ex);
        } finally {
            close(connection);
        }
        return request;
    }

    public void deleteActivity(long id) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            ActivityManager.deleteActivity(connection, id);
            connection.commit();
        } catch (SQLException ex) {
            logger.error("Can not delete Activity", ex);
            throw new DBException("Can not delete Activity", ex);
        } finally {
            close(connection);
        }
    }

    public void deleteRequest(long id) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            RequestManager.deleteRequest(connection, id);
            connection.commit();
        } catch (SQLException ex) {
            logger.error("Can not delete Request", ex);
            throw new DBException("Can not delete Request", ex);
        } finally {
            close(connection);
        }
    }

    /**
     * Transaction that deleats all that connects with task
     * @param id - task id
     * @throws DBException - possible exception
     */
    public void deleteTask(long id) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            TaskManager.deleteUserHasTask(connection, id);
            TaskManager.deleteTask(connection, id);
            connection.commit();
        } catch (SQLException ex) {
            logger.error("Can not delete Task", ex);
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException e) {
                logger.error("Can not roll back", e);
            }
            throw new DBException("Can not delete Task", ex);
        } finally {
            close(connection);
        }
    }

    /**
     * Transaction that deleats all that connects with user
     * @param id - user id
     * @throws DBException - possible exception
     */
    public void deleteUser(long id) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            List<Long> list = TaskManager.getListOfTasks(connection, id);
            for (long i: list){
                TaskManager.deleteTask(connection, i);
            }
            UserManager.deleteUserHasTask(connection, id);
            RequestManager.deleteRequestFoUser(connection, id);
            UserManager.deleteUser(connection, id);
            connection.commit();
        } catch (SQLException ex) {
            logger.error("Can not delete User", ex);
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException e) {
                logger.error("Can not roll back", e);
            }
            throw new DBException("Can not delete User", ex);
        } finally {
            close(connection);
        }
    }

    /**
     * Transaction that deleats all that connects with team
     * @param id - team id
     * @throws DBException - possible exception
     */
    public void deleteTeam(long id) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            List<User> list = UserManager.searchUserByTeamId(connection, id);
            for (User user: list){
                deleteUser(user.getUserId());
            }
            TeamManager.deleteTeam(connection, id);
            connection.commit();
        } catch (SQLException ex) {
            logger.error("Can not delete Team", ex);
            try {
                assert connection != null;
                connection.rollback();
            } catch (SQLException e) {
                logger.error("Can not roll back", e);
            }
            throw new DBException("Can not delete Team", ex);
        } finally {
            close(connection);
        }
    }

    public void updateTask(long id, Time time) throws DBException {
        Connection connection = null;
        try {
            connection = getConnection();
            TaskManager.updateTask(connection, id, time);
            connection.commit();
        } catch (SQLException ex) {
            logger.error("Can not update Task", ex);
            throw new DBException("Can not update Task", ex);
        } finally {
            close(connection);
        }
    }
}
