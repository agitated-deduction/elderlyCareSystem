#-*- encoding:utf-8 -*-
import time, random, sys, os
import subprocess
from PyQt4.QtGui import *
from PyQt4.QtCore import *
from PyQt4 import *
from PyQt4 import uic
from PyQt4 import QtCore, QtCore, QtGui
from weather import *
# from weather import NowTemp


# -- 온습도 센서 모듈 
import Adafruit_DHT
sensor = Adafruit_DHT.DHT11
pin = 4  # 온습도 센서 핀 


class mainGUI(QDialog):

    def __init__(self, parent=None):
        QDialog.__init__(self, parent)
        self.ui = uic.loadUi("main.ui")  # ui불러오기 
        self.ui.show()
        # self.ui.showMaximized()
        self.setGeometry(0, 0, 800, 480)  # 화면 사이즈 

        self.th = Thread1()  # dht
        self.th2 = Thread2()  # weather
        self.th3 = Thread3() # timer
        

        # 현재 날짜 세팅 
        self.dateTimeVar = QDateTime.currentDateTime()  # 값 받아오기 
        self.timer_() # 타이머 실행
        self.displayDateTime() # 날짜 함수 실행 
        self.image()  # 이미지 함수 실행 
        self.dht() # 온도 센서 
        self.displayweather()  # 날씨 
        
        
    def timer_(self):

        self.th3.start()  # 쓰레드 시작 

        #---- 시계
        # timer 초기화
        self.timer = QtCore.QTimer()
        
        # 업데이트 및 시작
        self.timer.timeout.connect(self.displayDateTime)
        self.timer.start(1000) # 1초다마 실행 


        #---- 날씨 
        self.Weathertimer = QtCore.QTimer()
        self.Weathertimer.setInterval(1000*60*60*3)      # 1000*60*60*3  세 시간 마다 실행 
        self.Weathertimer.timeout.connect(self.displayweather)
        self.Weathertimer.start()


    def displayDateTime(self) :
        
        # 현재 날짜 세팅 
        self.dateTimeVar = QDateTime.currentDateTime()  # 값 받아오기
        currenttime = self.dateTimeVar.toString("yyyy-MM-dd      hh:mm:ss")
        # 현재 날짜 넘기기 
        self.ui.label_time.setText(currenttime)

    def image(self):
        self.clockIcon = QPixmap()
        self.temIcon = QPixmap()
        self.humIcon = QPixmap()

        self.clockIcon.load("clock.png")  # 이미지 불러오기 
        self.temIcon.load("tem.png")
        self.humIcon.load("hum.png")


        self.clockIcon = self.clockIcon.scaledToWidth(80)  # 크기 조정
        self.temIcon = self.temIcon.scaledToWidth(80)
        self.humIcon = self.humIcon.scaledToWidth(80)

        self.ui.clockIcon.setPixmap(self.clockIcon)   # ui안에 넣기 
        self.ui.temIcon.setPixmap(self.temIcon)
        self.ui.humIcon.setPixmap(self.humIcon)


    def dht(self):  # 온습도 센서 

        self.th.start()  # 쓰레드 시작 

        self.th.change_value1.connect(self.ui.progressBar.setValue)
        self.th.change_value2.connect(self.ui.progressBar_2.setValue)

        #---- 35도 이상일경우 열사병 알림 
        # if()

    def displayweather(self):  
        self.th2.start()  # 쓰레드 시작 
 
        self.ui.label_weather_6.setText('현재온도: '+NowTemp+'\n')
        self.ui.label_weather_5.setText(WeatherCast+'\n')
        self.ui.label_weather_4.setText('자외선:'+TodayUV+'\n')
        self.ui.label_weather_3.setText('미세먼지:'+FineDust+'\n')
        self.ui.label_weather_2.setText('초미세먼지:'+UltraFineDust+'\n')
        self.ui.label_weather.setText('오존 지수:'+Ozon+'\n')



class Thread1(QThread):
    # 값이 변경되면 그 값을 change_value 시그널에 값을 emit 한다.
    change_value1 = pyqtSignal(int)
    change_value2 = pyqtSignal(int)

    def __init__(self):
        QThread.__init__(self)
        self.cond = QWaitCondition()
        self.mutex = QMutex()
        
        self._status = True


    def run(self):
        
        
        while True:
            
            h, t = Adafruit_DHT.read_retry(sensor, pin)         

            self.change_value1.emit(t)
            self.change_value2.emit(h)
            
            # print(t)  # 쓰레드 확인용
            # print(h)
            self.msleep(100)


class Thread2(QThread):   ## weather.py  reload

    def __init__(self):
        QThread.__init__(self)

    def run(self):
            
        from weather import *
        import weather  
        reload(weather)   # 날씨를 3 시간 마다 다시 읽기 위해서 


class Thread3(QThread):   ## 타이머,카메라 쓰레드 

    def __init__(self):
        QThread.__init__(self)

    def run(self):
            
        mainGUI.dateTimeVar = QDateTime.currentDateTime()  # 값 받아오기

        subprocess.call('sh ~/stream.sh', shell=True)  ## 카메라 스트리밍 
        # stream.sh 파일 없으면 만든다.  mjpg_streamer -i "input_raspicam.so -vf" -o "output_http.so -p 8090 -w /usr/local/share/mjpg-streamer/www/"

        subprocess.call("python /home/pi/_GUI/opencv.py",shell=True)  ##-------- 움직임 감지 실행

if __name__ == '__main__':
    
    app = QApplication(sys.argv)    
    w = mainGUI()
    sys.exit(app.exec_())