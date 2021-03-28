package com.terraboxstudios.publicsms.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Supplier;
import java.util.logging.Level;

public class Logger {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger("PublicSMS");
    private static final Supplier<String> formattedTime = () -> {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String formatted = simpleDateFormat.format(new Date());
        return "[" + formatted + "] ";
    };

    public static void debug(String message) {
        logger.log(Level.INFO, formattedTime.get() + message);
    }

    public static void warn(String message) {
        logger.log(Level.WARNING, formattedTime.get() + message);
    }

    public static void error(String message) {
        logger.log(Level.SEVERE, formattedTime.get() + message);
    }

}
