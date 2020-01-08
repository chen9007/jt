package com.jt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude=DataSourceAutoConfiguration.class)
//不自动加载数据源
public class SpringBootRun {
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBootRun.class,args);
	}
}
