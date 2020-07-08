package com.spring.elderlycare.util;

import java.io.IOException;

//import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
//@Async
public class MqttStarter {
	//리스트 불러와서 사용
	private final String mqttProcess = "D:\\1elderlyproject\\web\\mqttstarternoreconnect.jar";
	//로컬호스트 하나
	//private final String mqttProcess = "D:\\1elderlyproject\\web\\mqttTest.jar";
	public void mqttstart() {
		try {
			Runtime runtime = Runtime.getRuntime();
			//Process p = runtime.exec("java -jar D:\\1elderlyproject\\web\\mqttTest.jar");// DB에 값이 바로 들어감.
			/*Process p = */runtime.exec("java -jar "+mqttProcess); //서버 종료 시 DB에 값이 들어감.
			
		}catch(Exception e ) {
			e.printStackTrace();
		}
	}
	public void mqttdestroy() {
		try {
			Runtime.getRuntime().exec("taskkill /F /IM java.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
