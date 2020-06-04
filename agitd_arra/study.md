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
## Message Queuing Telemetry Transport

IoT기기, 모바일 기기에 최적화 된 가벼운 메세징 프로토콜

IoT 기기(publisher) --data--> MQTT브로커 --data--> IoT기기, 컴퓨터, 스마트폰(subscriber)

사물인터넷을 사용하기 위해 개발 된 TCP기반의 프로토콜로서 낮은 전력, 대역폭, 성능의 환경에서도 사용 가능하다.

publisher가 broker에게 데이터를 전송하면 subscriber에서 데이터를 받아온다.

어디로 데이터를 받을 지 정해주는 것이 Topic이다. publisher가 데이터에 대해 topic을 정하면 그 topic을 구독하는 subsciber는 해당 데이터를 받는다.



참고:<br>
https://www.joinc.co.kr/w/man/12/MQTT/Tutorial<br>
http://www.codejs.co.kr/mqtt-%ec%9d%b4%ed%95%b4%ed%95%98%ea%b8%b0/<br>
https://khj93.tistory.com/entry/MQTT-MQTT%EC%9D%98-%EA%B0%9C%EB%85%90
