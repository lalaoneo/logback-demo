package com.lori.logbackdemo.utils;

import com.lori.logbackdemo.thread.LogThread;
import org.slf4j.*;

public class Log {

    private static Logger logger = LoggerFactory.getLogger(Log.class);

    public static void log() {
        /**
         * use mdc
         */
        MDC.put("lori_id","i am lori");
        logger.info("first slf4j demo");

        new Thread(new LogThread()).start();

        /**
         * use marker
         */
        String firstMarker = "FIRST_MARKER";
        Marker marker = MarkerFactory.getMarker(firstMarker);

        logger.info(marker,"second slf4j demo");
    }
}
