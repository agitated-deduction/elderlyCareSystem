
# -*- coding: utf-8 -*- 
import datetime as dt
import time
import base64
import paho.mqtt.client as mqtt
import paho.mqtt.publish as publish
import Adafruit_DHT
import urllib2
sensor = Adafruit_DHT.DHT11
pin = 4
#---- 보내기

# broker_address="192.168.1.42" 
broker_address="localhost" 

broker_port=1883
client = mqtt.Client() #create new instance
print("connecting to broker....")
client.connect(host=broker_address, port=broker_port)
print("Subscribing to topic","home/temp")
print("Subscribing to topic","home/humid")
print("Subscribing to topic","home/alone")
print("Subscribing to topic","home/dem")
print("Subscribing to topic","home/vid")

#---- 동영상 인코딩해서 전송한다.
# f  = open('/home/pi/_GUI/Move/move2.avi')

# encoded = base64.b64encode(f.read())
# client.publish("home/11/vid", encoded ,0, True)
# # client.publish("home/11/vid", encoded ,0, True)
# print("..... video....")
# f.close()

#---- 반복적으로 받아올 센서를 전송한다. 
while True:
       h,t = Adafruit_DHT.read_retry(sensor, pin)
       client.publish("home/temp", str(t))  # home 의 온도 토픽
       print("Temperature = {0:0.1f}*C".format(t))
       time.sleep(15) # 30초 마다 전송 
       
       client.publish("home/humid", str(h)) # home 의 습도 토픽 
       print("Humidity = {}%".format(h))
       time.sleep(15) # 30초 마다 전송 

# print("Temperature = {0:0.1f}*C Humidity = {1:0.1f}%".format(t, h))