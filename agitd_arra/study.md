# spring

* 경량 컨테이너로서 자바 객체를 직접 관리
* 제어 역행 (IOC Inversion of Control)
* 의존성 주입(DI Dependency Injection)
* 관점지향 프로그래밍(AOP Aspect-Oriented Programming)
* MVC패턴
----->공통부분의 코딩 용이, 확장성 높음.


DI Dependency Injection
의존성 : unit test와 코드 수정이 어려워진다.
가령 class명을 변경하게 된다면 해당 클래스가 사용된 모든 클래스를 수정해야 한다.

이러한 이유로 스프링은 DI를 사용해 모듈간의 결합도를 낮춘다.
* 의존성을 대신 주입 할 bean class작성
* 주입을 위한 xml또는 @(어노테이션) 설정

IOC Container(Spring Container)
사용자가 작성한 메타데이터(xml또는 @(어노테이션))에 따라 bean 클래스를 생성 및 관리하는 spring의 컴포넌트를 말한다.

* IOC Container 설정
1. xml 파일 기술
	: code와 의존성을 주입하는 부분을 분리할 수 있다. 유지보수가 쉽다. 각 객체간의 의존관계를 한눈에 파악할 수 있다. 그러나 시스템이 거대해진다면 xml 파일이 많아져 오히려 유지보수가 어려울 수 있다.
2. @annotation 사용
	: 프로그램의 규모가 커지면서 xml에 기술할 내용이 많아짐에 따라 더 효율적인 annotation이 등장하게 된다. 직관적인 코드 작성이 가능하다.

@Configuration : 스프링 IOC container에게 해당 클래스가 bean 구성 클래스임을 알려줌
@Bean : 외부 라이브러리를 bean으로 만들고자 할 때 사용
@Component : 개발자가 직접 작성한 Class를 Bean으로 등록하기 위해 사용
@AutoWired :  Component 를 사용한 Bean의 의존성 주입은 AutoWired를 사용하여 의존성 자동 주입 가능

# MQTT
### Message Queuing Telemetry Transport

IoT기기, 모바일 기기에 최적화 된 가벼운 메세징 프로토콜

IoT 기기(publisher) --data--> MQTT브로커 --data--> IoT기기, 컴퓨터, 스마트폰(subscriber)

사물인터넷을 사용하기 위해 개발 된 TCP기반의 프로토콜로서 낮은 전력, 대역폭, 성능의 환경에서도 사용 가능하다.

publisher가 broker에게 데이터를 전송하면 subscriber에서 데이터를 받아온다.

어디로 데이터를 받을 지 정해주는 것이 Topic이다. publisher가 데이터에 대해 topic을 정하면 그 topic을 구독하는 subsciber는 해당 데이터를 받는다.



참고:<br>
https://www.joinc.co.kr/w/man/12/MQTT/Tutorial<br>
http://www.codejs.co.kr/mqtt-%ec%9d%b4%ed%95%b4%ed%95%98%ea%b8%b0/<br>
https://khj93.tistory.com/entry/MQTT-MQTT%EC%9D%98-%EA%B0%9C%EB%85%90


## mqtt client 구현하기 in java
내용 출처 : https://www.baeldung.com/java-mqtt-client

paho library 의존성 추가
```
<dependency>
  <groupId>org.eclipse.paho</groupId>
  <artifactId>org.eclipse.paho.client.mqttv3</artifactId>
  <version>1.2.0</version>
</dependency>
```

client에서 메세지를 주고 받기 위해서는 IMqttClient interface가 필요하다.
이 interface는 서버와 연결을 위한 메소드, 그리고 메세지를 주고 받을 수 있는 메소드를 가지고 있다.

1. MqttClient클래스의 인스턴스 생성

```java
String publisherId = UUID.randomUUID().toString();
IMqttClient publisher = new MqttClient("tcp://iot.eclipse.org:1883",publisherId);
```

2. 서버에 연결
`MqttConnectOptions` 클래스를 통해 인스턴스 선택적으로 전달.<br>
보안 자격 증명, 세션 복구 모드, 재 연결 모드 등과 같은 추가 정보 전달 가능.<br>
필요한 옵션만 설정하고 나머지는 기본값으로 가정한다.<br>
```java
MqttConnectOptions options = new MqttConnectOptions();
options.setAutomaticReconnect(true);
options.setCleanSession(true);
options.setConnectionTimeout(10);
publisher.connect(options);
```
위의 코드는 해당 내용을 나타낸다.<br>
* 네트워크 장애 시 라이브러리가 서버에 자동으로 다시 연결을 시도한다.
* 이전 실행에서 보내지 않은 메세지는 버린다.
* timeout은 10초

3. 메세지 보내기
mqtt는 3단계 QoS(Quality of Service)를 제공한다.<br>
0 - 메세지는 한번만 전달되며, 전달 여부는 확인하지 않는다. Fire and Forget.<br>
1 - 메세지는 반드시 적어도 한 번 전달된다. 값이 중복전달 될 수 있다. subscriber가 중복값을 처리할 수 있는 경우 이 옵션을 써도 된다.<br>
2 - 메세지는 정확히 한 번만 전달된다. 메세지 핸드셰이킹 과정을 추적하기 때문에 품질이 높지만 성능이 떨어질 수 있다. subscriber가 중복값을 처리하기 어려운 경우 사용하면 좋다.<br>

```java
public class EngineTemperatureSensor implements Callable<Void> {
 
    // ... private members omitted
     
    public EngineTemperatureSensor(IMqttClient client) {
        this.client = client;
    }
 
    @Override
    public Void call() throws Exception {        
        if ( !client.isConnected()) {
            return null;
        }           
        MqttMessage msg = readEngineTemp();
        msg.setQos(0);
        msg.setRetained(true);
        client.publish(TOPIC,msg);        
        return null;        
    }
 
    private MqttMessage readEngineTemp() {             
        double temp =  80 + rnd.nextDouble() * 20.0;        
        byte[] payload = String.format("T:%04.2f",temp)
          .getBytes();        
        return new MqttMessage(payload);           
    }
}
```

4. 메세지 받기
```java
CountDownLatch receivedSignal = new CountDownLatch(10);
subscriber.subscribe(EngineTemperatureSensor.TOPIC, (topic, msg) -> {
    byte[] payload = msg.getPayload();
    // ... payload handling omitted
    receivedSignal.countDown();
});    
receivedSignal.await(1, TimeUnit.MINUTES);
```


# Spring project 

## mvc project directory 구조

src/main/java : 자바 소스파일 디렉토리<br>
src/main/resources : 리소스파일(설정파일) 디렉토리<br>
	src/main/resources/log4j.xml : 로그파일<br>
<br>
src/test/java : 테스트 파일 디렉토리<br>
src/test/resources : 테스트 리소스 파일 디렉토리<br>
<br>
Maven Dependencies : maven을 통해 다운받은 라이브러리 파일<br>
<br>
src/main/webapp/resources : 리소스파일 디렉토리(js, css, image등)<br>
<br>
WEB-INF 외부 직접 접근 차단. 컨드롤러를 통하여 접근 가능.<br>
	src/main/webapp/WEB-INF/classes : 클래스 파일 디렉토리<br>
	src/main/webapp/WEB-INF/spring : 스프링 환경 설정 파일 디렉토리 (root-context.xml, servlet-context.xml) (서블릿 파일)<br>
		src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml : dispatcher 서블릿과 관련된 view 지원 및 bean을 설정하는 파일(controller등)<br>
		src/main/webapp/WEB-INF/spring/root-context.xml : 공통 bean을 설넝하는 파일(service, repository, db, log등)<br>
	src/main/webapp/WEB-INF/views : view파일 디렉토리(html, jsp) (jsp파일 폴더)<br>
	src/main/webapp/WEB-INF/web.xml : 설정을 위한 배포서술자 파일. WAS가 최초 구현될 때 web.xml을 읽고 해당 설정 구현. 설정파일.<br>
<br>
target : 빌드 결과물<br>
<br>
pom.xml : maven에서 참조하는 설정파일<br>


### mybatis
개발자가 지정한 SQL, 저장 프로시저 그리고 몇가지 고급 매핑을 지원하는 퍼시스턴스 프레임워크 
https://mybatis.org/mybatis-3/ko/index.html

### interceptor
컨트롤러에서 들어오는 HttpRequest와 컨트롤러가 응답하는 HttpResponse를 가로채는 역할을 한다.


# RESTful

### open api 개방형 api
프로그래밍에서 사용할 수 있는 개방되어 있는 상태의 인터페이스

### rest representational safe tranfer

https://www.youtube.com/watch?v=pKT4OTFjFcA&list=PL9mhQYIlKEhfYqQ-UkO2pe2suSx9IoFT2&index=24

http uri + http method
http uri를 통해 제어할 자원 resource를 명시,
http method (get, post, put, delete)를
통해 해당 자원을 제어하는 명령을 내리는 방식의 ^아키텍쳐^

| http method | CRUD |
|:--------|:--------|
| POST | create(insert) |
| GET | read(select) |
| PUT | update |
| DELETE | delete |

rest의 원리를 따르는 시스템을 restful 용어 사용
ex
GET /list.do? no=510&name=java (query string)
----> GET /bbs/java/510
GET /delete.do?no=510&name=java
----> DELETE /bbs/java/510
GET과 POST만으로 자원에 대한 CRUD를 처리, uri는 액션을 나타내는 기존의 상태에서
4가지 메서드를 사용하여 CRUD처리, uri는 제어하려는 자원 나타내도록

RESTful과 JSON/xml

json은 경량(lightweight)의 data교환 형식
javascript에서 객체를 만들때 사용하는 표현식
특정 언어에 종속되지 않는다.
```json
{
	"string1": "value1",
	"string2": "value2",
	"string3": ["value3", "value4"]
}
```

json 라이브러리 jackson
json을 java객체로 java를 json형태로 변환해주는 json라이브러리

xml extensible markup language
데이터 저장 전달 위한 언어

xml과 html
data전달하는 것에 포커스 - data를 표현하는 것에 포커스
사용자가 마음대로 tag정의 가능 - 미리 정의된 tag만 사용 가능
```xml
<?xml version = "1.0" encoding = "UTF-8"?>
<customer>
	<name>김형희</name>
	<addr>진안</addr>
	<phone>01000000000</phone>
</customer>
```


## spring mvc 기반 restful 웹서비스 환경 설정, 구현
pom.xml
jackson mapper
```xml
<!-- https://mvnrepository.com/artifact/org.codehaus.jackson/jackson-mapper-asl -->
<dependency>
    <groupId>org.codehaus.jackson</groupId>
    <artifactId>jackson-mapper-asl</artifactId>
    <version>1.9.13</version>
</dependency>
```

root-context.xml
`<mvc:annotation-driven />`
`<mvc:default-servlet-handler/>`서버에 내부적으로 정의된 /무시

구현
1. RESTful 웹 서비스 처리할 RESTfulController클래서 작성, Spring Bean등록
2. 요청 처리할 메서드에 @RequestMapping, @ReqeustBody(json ->java)와 @ResponseBody(java->json)어노테이션 선언
3. REST Client Tool(Postman)을 사용하여 각각의 메서드 테스트
4. Ajax통신을 하여 RESTful 웹서비스 호출하는 HTML페이지 작성

Postman 설치 (RESTAPI 테스트 하는 Chrome 확장 프로그램)

* 사용자관리

| Action | Resource URI | HTTP Method |
|:--------|:--------|:--------|
| 사용자 목록 | /users | GET |
| 사용자 보기 | /users/{id} | GET |
| 사용자 등록 | /users | POST |
| 사용자 수정 | /users | PUT |
| 사용자 삭제 | /users/{id} | DELETE |

@RequestBody : HTTP Request Body를 java 객체로 전달받을 수 있다.
@ResponseBody : Java객체를 HTTP Response Body로 전송할 수 있다.


오류

`org.springframework.http.converter.HttpMessageNotWritableException: No converter found for return value of type: class com.spring.elderlycare.dto.MemberDTO`
controller에서 객체 반환시 json으로 변환되지 않음.
```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-core</artifactId>
    <version>2.10.0</version>
</dependency>
		    <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.10.0</version>
</dependency>
```

```xml
<mvc:annotation-driven>
		<mvc:message-converters>
		<bean class = "org.springframework.http.converter.json.MappingJackson2HttpMessageConverter"/>
		</mvc:message-converters>
	</mvc:annotation-driven>
```

### session
클라이언트가 서버 접속하는 순간 생성
default 유지시간 30분(서버에 접속 후 요청 하지 않는 최대 시간)

web.xml파일에서 직접 설정 가능

```xml
<session-config>
	<session-timeout>30</session-timeout>
</session-config>
```

@SessionAttributes 파라미터 지정된 이름이 model에 저장 되면 session에도 저장됨.


### USER기능

| 동작 | 요청 | Method | 기능 |
|:-------|:-------|:-------|:-------|
| 로그인 화면 | /users/login | GET | 로그인 화면을 띄운다 |
| 회원가입 화면 | /users/join | GET | 회원가입 화면을 띄운다 |
| 로그인 체크 | /users/login | POST | 로그인 시도 시 아이디 비밀번호 체크하고 로그인 한다 |
| 회원가입 하기 | /users/join | POST | 회원가입 한다 |
| 로그아웃 | /users/logout | GET | 로그하웃 한다 |
| 내 정보 | /users/{id} | GET | 로그인 된 아이디의 정보를 띄운다 |
| 내 정보 수정 | /users/{id} | PUT | 로그인 된 아이디의 정보를 수정한다 |
| 내 정보 삭제 | /users/{id} | DELETE | 로그인 된 아이디 정보를 삭제한다(탈퇴) |
| 가입 승인(보류) | /users/{b_id} | PUT | 보호자의 가입을 담당자가 승인한다 |



### Spring AOP
Aspect Oriented Programming 관점 지향 프로그래밍
* Aspect: 흩어진 관심사들을 모듈화 한 것. 주로 부가기능을 모듈화 한 것.
* Target: Aspect를 적용하는 곳(클래스, 메서드 등)
* Advice: 실질적으로 어떤 일을 해야 할 지에 대한 것, 실질적 부가기능을 담은 구현체
* JointPoint: Advice가 적용될 위치, 끼어들 수 있는 지점. 메서드 진입 지점, 생성자 호출 시점, 필드에서 값을 꺼내올 때 등 다양한 시점에 적용 가능
* PointCut: JointPoint의 상세 스페ㄱ 정의. ex A라는 메서드의 진입 시점에 호출 <-처럼 더 구체적으로 advice 실행 지점 정할 수 있음

aspect실행 시점 annotation

* @Before (이전) : 어드바이스 타겟 메소드가 호출되기 전에 어드바이스 기능을 수행
* @After (이후) : 타겟 메소드의 결과에 관계없이(즉 성공, 예외 관계없이) 타겟 메소드가 완료 되면 어드바이스 기능을 수행
* @AfterReturning (정상적 반환 이후)타겟 메소드가 성공적으로 결과값을 반환 후에 어드바이스 기능을 수행
* @AfterThrowing (예외 발생 이후) : 타겟 메소드가 수행 중 예외를 던지게 되면 어드바이스 기능을 수행
* @Around (메소드 실행 전후) : 어드바이스가 타겟 메소드를 감싸서 타겟 메소드 호출전과 후에 어드바이스 기능을 수행


출처: https://engkimbs.tistory.com/746 [새로비]



https://docs.spring.io/spring-integration/docs/5.3.0.RC1/reference/html/mqtt.html