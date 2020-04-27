## 代码社区

### 资料
[Spring 文档](https://spring.io/guides)
[Spring Web](https://spring.io/guides/gs/serving-web-content/)
[es](https://elasticsearch.cn/explore/)
[Github deploy key]()
[FlyWay 的maven配置](https://flywaydb.org/getstarted/firststeps/maven)
[bootstrap](https://v3.bootcss.com/css/)

### 工具
https://git-scm.com/

### 脚本
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