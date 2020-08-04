# -*- coding: utf-8 -*- 
import requests
import json
from pyfcm import FCMNotification

# push_service = FCMNotification(api_key="cTTX-DV0R1GPcOK48bxMf5:APA91bGZcYpPcHxfHK-IJGuPYIfMIE4KNxmCzM_IP8gHratx_uc42vrWRBrU7mNmM7pp3a2pFcs8nK2WKVOw5eLhbqZ2Q5_v8qifkVI-ti9GAx6D1m-7MjVbbtNhwYIH31DPuSErCgL-")

# registration_id = "eTRx-Z31TdCjy00iLSygQB:APA91bHKGYvaPTKc26kIJjhC2Bu_GQf-XPlwnZNMubK4gqptdhxtIEmqdh-r9-RyFClj0BLAoXRQn_xOBN-obMhMsUU__q_JqmKeSN1DCcQlb5zSzgepPzJM6gD_Qwu43S4bpZhhA1Gx"
# message_title = "Test"
# message_body = "Hi john, your customized news for today is ready"
# result = push_service.notify_single_device(registration_id=registration_id, message_title=message_title, message_body=message_body)

# print result
device = 'eTRx-Z31TdCjy00iLSygQB:APA91bHKGYvaPTKc26kIJjhC2Bu_GQf-XPlwnZNMubK4gqptdhxtIEmqdh-r9-RyFClj0BLAoXRQn_xOBN-obMhMsUU__q_JqmKeSN1DCcQlb5zSzgepPzJM6gD_Qwu43S4bpZhhA1Gx'

def send_fcm_notification(ids, title, body):
    # fcm 푸시 메세지 요청 주소
    url = 'https://fcm.googleapis.com/fcm/send'
    
    # 인증 정보(서버 키)를 헤더에 담아 전달
    headers = {
        'Authorization': "key=AAAAdhBR2Mk:APA91bGHV1aLRh3fYttfaxRBgDuqszk4kly85eYX2H3i526Hd7WpmRh1YzS5QblOUte9NVGWVhNxvLLo4mpFRtieQ5tLeWw9F7R7x9iNN_JGFTmdkGxRLwAaPSTA32fpi5LWjc77iKgK",
        'Content-Type': 'application/json; UTF-8'
    }

    # 보낼 내용과 대상을 지정
    content = {
        'to': ids,
        'data': {
            'ekey' : 1,
            'title': title,
            'body': body,
            'url': 'http://192.168.1.19:5000'
        }
    }

    # json 파싱 후 requests 모듈로 FCM 서버에 요청
    
    requests.post(url, data=json.dumps(content), headers=headers)
    print('sending...')


if __name__ == "__main__":
    send_fcm_notification(device,'노인2','이틀간 움직임 없음..')