package com.lori.logbackdemo.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class LogThread implements Runnable{

    Logger logger = LoggerFactory.getLogger(LogThread.class);

    @Override
    public void run() {
        MDC.put("lori_id","i am lori2");
        logger.info("log thread");
    }
}
