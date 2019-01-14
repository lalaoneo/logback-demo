package com.lori.logbackdemo.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import static org.springframework.boot.context.logging.LoggingApplicationListener.CONFIG_PROPERTY;
import static org.springframework.core.io.ResourceLoader.CLASSPATH_URL_PREFIX;

public class LoggingListener implements SpringApplicationRunListener, Ordered {

    private static final String DEFAULT_LOGBACK_LOCATION = CLASSPATH_URL_PREFIX + "logback-spring.xml";

    private final SpringApplication application;

    private final String[] args;


    public LoggingListener(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }

    @Override
    public void starting() {

    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        if (!environment.containsProperty(CONFIG_PROPERTY)){
            System.setProperty(CONFIG_PROPERTY,DEFAULT_LOGBACK_LOCATION);
        }
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {

    }

    @Override
    public void started(ConfigurableApplicationContext context) {

    }

    @Override
    public void running(ConfigurableApplicationContext context) {

    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {

    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
