package com.my.time.accounting.logic;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Class implements ServletContextListener
 *
 * @author Vadym Aldyk
 * @version 1.0
 */
@WebListener
public class MyListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext ctx = event.getServletContext();
        String path = ctx.getRealPath("/WEV-INF/log4j.log");
        System.setProperty("logFile", path);

        final Logger logger = LogManager.getLogger(MyListener.class);
        logger.debug("path = "+ path);
    }
}
