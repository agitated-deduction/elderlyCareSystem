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


###log4j -> logback
개발시 오류 확인, 처리를 위해 사용되는 logging.
logging사용시 에러 발생 관련 정보를 제공받을 수 있어서 유용하다.
logback은 log4j를 기반으로 만든 logging 라이브러리. logback 사용 시에 log4j보다 장점이 많다.
https://www.lesstif.com/java/logback-spring-framework-log-19365998.html
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration scan="true" scanPeriod="30 seconds">
 
<property name="LOG_HOME" value="logs" />
 	<property name="LOG_PATTERN" value="%logger{36} %d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"/>
	<appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
		<file>${LOG_HOME}/test-web-app.log</file>
		<rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
			<!-- daily rollover -->
			<fileNamePattern>${LOG_HOME}/test-web-app.log.%d{yyyy-MM-dd}.log</fileNamePattern>
			<!-- keep 30 days' worth of history -->
			<maxHistory>30</maxHistory>
		</rollingPolicy>
		<encoder>
			<pattern>${LOG_PATTERN}</pattern>
		</encoder>
	</appender>
	<appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
		<encoder>
			<pattern>
				${LOG_PATTERN}
			</pattern>
		</encoder>
	</appender>
	<logger name="org.springframework" level="DEBUG" >
		<appender-ref ref="STDOUT" />
		<appender-ref ref="FILE" />
	</logger>
 	
	<!-- turn OFF all logging (children can override) -->
	<root level="INFO">
		<appender-ref ref="STDOUT" />
		<appender-ref ref="FILE" />
	</root>
</configuration>
```

### interceptor
컨트롤러에서 들어오는 HttpRequest와 컨트롤러가 응답하는 HttpResponse를 가로채는 역할을 한다.
