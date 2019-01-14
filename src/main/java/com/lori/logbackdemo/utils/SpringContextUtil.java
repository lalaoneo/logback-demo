package com.lori.logbackdemo.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext context ;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static void getActiveProfile() {
        String[] strings = context.getEnvironment().getActiveProfiles();
        for (String str : strings){
            System.out.println(str);
        }
    }
}
