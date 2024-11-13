package com.my.time.accounting.database;

import static com.my.time.accounting.database.managers.Utils.*;

import com.my.time.accounting.database.controller.DatabaseGlobalController;
import com.my.time.accounting.database.managers.*;
import com.my.time.accounting.entity.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

public class DBManager {
    private final Logger logger = LogManager.getLogger(DBManager.class);
    private final DBConnection dbConnection;
    private final DatabaseGlobalController<Activity, Administrator> activityController;
    private final DatabaseGlobalController<Administrator, Task> administratorController;
    private final DatabaseGlobalController<Request, User> requestController;
    private final DatabaseGlobalController<Task, User> taskController;
    private final DatabaseGlobalController<Team, User> teamController;
    private final DatabaseGlobalController<User, Administrator> userController;
    
    public DBManager() {
        dbConnection = DBConnection.getInstance();
        activityController = new DatabaseGlobalController<>(new ActivityManager());
        administratorController = new DatabaseGlobalController<>(new AdminManager());
        requestController = new DatabaseGlobalController<>(new RequestManager());
        taskController = new DatabaseGlobalController<>(new TaskManager());
        teamController = new DatabaseGlobalController<>(new TeamManager());
        userController = new DatabaseGlobalController<>(new UserManager());
    }

    public Administrator searchAdminByEmail(String email) throws DBException {
        return administratorController.searchForEntityByName(email);
    }

    public User searchUserByEmail(String email) throws DBException {
        User user;
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
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
        return userController.searchForEntityById(id);
    }

    public Team searchTeamByName(String name) throws DBException {
        return teamController.searchForEntityByName(name);
    }

    public Activity searchActivityByName(String name) throws DBException {
        return activityController.searchForEntityByName(name);
    }

    public Activity searchActivityById(long id) throws DBException {
        return activityController.searchForEntityById(id);
    }

    public void insertAdministrator(Administrator administrator) throws DBException {
        administratorController.insertEntity(administrator);
    }

    public void insertActivity(Activity activity) throws DBException {
        activityController.insertEntity(activity);
    }

    public void insertRequest(Request request) throws DBException {
        requestController.insertEntity(request);
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
            connection = dbConnection.getConnection();
            taskController.insertEntity(task);
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
        teamController.insertEntity(team);
    }

    public void insertUser(User user) throws DBException {
        userController.insertEntity(user);
    }

    public List<Activity> getAllActivitiesForAdmin(String email) throws DBException {
        return activityController.getAllActivitiesByRequest(searchAdminByEmail(email));
    }

    public List<Request> getAllRequestsForAdmin(String email) throws DBException {
        List<Request> requests;
        Connection connection = null;

        try {
            connection = dbConnection.getConnection();
            requests = RequestManager.getAllRequestsForAdmin(connection, searchAdminByEmail(email));
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
            connection = dbConnection.getConnection();
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
        return userController.getAllActivitiesByRequest(searchAdminByEmail(email));
    }

    public List<Team> getAllTeamsForUser(String email) throws DBException {
        return teamController.getAllActivitiesByRequest(searchUserByEmail(email));
    }

    public List<Request> getAllRequestsForUser(String email) throws DBException {
        return requestController.getAllActivitiesByRequest(searchUserByEmail(email));
    }

    public List<Task> getAllTasksForAdmin(String email) throws DBException {
        List<Task> tasks;
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
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
        return taskController.getAllActivitiesByRequest(searchUserByEmail(email));
    }

    public User searchUserByName(String name) throws DBException {
        return userController.searchForEntityByName(name);
    }

    public Administrator searchAdminById(long id) throws DBException {
        return administratorController.searchForEntityById(id);
    }

    public Request searchRequestById(long id) throws DBException {
        return requestController.searchForEntityById(id);
    }

    public void deleteActivity(long id) throws DBException {
        activityController.deleteEntity(id);
    }

    public void deleteRequest(long id) throws DBException {
        requestController.deleteEntity(id);
    }

    /**
     * Transaction that deleats all that connects with task
     * @param id - task id
     * @throws DBException - possible exception
     */
    public void deleteTask(long id) throws DBException {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            TaskManager.deleteUserHasTask(connection, id);
            taskController.deleteEntity(id);
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
            connection = dbConnection.getConnection();
            List<Long> list = TaskManager.getListOfTasks(connection, id);
            for (long i: list){
                taskController.deleteEntity(i);
            }
            UserManager.deleteUserHasTask(connection, id);
            RequestManager.deleteRequestFoUser(connection, id);
            userController.deleteEntity(id);
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
            connection = dbConnection.getConnection();
            List<User> list = UserManager.searchUserByTeamId(connection, id);
            for (User user: list){
                deleteUser(user.getUserId());
            }
            teamController.deleteEntity(id);
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
            connection = dbConnection.getConnection();
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
