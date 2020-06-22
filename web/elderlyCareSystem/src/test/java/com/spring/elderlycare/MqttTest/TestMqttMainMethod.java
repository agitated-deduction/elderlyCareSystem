package com.spring.elderlycare.MqttTest;

import java.util.HashMap;
import java.util.function.Consumer;

public class TestMqttMainMethod {
	public static void main(String[] args) {
		System.out.println("=============mqtt test===========");
		final Consumer<HashMap<Object, Object>> pdk = (arg)->{  //해쉬맵을 받는 함수를 정의하자.
	        arg.forEach((key, value)->{
			    System.out.println( String.format("대상, 키 -> %s, 값 -> %s", key, value) );
	        });			
	    };
		
		MQTTSubscriber subscriber = new MQTTSubscriber(pdk);
		subscriber.config("127.0.0.1", 1883, false, false);
		subscriber.subscribeMessage("test/#");
		
		subscriber.initConnectionLost( (arg)->{  //콜백행위1, 서버와의 연결이 끊기면 동작
            arg.forEach((key, value)->{
                System.out.println( String.format("커넥션 끊김~! 키 -> %s, 값 -> %s", key, value) );
            });
        });

		subscriber.initDeliveryComplete((arg)-> {  //콜백행위2, 메시지를 전송한 이후 동작
            arg.forEach((key, value)->{
                System.out.println( String.format("메시지 전달 완료~! 키 -> %s, 값 -> %s", key, value) );
            });
        });
		new Thread( ()->{
            try {
                Thread.sleep(9000);
                //subscriber.sender("new_topic", "Hello world! 한글한글!");  //이런식으로 보낸다.
                subscriber.disconnect();  //종료는 이렇게!
            } catch (Exception e) {
                e.printStackTrace();
            }
        } ).start();
	}
}

/*
import java.util.HashMap;
import java.util.function.Consumer;

public class TestMqttMainMethod {
    
    
    public static void main(String [] args){
    	System.out.println("=============mqtt test===========");
        final Consumer<HashMap<Object, Object>> pdk = (arg)->{  //메시지를 받는 콜백 행위
            arg.forEach((key, value)->{
                System.out.println( String.format("메시지 도착 : 키 -> %s, 값 -> %s", key, value) );
            });            
        };

        MyMqttClient client = new MyMqttClient(pdk);  //해당 함수를 생성자로 넣어준다.

        client.init("t", "1234", "tcp://127.0.0.1:1883", "test")
              .subscribe(new String[]{"test/#","new_topic"});  //subscribe 메소드는 구독할 대상


        client.initConnectionLost( (arg)->{  //콜백행위1, 서버와의 연결이 끊기면 동작
            arg.forEach((key, value)->{
                System.out.println( String.format("커넥션 끊김~! 키 -> %s, 값 -> %s", key, value) );
            });
        });

        client.initDeliveryComplete((arg)-> {  //콜백행위2, 메시지를 전송한 이후 동작
            arg.forEach((key, value)->{
                System.out.println( String.format("메시지 전달 완료~! 키 -> %s, 값 -> %s", key, value) );
            });
        });


        new Thread( ()->{
            try {
                Thread.sleep(9000);
                client.sender("new_topic", "Hello world! 한글한글!");  //이런식으로 보낸다.
                client.close();  //종료는 이렇게!
            } catch (Exception e) {
                e.printStackTrace();
            }
        } ).start();        
        
    }
}*/