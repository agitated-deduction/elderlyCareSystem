package com.maven.mqtt.asyncmqtt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttService implements MqttCallbackExtended {
	private String brokerURL = "";
	private String clientId = "asyncMqtt_elderlyCare";
	private MemoryPersistence persistence = new MemoryPersistence();

	MqttAsyncClient client = null;
	MqttConnectOptions options = null;
	// ClientComms comms = null;
	// private int eld = 0;

	MqttDAO dao = new MqttDAO();
	// private SqlSession sqlSession = null;

	// private static final String ns = "mqttSubscriber.";

	
	  
	 
	public MqttService() {
		/*
		 * String resource = "com/maven/mqtt/sql/mybatis-config.xml"; try { InputStream
		 * inputStream = Resources.getResourceAsStream(resource); SqlSessionFactory
		 * sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		 * 
		 * sqlSession = sqlSessionFactory.openSession(true); }catch(IOException e) {
		 * e.printStackTrace(); return; }
		 */
	}

	public void mqttSubscribe(String broker, int port, String topic) {
		this.brokerURL = "tcp://" + broker + ":" + port;
		try {
			// this.eld = elderly;
			// this.topic = topic;
			client = new MqttAsyncClient(brokerURL, clientId, persistence);
			options = new MqttConnectOptions();
			options.setCleanSession(true);
			//options.setAutomaticReconnect(true);

			// IMqttToken token =
			client.connect(options);
			// token.waitForCompletion();
			Thread.sleep(1000);
			client.setCallback(new MqttService());
			
			client.subscribe(topic, 2);
		} catch (Exception me) {
			if (me instanceof MqttException) {
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
		System.out.println("connection lost");
		
		// cause.printStackTrace();

	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		
		new Thread(
				()->{
					insertData(topic, message);
				}
			).start();
		//insertData(topic, message);

	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		System.out.println("deliveryComplete");

	}

	private void insertData(String topic, MqttMessage message) {
		System.out.println("topic "+topic);
		System.out.println("message "+message);
		try {
			String[] tp = topic.split("/");

			if (tp[2].equals("humid") || tp[2].equals("temp")) {
				Map<String, Object> obj = new HashMap<String, Object>();
				float data = Float.parseFloat(message.toString());
				obj.put("elderly", Integer.parseInt(tp[1]));
				obj.put(tp[2], data);
				// sqlSession.insert(ns+"log", obj);
				dao.insert(obj);
			}else if(tp[2].equals("ht")){
				Map<String, Object> obj = new HashMap<String, Object>();
				String datas[] = message.toString().split("/");
				obj.put("elderly", Integer.parseInt(tp[1]));
				obj.put("humid",Float.parseFloat(datas[0]));
				obj.put("temp",Float.parseFloat(datas[1]));
				dao.insert(obj);
			}else {
				myAlert(tp[2]);
			}

		} catch (NumberFormatException e) {
			System.out.println("cat1"+topic);
			// "home/vid"
			// 인코딩 되어서 오는 파일 디코딩해서 저장하든 뭐 하든 알아서
			/*
			 * https://addio3305.tistory.com/83 참고해서 수정하기 if(topic.equals("home/vid")) {
			 * Decoder decoder = Base64.getDecoder(); byte[] decodesBytes =
			 * decoder.decode(message.getPayload());
			 * System.out.println("testestest"+decodesBytes.toString());
			 * 
			 * try { File outFile = new File("./test"); FileOutputStream fileOutputStream =
			 * new FileOutputStream(outFile); fileOutputStream.write(decodesBytes);
			 * fileOutputStream.close();
			 * 
			 * }catch(Throwable e) { e.printStackTrace(System.out); } }
			 */
			// e.printStackTrace();
		} catch (Exception e) {
			System.out.println("catch "+topic);
			// home vid?? 왜?
			//일단 버림.
		}
	}

	private void myAlert(String tp) {
		System.out.println("alert "+tp);
		// tp : gas, alone
		/*
		 * 알림 시스템. 어플로 알림 전송. 1. humidity, temperature 이상범위 2. 3일 이상
		 * 움직임이 없을 시 4. 이상 가스 검출 시
		 */
	}

	public List<Map<String, Object>> getIoTList() {
		return dao.getDevList();
	}

	public void connectComplete(boolean reconnect, String serverURI) {
		System.out.println("connectComplete");
		/*
		 * try { client.subscribe("home/#", 2);
		 * 
		 * }catch(MqttException e) {
		 * 
		 * }
		 */
	}

}
