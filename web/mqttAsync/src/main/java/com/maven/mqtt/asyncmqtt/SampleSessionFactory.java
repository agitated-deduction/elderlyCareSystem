package com.maven.mqtt.asyncmqtt;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SampleSessionFactory {
	public void demo() {
		String resource = "com/maven/mqtt/sql/mybatis-config.xml";
		
		SqlSession session= null;
		
		try {
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = 
					new SqlSessionFactoryBuilder().build(inputStream);
			
			session = sqlSessionFactory.openSession(false);
		}catch(IOException e) {
			e.printStackTrace();
			return;
		}
		
	}
}
