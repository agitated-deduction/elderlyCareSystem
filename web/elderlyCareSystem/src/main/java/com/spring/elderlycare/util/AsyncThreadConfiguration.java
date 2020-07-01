package com.spring.elderlycare.util;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

//@Configuration
//@EnableAsync
public class AsyncThreadConfiguration {
	//@Bean
	public Executor asyncThreadExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(8);
		threadPoolTaskExecutor.setMaxPoolSize(8); 
		threadPoolTaskExecutor.setThreadNamePrefix("test-pool"); 
		return threadPoolTaskExecutor;

	}
}