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
print("Subscribing to topic","temp/temp")
# client.publish("temp/temp","HI~~~~")#publish

while True:
       h,t = Adafruit_DHT.read_retry(sensor, pin)
       client.publish("temp/temp", str(t))
       client.publish("temp/temp", str(h))
       print("Temperature = {0:0.1f}*C Humidity = {1:0.1f}%".format(t, h))

       time.sleep(15)