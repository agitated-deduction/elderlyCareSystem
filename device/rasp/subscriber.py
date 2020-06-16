# -*- coding: utf-8 -*- 
import paho.mqtt.client as mqtt
import time
#---- 받기 

def on_connect(client, userdata, flags, rc):
    print("Connected with result code "+str(rc))

#서버로부터 publish message를 받을 때 호출되는 콜백
def on_subscribe(client, userdata, mid, granted_qos):
    print("subscribed: " + str(mid) + " " + str(granted_qos))
    
def on_message(client, userdata, msg):
    print(str(msg.payload.decode("utf-8")))
    

client = mqtt.Client() #client 오브젝트 생성
client.on_connect = on_connect #콜백설정
client.on_subscribe = on_subscribe
client.on_message = on_message #콜백설정

# client.connect('192.168.1.34', 1883)
client.connect('222.106.22.114', 1883)   # 외부 통신용

client.subscribe('home/#', 1)  # home 아래의 모든 토픽을 구독한다.
client.loop_forever()
