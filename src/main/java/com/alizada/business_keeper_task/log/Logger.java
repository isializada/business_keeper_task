package com.alizada.business_keeper_task.log;

public class Logger {
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Logger.class);
    public static org.apache.log4j.Logger getLogger() {
        return logger;
    }
}
