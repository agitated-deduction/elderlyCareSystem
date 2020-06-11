# health monitoring database
```sql
create database healthmonitoring;

use healthmonitoring;
```
## member table
보호자와 담당자가 있다. 담당자는 가입할 필요 없이 등록되어있다고 가정한다.<br>
보호자는 가입요청을 할 수 있고, 담당자가 승인하면 그 때부터 사용 가능하다.
```sql
create table membe(
	m_key int NOT NULL AUTO_INCREMENT,
    m_id varchar(32) NOT NULL,
    m_pwd varchar(32) NOT NULL,
    m_name varchar(16) NOT NULL,	
    m_tel varchar(16) NOT NULL,
    m_email varchar(64) NOT NULL,	#-1:승인되지 않은 가입자 0:승인된 가입자 1:담당자
    m_role int NOT NULL,
    PRIMARY KEY(m_key)
);
```
## device table
기기에는 기기와 기기사용자의 정보를 저장한다.<br>
# 통신 위한 정보 추가 필요 20200604<br>

```sql
create table device(
	d_key int NOT NULL AUTO_INCREMENT,
    d_name varchar(32) NOT NULL,
    d_birth date NOT NULL,
    d_tel varchar(16),
    d_address varchar(256),
    d_illness varchar(128),			#질병코드를 ';'로 구분하여 작성.
    PRIMARY KEY(d_key)
);
```
## relation table
관계 정리 테이블
```sql 
create table manage_relation(
	device int,
    staff int,
    guardian int,
	FOREIGN KEY (device) REFERENCES device(d_key),
    FOREIGN KEY (staff) REFERENCES membe(m_key),
    FOREIGN KEY (guardian) REFERENCES membe(m_key)
);
```

추가로 만들 테이블
datalog_today

datalog_day
datalog_month



-------------
#DB connection


```java

    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String URL = "jdbc:mysql://127.0.0.1:3306/healthmonitoring";
    static final String USERNAME = "healthmonitoring";
    static final String PASSWORD = "hm1234";

```
DB 연결 테스트를 하는데 오류가 발생했다.
```
Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'. The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary. java.sql.SQLException: The server time zone value '????α? ????' is unrecognized or represents more than one time zone. You must configure either the server or JDBC driver (via the 'serverTimezone' configuration property) to use a more specifc time zone value if you want to utilize time zone support. at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:129)
```


https://developer-kylee.tistory.com/8<br>
위의 블로그 참고하여 수정
```java

    static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String URL = "jdbc:mysql://127.0.0.1:3306/healthmonitoring?serverTimezone=UTC";
    static final String USERNAME = "healthmonitoring";
    static final String PASSWORD = "hm1234";

```




### DB수정
20200611
```sql
create table `user`(
    uid varchar(32) NOT NULL,
    upwd varchar(32) NOT NULL,
    uname varchar(16) NOT NULL, 
    utel varchar(16) NOT NULL,
    uemail varchar(32) NOT NULL,    
    urole char(1) NOT NULL,             #-1:승인되지 않은 가입자 0:승인된 가입자 1:담당자
    PRIMARY KEY(uid)
);

create table deviceUser(
    dkey int NOT NULL AUTO_INCREMENT,
    dname varchar(32) NOT NULL,
    dbirth date NOT NULL,
    dtel varchar(16),
    daddr varchar(64),
    PRIMARY KEY(dkey)
);

create table device(
    dkey int,
    staff varchar(32),
    relative varchar(32),
    FOREIGN KEY (devuser) REFERENCES device(dkey),
    FOREIGN KEY (staff) REFERENCES `user`(uid),
    FOREIGN KEY (guardian) REFERENCES `user`(uid),
    homeIoT varchar(32),
    bandIoT varchar(32),
    PRIMARY KEY(dkey)
);
```