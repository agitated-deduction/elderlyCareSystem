# -*- coding: utf-8 -*- 
import cv2
import numpy as np
import sys

cap = cv2.VideoCapture('http://192.168.1.34:8090/?action=stream')   # 스트리밍 영상 가져오기 


# Background Subtraction 의 알고리즘 중 BackgroundSubtractorMOG2 적용해 본다.
# BackgroundSubtractorMOG2
fgbg = cv2.createBackgroundSubtractorMOG2()
kernel = cv2.getStructuringElement(cv2.MORPH_ELLIPSE,(3,3))  # 커널을 생성
# 얼굴 검출 
faceCascade = cv2.CascadeClassifier('/usr/local/share/opencv4/haarcascades/haarcascade_frontalface_default.xml')


# 파일 저장하기 
frame_width, frame_height, frame_rate = int(cap.get(3)), int(cap.get(4)), int(cap.get(5))
fourcc = cv2.VideoWriter_fourcc(*'DIVX') # 디지털 포맷 코드 - 인코딩 방식 설정 
# out = cv2.VideoWriter('move2.avi', fourcc, frame_rate, (frame_width, frame_height), 0) # 캡쳐 동영상 저장
cnt = 0


while(cap.isOpened()):
    ret, frame = cap.read()      # 배경 제거 프레임
    ret1, sub_frame = cap.read()  # 물체 검출할 서브 프레임 
        
    if (ret):
        
        fgmask = fgbg.apply(frame)
        
        fgmask = cv2.morphologyEx(fgmask, cv2.MORPH_CLOSE, kernel)  # 형태 변환 중 Closing 기법을 적용
        fgmask = cv2.morphologyEx(fgmask, cv2.MORPH_OPEN, kernel)    # 형태 변환 중 Opening 기법을 적용

        # 물체 검출 / 임계처리 (src, 임계값, 임계값 넘었을 때 , 처리 타입 )
        ret1, thr = cv2.threshold(fgmask, 70, 255, cv2.THRESH_BINARY)
        # 가장 바깥쪽 라인을 찾으며, 최소 point만 저장하여 메모리 절약 
        countors, hierarchy = cv2.findContours(thr, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        cv2.drawContours(sub_frame, countors, -1, (0, 255, 0), 1)

        try:
            cnt = countors[0]
            cx, cy, cw, ch = cv2.boundingRect(cnt)
            contour_box = int(cw * ch)
        except:
            pass

        # area = int(cv2.contourArea(cnt))
        # perimeter = int(cv2.arcLength(cnt, False))


        faces = faceCascade.detectMultiScale(
            sub_frame,
            scaleFactor=1.1,
            minNeighbors=5,
            minSize=(30,30),
            flags = cv2.CASCADE_SCALE_IMAGE 
        )


        if( contour_box > 20):  # 컨투어의 면적이 크면 글자표시 
            cv2.putText(sub_frame,'Detected!', (10, 30),
            cv2.FONT_HERSHEY_SIMPLEX, 1, (0,0,255), 2)

            
        for(x, y, w, h) in faces:
            cv2.rectangle(sub_frame, (x, y), (x+w, y+h), (0, 0, 255), 2)
            if (x > 0) :  # 사각형이 존재한다면 , 얼굴이 인식된다면 
                cv2.putText(sub_frame,'rectangle!', (10, 60),
                cv2.FONT_HERSHEY_SIMPLEX, 1, (0,0,255), 2)

            if (x > 0) and ( contour_box > 20):
                cv2.putText(sub_frame,'HUMAN Detected!', (10, 90),
                cv2.FONT_HERSHEY_SIMPLEX, 1, (0,0,255), 2)



        cv2.imshow('frame', fgmask)
        cv2.imshow("Found", sub_frame) 

        # out.write(fgmask)
   
    if cv2.waitKey(10) & 0xFF == ord('q'):  # q로 종료 
        break


cap.release()
# out.release()
cv2.destroyAllWindows()