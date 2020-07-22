package com.maven.mqtt.asyncmqtt;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MqttDAO {
	private SqlSession sqlSession = null;
	
	private static final String ns = "mqttSubscriber.";
	public MqttDAO() {
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
	public void insert(Map<String, Object>obj) {
		sqlSession.insert(ns+"log", obj);
		sqlSession.commit(true);
	}
	public List<Map<String, Object>> getDevList() {
		return sqlSession.selectList("selectDevices");
	}
}