package com.my.time.accounting.logic;

import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;

@WebListener
public class MyListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        String homeDir=event.getServletContext().getRealPath("/");
        File propertiesFile=new File(homeDir,"WEB-INF/log4j.properties");
        PropertyConfigurator.configure(propertiesFile.toString());
    }

    @Override
    public void contextDestroyed(ServletContextEvent event){

    }
}
