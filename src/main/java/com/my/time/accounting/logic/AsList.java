package com.my.time.accounting.logic;

import com.my.time.accounting.entity.Administrator;
import com.my.time.accounting.entity.User;

import java.util.ArrayList;
import java.util.List;

public class AsList {
    public static List<Administrator> adminAsList(Administrator administrator){
        List<Administrator> administrators = new ArrayList<>();
        administrators.add(administrator);
        return administrators;
    }

    public static List<User> userAsList(User user){
        List<User> users = new ArrayList<>();
        users.add(user);
        return users;
    }
}
