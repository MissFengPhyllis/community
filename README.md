## code community

### material
[Spring Document](https://spring.io/guides)
[Spring Web](https://spring.io/guides/gs/serving-web-content/)
[es](https://elasticsearch.cn/explore/)
[Github deploy key]()
[FlyWay maven config](https://flywaydb.org/getstarted/firststeps/maven)
[bootstrap](https://v3.bootcss.com/css/)
[lombok](https://projectlombok.org/ )
[spring interceptor](https://docs.spring.io/spring/docs/5.0.3.RELEASE/spring-framework-reference/web.html#mvc-handlermapping-interceptor)

### tool
https://git-scm.com/

### script
```sql
create table USER
(
    ID           INT auto_increment,
    ACCOUNT_ID   VARCHAR(100),
    NAME         VARCHAR(50),
    TOKEN        CHAR(36),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    constraint USER_PK
        primary key (ID)
);
CREATE USER IF NOT EXISTS sa PASSWORD '123';
ALTER USER sa admin true ;


```
```css
mvn flyway:migrate
```