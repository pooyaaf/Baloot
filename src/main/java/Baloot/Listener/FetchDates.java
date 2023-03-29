package Baloot.Listener;

import Baloot.Context.ContextManager;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletContextEvent;

public class FetchDates implements ServletContextListener {

    public void contextInitialized(ServletContextEvent arg0) {
        ContextManager.getInstance().initialize();
    }

}
