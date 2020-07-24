package com.maven.mqtt.asyncmqtt;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class MqttSubscriber implements MqttCallback{
	private String brokerURL = "";
	private String clientId = "AsyncTest";
	private MemoryPersistence persistence = new MemoryPersistence();
	
	MqttAsyncClient client = null;
	MqttConnectOptions options = null;
	
	//private int eld = 0;
	
	private SqlSession sqlSession = null;
	
	private static final String ns = "mqttSubscriber.";
	
	public MqttSubscriber() {
		String resource = "com/maven/mqtt/sql/mybatis-config.xml";
		try {
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = 
					new SqlSessionFactoryBuilder().build(inputStream);
			
			sqlSession = sqlSessionFactory.openSession(true);
		}catch(IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public void mqttSubscribe(String broker, int port, String topic, int elderly) {
		this.brokerURL = "tcp://"+broker+":"+port;
		try {
			//this.eld = elderly;
			client = new MqttAsyncClient(brokerURL, clientId, persistence);
			options = new MqttConnectOptions();
			options.setCleanSession(true);
			
			//IMqttToken token = 
			client.connect(options);
			//token.waitForCompletion();
			Thread.sleep(1000);
			client.setCallback(new MqttSubscriber());
			client.subscribe(topic, 2);
		}catch(Exception me){
			if(me instanceof MqttException) {
				System.out.println("reason " + ((MqttException) me).getReasonCode());
			}
			System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
		}
	}


	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		System.out.println("connection lost");
		cause.printStackTrace();
	}


	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("topic: " + topic);
        System.out.println("message: " + new String(message.getPayload()));
        
        insertData(topic, message);
		
	}


	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		System.out.println("deliveryComplete");
		
	}
	private void insertData(String topic, MqttMessage message) {
		try { 
			String[] tp = topic.split("/");
			for(String i: tp) {
				System.out.println(i);
			}
			if(tp[2].equals("humid")||tp[2].equals("temp")) {
				Map<String, Object> obj = new HashMap<String, Object>();
				float data = Float.parseFloat(message.toString()); 
				obj.put("elderly", Integer.parseInt(tp[1]));
				obj.put(tp[2], data);
				sqlSession.insert(ns+"log", obj);
			}else
				alertToApp(tp[2]);
			
			
		  }catch(NumberFormatException e) { 
			  //"home/vid"
			  //인코딩 되어서 오는 파일 디코딩해서 저장하든 뭐 하든 알아서
			  /*https://addio3305.tistory.com/83 참고해서 수정하기
		        if(topic.equals("home/vid")) {
		        Decoder decoder = Base64.getDecoder();
		        byte[] decodesBytes = decoder.decode(message.getPayload());
		        System.out.println("testestest"+decodesBytes.toString());
		        
		        try {
		        	File outFile = new File("./test");
		        	FileOutputStream fileOutputStream = new FileOutputStream(outFile);
		        	fileOutputStream.write(decodesBytes);
		        	fileOutputStream.close();
		        	
		        }catch(Throwable e) {
		        	e.printStackTrace(System.out);
		        }
		        }*/
		  }catch(Exception e) {
			  //home vid?? 왜?
		  }
	}
	private void alertToApp(String tp) {
		//tp : gas, alone
		/*알림 시스템. 어플로 알림 전송. 
		*1. humidity, temperature 이상범위
		*2. 밤 중 움직임 감지 영상
		*3. 3일 이상 움직임이 없을 시
		*4. 이상 가스 검출 시
		*/
	}
	public List<Map<String, Object>> getIoTList() {
		return sqlSession.selectList("selectDevices");
	}
}
	