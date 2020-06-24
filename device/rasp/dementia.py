# -*- coding: utf-8 -*- 

import cv2
import numpy as np
import sys, time, datetime
from datetime import timedelta, time, datetime
from time import sleep

#----> 이 파일은 main.py에 의해 불러올 것이다.


def dem():
    cap = cv2.VideoCapture('http://192.168.1.34:8090/?action=stream')   # 스트리밍 영상 가져오기 

    # Background Subtraction 의 알고리즘 중 BackgroundSubtractorMOG2 적용해 본다.
    # BackgroundSubtractorMOG2
    fgbg = cv2.createBackgroundSubtractorMOG2()
    kernel = cv2.getStructuringElement(cv2.MORPH_ELLIPSE,(3,3))  # 커널을 생성
    # 얼굴 검출 
    faceCascade = cv2.CascadeClassifier('/usr/local/share/opencv4/haarcascades/haarcascade_frontalface_default.xml')

    StartRec = datetime.today() # 시작 시간 
    NowRec =  datetime.now() 
    EndRec = StartRec + timedelta(seconds=30)  # 30초 뒤  

    cnt = 0
    contour_box = None 

    #---- 이 파일이 켜질 시간 설정
    global Hflag 
    Hflag = 0              # 사람이 인식 되었는지 0=안됨  1=됨
    start_time = time(17,35)  # 밤 11시 ~ 새벽 4시
    end_time =  time(17,40)
    Now_time = datetime.now().time()   # 현재 시간


    nightcamera = 0

    if (start_time <= Now_time and end_time > Now_time):  # 시간되면 카메라 켜진다. 
        nightcamera = 1

        #----- 파일 저장 설정  
        frame_width, frame_height, frame_rate = int(cap.get(3)), int(cap.get(4)), 10
        fourcc = cv2.VideoWriter_fourcc(*'DIVX') # 디지털 포맷 코드 - 인코딩 방식 설정 
        filename = '/home/pi/_GUI/Move/move({}).avi'.format(datetime.now())
        out = cv2.VideoWriter(filename, fourcc, frame_rate, (frame_width, frame_height)) # 캡쳐 동영상 저장


    while(nightcamera):
        
        Now_time = datetime.now().time()   # 현재 시간
        NowRec =  datetime.now() 

        ret, frame = cap.read()      # 배경 제거 프레임
        ret1, sub_frame = cap.read()  # 물체 검출할 서브 프레임 

        if (end_time < Now_time): # 카메라가 켜져있지만 감지되지 않고 시간이 지난 경우 종료.  # 밤에 아무 일도 없는 경우 
            nightcamera = 0
            
        if (ret):
            
            fgmask = fgbg.apply(frame)
            subframe = cv2.cvtColor(sub_frame, cv2.IMREAD_COLOR)
            fgmask = cv2.morphologyEx(fgmask, cv2.MORPH_CLOSE, kernel)  # 형태 변환 중 Closing 기법을 적용
            fgmask = cv2.morphologyEx(fgmask, cv2.MORPH_OPEN, kernel)    # 형태 변환 중 Opening 기법을 적용

            # 물체 검출 / 임계처리 (src, 임계값, 임계값 넘었을 때 , 처리 타입 )
            ret1, thr = cv2.threshold(fgmask, 30, 255, cv2.THRESH_BINARY)
            
            # 흰색 테두리를 찾으며, 최소 point만 저장하여 메모리 절약 
            countors, hierarchy = cv2.findContours(thr, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
            # cv2.drawContours(sub_frame,countors, -1, (0, 255, 0), 1)

            try:
                cnt = countors[-1]
                hull=cv2.convexHull(cnt)  # 대략적인 다각형 그리기 
                cv2.drawContours(sub_frame,[hull], -1, (0, 255, 0), 1)
                area=int(cv2.contourArea(cnt))
                # print(area)

            except:
                pass


            faces = faceCascade.detectMultiScale(
                sub_frame,
                scaleFactor=1.1,
                minNeighbors=5,
                minSize=(30,30),
                flags = cv2.CASCADE_SCALE_IMAGE 
            )

            if( area > 3000):  # 컨투어의 면적이 크면 글자표시 
                cv2.putText(sub_frame,'Detected!', (10, 30),
                cv2.FONT_HERSHEY_SIMPLEX, 1, (0,0,255), 2)

                
            for(x, y, w, h) in faces:
                cv2.rectangle(sub_frame, (x, y), (x+w, y+h), (0, 0, 255), 2)

                if (x+w > 0) and ( area > 3000):  # 이 시간에 사람이 인식 될 때 
                    cv2.putText(sub_frame,'HUMAN Detected!', (10, 90),
                    cv2.FONT_HERSHEY_SIMPLEX, 1, (0,0,255), 2)
                    Hflag = 1
                    StartRec = datetime.today() # 녹화 시작 시간 
                    EndRec = StartRec + timedelta(seconds=30)  # 녹화 30초 뒤
                    
                #--- 알림 전송 해야 한다.


            cv2.imshow("Found", sub_frame) 

        if(Hflag == 1):
            print("~~~~HUMAN Detected!~~~~")
            print("EndRec: ", EndRec.second)
            print("NowRec:",NowRec.second)
            
            out.write(subframe)   # 녹화 시작 /home/pi/_GUI/Move/ 에 저장된다.
      
            if(NowRec.second == EndRec.second): # 초가 지났으면 
                print("~~~~out release~~~~")
                 
                  
                break  # ----> 영상촬영 끝나면 dementia.py를 종료한다.
                # Hflag = 0            

        
        if cv2.waitKey(1) & 0xFF == ord('q'):  # q로 종료 
            break

    try:        
        cap.release()
        out.release()
        cv2.destroyAllWindows()
    except:
        pass

    dem() # 영상 녹화 완료 후  재 실행 


if __name__ == "__main__":
    dem()