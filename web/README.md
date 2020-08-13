# 노인 건강 모니터링 시스템



* 홈 디바이스
* 웨어러블 디바이스(스마트 밴드)
* 웹
* 앱


## WEB

#### 개발 환경
Back-end : Apache Tomcat, Java, Spring framework, MySQL
Front-end : html, css, javascript, jquery
프론트엔드는 무료 대시보드 템플릿 사용
https://www.egrappler.com/templatevamp-twitter-bootstrap-admin-template-now-available/

#### 웹 주요 기능
1. 센서 데이터 서버로 수집
2. 데이터 전송 및 웹 페이지로 가시화
3. 로그인, 로그아웃, 회원 관리 기능
4. 간단한 일정 관리 기능


#### 화면 구성

메인 화면
![home](./img/home.png)

데이터 화면
![data](./img/data.png)

모니터링 화면
![monitoring](./img/data.png)


#### DB 구조
![erd](./img/erd.jpg)
user는 사이트 가입자(보호자) 및 담당 직원을 말한다. elderly는 관리 대상은 나타낸다. 센서 데이터는 홈 디바이스로부터 오는 데이터와