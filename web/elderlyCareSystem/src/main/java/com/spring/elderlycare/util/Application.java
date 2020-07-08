package com.spring.elderlycare.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

//@SpringBootApplication
//@Configuration /*@EnableAutoConfiguration*/ @ComponentScan 
public class Application {
	//@Autowired
	static
	Runnable MessageListener;
	
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[]args) {
		//SpringApplication.run(Application.class, args); 
		logger.info("===================main==================");
		TaskExecutor executor = new SimpleAsyncTaskExecutor();
		executor.execute(MessageListener);
	}
	
	 /*@Bean
	 public CommandLineRunner schedulingRunner(TaskExecutor executor) {
	     return new CommandLineRunner() {
	         public void run(String... args) throws Exception {
	             executor.execute(MessageListener);
	         }
	     };
	 }*/
}
