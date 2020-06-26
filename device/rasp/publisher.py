# -*- coding: utf-8 -*- 
import datetime as dt
import time
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
print("connecting to broker")
client.connect(host=broker_address, port=broker_port)
print("Subscribing to topic","home/temp")
print("Subscribing to topic","home/humid")
print("Subscribing to topic","home/alone")


# f = open('/home/pi/_GUI/Move/move(00:00:00).avi')
# vidstring = f.read()
# byteArray = bytes(vidstring)
# client.publish("home/vid", byteArray ,0)
# client.loop_forever()


while True:
       h,t = Adafruit_DHT.read_retry(sensor, pin)
       client.publish("home/temp", str(t))  # home 의 온도 토픽
       client.publish("home/humid", str(h)) # home 의 습도 토픽 
       print("Temperature = {0:0.1f}*C Humidity = {1:0.1f}%".format(t, h))


       # f = open('/home/pi/_GUI/Move/b.txt')
       # client.publish("home/vid", f.read())
       
      
       time.sleep(1) # 30초 마다 전송 