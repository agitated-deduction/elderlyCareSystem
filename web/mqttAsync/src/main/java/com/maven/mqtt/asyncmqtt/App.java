package com.maven.mqtt.asyncmqtt;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
		
    	MqttSubscriber client = new MqttSubscriber();
		/*try {
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = 
					new SqlSessionFactoryBuilder().build(inputStream);
			
			sqlSession = sqlSessionFactory.openSession(true);
		}catch(IOException e) {
			e.printStackTrace();
			return;
		}*/
		//List<Map<String, Object>> list = sqlSession.selectList("selectDevices");
		
		//for(Map<String,Object> m : list) {
		
			//mqttSubscribe((String)m.get("homeIoT"), 1883, "home/#", Integer.parseInt(m.get("elderly").toString()));
			client.mqttSubscribe("121.138.83.121", 1883, "home/#", 13);
			//client.mqttSubscribe("127.0.0.1", 1883, "home/#", 10);
		//}
    }
}
