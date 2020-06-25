package com.spring.elderlycare.util;

import java.util.concurrent.Executor;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AppConfig implements AsyncConfigurer{
	
	@Resource(name= "executor")
	private SimpleAsyncTaskExecutor executor;
	
	@Bean(name = "executor")
	public Executor taskExecutor() {
		return new SimpleAsyncTaskExecutor();
	}
}
