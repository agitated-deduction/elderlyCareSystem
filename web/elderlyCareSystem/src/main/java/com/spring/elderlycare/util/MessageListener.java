package com.spring.elderlycare.util;

//@Component
public class MessageListener implements Runnable{
	//@Autowired
	//MQTTSubscriber subscriber;
	
	@Override
	public void run() {
		System.out.println("============Message Listener==========");
		// TODO Auto-generated method stub
		//아이피 리스트, subscribe.
		//subscriber.config("222.106.22.114", 1883, false, false);
		while(true) {
			
			//subscriber.subscribeMessage("home/#");
		}
	}

}
