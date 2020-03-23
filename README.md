# Multitenancy
Follow are 2 different approaches of Multitenancy.
- Schema per tenant
- Database per tenant [check it out](https://github.com/carloselpapa10/multitenancy/tree/feature/database-per-tenant)
> Note: Master and develop branchs contain Schema per tenant approach.

## Prerequisites
- Java 8 or greater
- Apache Maven
- Git
- Docker

## How to run it?
Run this Docker command to create a mysql database.
```sh
$ docker run --name mysqldb -p 3309:3306 -e MYSQL_ROOT_PASSWORD=rootpassword -d mysql:latest
```
### Schema per tenant approach
Install the maven application
```sh
$ mvn clean install -DskipTests
```
Run the application
```sh
$ mvn spring-boot:run
```

### Database per tenant approach
Checkout out the database per tenant branch
```sh
$  git checkout feature/database-per-tenant
```
Install the maven application
```sh
$ mvn clean install -DskipTests
```
Run the application
```sh
$ mvn spring-boot:run
```
Register each tenant manually in the public database. So, each row, each tenant
```sh
INSERT INTO public.DataSourceConfig VALUES (1, 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://localhost:3309/test1?ApplicationName=MultiTenant', 'test1', 'root', 'rootpassword', true);
INSERT INTO public.DataSourceConfig VALUES (2, 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://localhost:3309/test2?ApplicationName=MultiTenant', 'test2', 'root', 'rootpassword', true);
```
Stop the application (Ctrl + C) and start it again to apply the changes.

### How to use it?
POST request to add a new city (Bogota) into tenant named test1
```sh
$ curl -X POST   http://localhost:8080/ -H 'Content-Type: application/json' -H 'X-TenantID: test1' -d '{"name":"Bogota"}'
```

POST request to add a new city (Madrid) into tenant named test2
```sh
$ curl -X POST   http://localhost:8080/ -H 'Content-Type: application/json' -H 'X-TenantID: test2' -d '{"name":"Madrid"}'
```

GET request to see all cities into tenant named test1
```sh
$ curl -X GET http://localhost:8080/ -H 'Content-Type: application/json' -H 'X-TenantID: test1'
```
GET request to see all cities into tenant named test2
```sh
$ curl -X GET http://localhost:8080/ -H 'Content-Type: application/json' -H 'X-TenantID: test2'
```

### References
1. [Hibernate 5 User Guide](https://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#multitenacy) 
2. [Hibernate 5 - Multenancy Baeldung](https://www.baeldung.com/hibernate-5-multitenancy)
3. [MultiTenancy implementation using spring boot](https://medium.com/swlh/multi-tenancy-implementation-using-spring-boot-hibernate-6a8e3ecb251a)
4. [Spring boot multitenancy](https://bytefish.de/blog/spring_boot_multitenancy/)
5. [SaaS Tenancy app design patterns](https://docs.microsoft.com/es-mx/azure/sql-database/saas-tenancy-app-design-patterns)

### MSc Carlos Avendaño
[Linkedin](https://www.linkedin.com/in/carlos-alberto-avenda%C3%B1o-arango-534b0a137/)