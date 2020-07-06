package com.spring.elderlycare.util;

//import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
//@Async
public class MqttStarter {
	public void mqttstart() {
		try {
			Runtime runtime = Runtime.getRuntime();
			//Process p = runtime.exec("java -jar D:\\1elderlyproject\\web\\mqttTest.jar");// DB에 값이 바로 들어감.
			/*Process p = */runtime.exec("java -jar D:\\1elderlyproject\\web\\mqttTestGetList.jar"); //서버 종료 시 DB에 값이 들어감.
			
		}catch(Exception e ) {
			e.printStackTrace();
		}
	}
}
