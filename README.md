# Multitenancy
This repo implements 2 different approaches of Multitenancy.
- Schema per tenant
- Database per tenant [check it out](https://github.com/carloselpapa10/multitenancy/tree/feature/database-per-tenant)
- Schema per tenant using mqtt requests (kafka broker) [check it out](https://github.com/carloselpapa10/multitenancy/tree/feature/schema-per-tenant-with-kafka)
> Note: Master and develop branchs contain Schema per tenant approach.

## Understanding the Request Flow
The process to establish a multi-tenant communication usually consists of the following three steps:
1. Accept the incoming connection, and authenticate the user if necessary.
2. Intercept the request and identify the tenant for which the user is issuing the request.
3. Establish a connection with the database or schema of the tenant.

Tenant identification is performed against a default schema, which contains the user's information. A user can authenticate himself on an external service and then pass the tenant information using an HTTP header or a Mqtt header.

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
Check out the database per tenant branch
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

### Schema per tenant using mqtt requests (kafka broker) approach
Run these docker commands
```sh
$ docker run -d \
	--name zookeeper \
	--network=demo_default \
	-p 2181:2181 \
	-e ZOOKEEPER_CLIENT_PORT=2181 \
	confluentinc/cp-zookeeper

$ docker run -d \
	--name kafka \
	--network=demo_default \
	-p 9092:9092 \
	-e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
	-e KAFKA_ADVERTISED_HOST_NAME=${DOCKER_HOST_IP} \
	-e KAFKA_PORT=9092 \
	-e KAFKA_ADVERTISED_PORT=9092 \
	wurstmeister/kafka:0.11.0.1
```
Check out the schema per tenant with kafka branch
```sh
$  git checkout feature/schema-per-tenant-with-kafka
```
Install the maven application
```sh
$ mvn clean install -DskipTests
```
Run the application
```sh
$ mvn spring-boot:run
```
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
6. [Multi-Tenancy Data Models in Kafka](https://www.digitalernachschub.de/blog/multi-tenancy-data-models-in-kafka/)
7. [Introduction to the Spring Cloud Stream](https://www.alibabacloud.com/blog/introduction-to-the-spring-cloud-stream-system_594822)
8. [Spring Cloud Stream With Kafka](https://dzone.com/articles/spring-cloud-stream-with-kafka)
9. [Kafka With Spring Cloud Stream](https://dzone.com/articles/kafka-with-spring-cloud-stream)
10. [Spring Cloud Stream - Pivotal](https://spring.io/projects/spring-cloud-stream)

### MSc Carlos Avendaño
[Linkedin](https://www.linkedin.com/in/carlos-alberto-avenda%C3%B1o-arango-534b0a137/)