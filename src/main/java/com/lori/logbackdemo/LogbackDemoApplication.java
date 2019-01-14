package com.lori.logbackdemo;

import com.lori.logbackdemo.utils.Log;
import com.lori.logbackdemo.utils.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogbackDemoApplication {

	/**
	 * 需要指定-Dspring.profiles.active=showInConsole参数，
	 * logback配置文件使用了<springProfile
	 * @param args
	 */

	public static void main(String[] args) {
		SpringApplication.run(LogbackDemoApplication.class, args);

		SpringContextUtil.getActiveProfile();

		Log.log();
	}

}

