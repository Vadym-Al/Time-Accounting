package com.my.time.accounting.database;

public class SQLConstance {
    private SQLConstance(){}

    public static final String SQL_NAME = "name";
    public static final String SQL_LAST_NAME = "last_name";
    public static final String SQL_PASSWORD = "password";
    public static final String SQL_EMAIL = "email";
    public static final String SQL_COMPANY = "company";
    public static final String SQL_ABOUT = "about";
    public static final String SQL_PHONE_NUMBER = "phone_number";
    public static final String SQL_CREATE_TIME = "create_time";
    public static final String SQL_ADMINISTRATOR_ID = "administrator_id";
    public static final String SQL_ACTIVITY_ID = "activity_id";
    public static final String SQL_TEAM_ID = "team_id";
    public static final String SQL_ERROR = "error";

    //--------------------------------------------------------

    public static final String SQL_FIND_ADMINISTRATOR_BY_EMAIL = "SELECT * FROM administrator WHERE email LIKE ? ESCAPE '!'";
    public static final String SQL_FIND_USER_BY_EMAIL = "SELECT * FROM user WHERE email LIKE ? ESCAPE '!'";
    public static final String SQL_FIND_TEAM_BY_NAME = "SELECT * FROM team WHERE name LIKE ? ESCAPE '!'";
    public static final String SQL_FIND_ACTIVITY_BY_NAME = "SELECT * FROM activity_type WHERE name LIKE ? ESCAPE '!'";
    public static final String SQL_FIND_USER_BY_NAME = "SELECT * FROM user WHERE name LIKE ?";

    //--------------------------------------------------------

    public static final String SQL_GET_ACTIVITIES_BY_ADMIN = "SELECT * FROM activity_type WHERE administrator_id LIKE ?";
    public static final String SQL_GET_REQUESTS_BY_ADMIN = "SELECT * FROM request WHERE administrator_id LIKE ?";
    public static final String SQL_GET_TEAMS_BY_ADMIN = "SELECT * FROM team WHERE administrator_id LIKE ?";
    public static final String SQL_GET_USERS_BY_ADMIN = "SELECT * FROM user WHERE administrator_id LIKE ?";
    public static final String SQL_GET_TASKS_BY_ADMIN = "SELECT * FROM task WHERE administrator_id LIKE ?";

    public static final String SQL_GET_TEAMS_BY_USER = "SELECT * FROM team WHERE team_id LIKE ?";
    public static final String SQL_GET_REQUESTS_BY_USER = "SELECT * FROM request WHERE user_id LIKE ?";

    public static final String SQL_GET_TASKS_BY_TASK_ID = "SELECT * FROM user_has_task WHERE user_id LIKE ?";
    public static final String SQL_GET_TASKS_BY_USER_HAS_TASK = "SELECT * FROM task WHERE task_id LIKE ?";
    /*
    public static final String SQL_FIND_TEAM_BY_NAME = "SELECT * FROM teams WHERE name LIKE ? ESCAPE '!'";
    public static final String SQL_FIND_TEAM_BY_ID = "SELECT * FROM teams WHERE id=?";
    public static final String SQL_FIND_ALL_TEAM_BY_USER = "SELECT * FROM users_teams WHERE user_id=?";*/

    //--------------------------------------------------------

    public static final String SQL_ADD_NEW_ADMINISTRATOR = "INSERT INTO administrator (name, last_name, password, email, phone_number, company) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String SQL_ADD_NEW_USER = "INSERT INTO user (name, last_name, password, email, phone_number, company, team_id, administrator_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_ADD_TASK_TO_USER = "INSERT INTO user_has_task (user_id, task_id) VALUES (?, ?)";
    public static final String SQL_ADD_NEW_TASK = "INSERT INTO task (name, deadline, about, activity_type_id, administrator_id) VALUES (?, ?, ?, ?, ?)";
    public static final String SQL_ADD_NEW_TEAM = "INSERT INTO team (name, company, description, administrator_id) VALUES (?, ?, ?, ?)";
    public static final String SQL_ADD_NEW_REQUEST = "INSERT INTO users_teams (user_id, team_id) VALUES (?, ?)";
    public static final String SQL_ADD_NEW_ACTIVITY_TYPE = "INSERT INTO activity_type (name, about, administrator_id) VALUES (?, ?, ?)";

    //--------------------------------------------------------

    public static final String SQL_FIND_ALL_USERS = "SELECT * FROM user";
    public static final String SQL_FIND_ALL_ADMINISTRATORS = "SELECT * FROM administrator";
    public static final String SQL_FIND_ALL_TASKS = "SELECT * FROM task";
    public static final String SQL_FIND_ALL_TEAMS = "SELECT * FROM team";
    public static final String SQL_FIND_ALL_REQUEST = "SELECT * FROM request";
    public static final String SQL_FIND_ALL_ACTIVITY_TYPE = "SELECT * FROM activity_type";

    //--------------------------------------------------------

    public static final String SQL_DELETE_TEAM = "DELETE FROM teams WHERE name=?";

}
