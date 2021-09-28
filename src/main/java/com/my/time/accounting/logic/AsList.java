package com.my.time.accounting.logic;

import com.my.time.accounting.database.managers.ActivityManager;
import com.my.time.accounting.entity.Administrator;
import com.my.time.accounting.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that packed single entities in list
 *
 * @author Vadym Aldyk
 * @version 1.0
 */
public class AsList {
    /**
     * private constructor
     * @see AsList#AsList()
     */
    private AsList(){}
    /**
     * Method that pack single administrator in list
     * @param administrator - entity of database
     * @return list of administrator
     */
    public static List<Administrator> adminAsList(Administrator administrator){
        List<Administrator> administrators = new ArrayList<>();
        administrators.add(administrator);
        return administrators;
    }

    /**
     * Method that pack single user in list
     * @param user - entity of database
     * @return list of user
     */
    public static List<User> userAsList(User user){
        List<User> users = new ArrayList<>();
        users.add(user);
        return users;
    }
}
