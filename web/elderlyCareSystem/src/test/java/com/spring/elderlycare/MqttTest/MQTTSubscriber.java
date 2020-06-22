package com.spring.elderlycare.MqttTest;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.function.Consumer;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/*https://www.monirthought.com/2017/11/eclipse-paho-java-client-mqtt-client.html*/
@Component
public class MQTTSubscriber extends MQTTConfig implements MqttCallback, MQTTSubscriberBase {

 private String brokerUrl = null;
 final private String colon = ":";
 final private String clientId = "demoClient2";

 private MqttClient mqttClient = null;
 private MqttConnectOptions connectionOptions = null;
 private MemoryPersistence persistence = null;
 
 //https://lts0606.tistory.com/300
 private Consumer<HashMap<Object, Object>> FNC = null;  //메시지 도착 후 응답하는 함수 
 private Consumer<HashMap<Object, Object>> FNC2 = null; //커넥션이 끊긴 후 응답하는 함수
 private Consumer<HashMap<Object, Object>> FNC3 = null; //전송이 완료된 이후 응답하는 함수.
 
 
 private static final Logger logger = LoggerFactory.getLogger(MQTTSubscriber.class);

 public MQTTSubscriber(Consumer<HashMap<Object, Object>> fnc) {
	 logger.info("===========init===========");
  this.config();
  this.FNC = fnc;
 }

 /*
  * (non-Javadoc)
  * 
  * @see org.eclipse.paho.client.mqttv3.MqttCallback#connectionLost(java.lang.
  * Throwable)
  */
 
 /**
  * 커넥션이 끊어진 이후의 콜백행위를 등록합니다.<br>
  * 해쉬맵 형태의 결과에 키는 result, 값은 Throwable 객체를 반환 합니다. 
  * **/
 public void initConnectionLost (Consumer<HashMap<Object, Object>> fnc){
	 logger.info("===========initConnectionLost===========");
     FNC2 = fnc;
 }
	

 /**
  * 커넥션이 끊어진 이후의 콜백행위를 등록합니다.<br>
  * 해쉬맵 형태의 결과에 키는 result, 값은 IMqttDeliveryToken 객체를 반환 합니다. 
  * **/
 public void initDeliveryComplete (Consumer<HashMap<Object, Object>> fnc){
	 logger.info("===========initDeliveryComplete===========");
     FNC3 = fnc;
 }
 
 
 @Override
 public void connectionLost(Throwable cause) {
  logger.info("Connection Lost");
 }

 /*
  * (non-Javadoc)
  * 
  * @see
  * org.eclipse.paho.client.mqttv3.MqttCallback#messageArrived(java.lang.String,
  * org.eclipse.paho.client.mqttv3.MqttMessage)
  */
 @Override
 public void messageArrived(String topic, MqttMessage message) throws Exception {
  // Called when a message arrives from the server that matches any
  // subscription made by the client
  String time = new Timestamp(System.currentTimeMillis()).toString();
  System.out.println();
  System.out.println("***********************************************************************");
  System.out.println("Message Arrived at Time: " + time + "  Topic: " + topic + "  Message: "
    + new String(message.getPayload()));
  System.out.println("***********************************************************************");
  System.out.println();
 }

 /*
  * (non-Javadoc)
  * 
  * @see
  * org.eclipse.paho.client.mqttv3.MqttCallback#deliveryComplete(org.eclipse.paho
  * .client.mqttv3.IMqttDeliveryToken)
  */
 @Override
 public void deliveryComplete(IMqttDeliveryToken token) {
  // Leave it blank for subscriber

 }

 /*
  * (non-Javadoc)
  * 
  * @see
  * com.monirthought.mqtt.subscriber.MQTTSubscriberBase#subscribeMessage(java.
  * lang.String)
  */
 @Override
 public void subscribeMessage(String topic) {
	 logger.info("===========subscribeMessage===========");
	 System.out.println("test");
  try {
   this.mqttClient.subscribe(topic, this.qos);
  } catch (MqttException me) {
   me.printStackTrace();
  }
 }

 /*
  * (non-Javadoc)
  * 
  * @see com.monirthought.mqtt.subscriber.MQTTSubscriberBase#disconnect()
  */
 public void disconnect() {
  try {
   this.mqttClient.disconnect();
  } catch (MqttException me) {
   logger.error("ERROR", me);
  }
 }

 /*
  * (non-Javadoc)
  * 
  * @see com.monirthought.config.MQTTConfig#config(java.lang.String,
  * java.lang.Integer, java.lang.Boolean, java.lang.Boolean)
  */
 @Override
 protected void config(String broker, Integer port, Boolean ssl, Boolean withUserNamePass) {
	 logger.info("===========config===========");
  String protocal = this.TCP;
  if (true == ssl) {
   protocal = this.SSL;
  }

  this.brokerUrl = protocal + this.broker + colon + port;
  this.persistence = new MemoryPersistence();
  this.connectionOptions = new MqttConnectOptions();

  try {
   this.mqttClient = new MqttClient(brokerUrl, clientId, persistence);
   this.connectionOptions.setCleanSession(true);
   if (true == withUserNamePass) {
    if (password != null) {
     this.connectionOptions.setPassword(this.password.toCharArray());
    }
    if (userName != null) {
     this.connectionOptions.setUserName(this.userName);
    }
   }
   this.mqttClient.connect(this.connectionOptions);
   this.mqttClient.setCallback(this);
  } catch (MqttException me) {
   me.printStackTrace();
  }

 }

 /*
  * (non-Javadoc)
  * 
  * @see com.monirthought.config.MQTTConfig#config()
  */
 @Override
 protected void config() {
  this.brokerUrl = this.TCP + this.broker + colon + this.port;
  this.persistence = new MemoryPersistence();
  this.connectionOptions = new MqttConnectOptions();
  try {
   this.mqttClient = new MqttClient(brokerUrl, clientId, persistence);
   this.connectionOptions.setCleanSession(true);
   this.mqttClient.connect(this.connectionOptions);
   this.mqttClient.setCallback(this);
  } catch (MqttException me) {
   me.printStackTrace();
  }

 }

}