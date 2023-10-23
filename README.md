# betting-app assignment
### by Kindred assignment
#### Tech: Spring Boot, Open API 3, postgres, Flyway 

## Maven

### Testing
First check that you are able to compile and pass the tests:
```
mvn clean test
```

For test report and code coverage:

```
mvn surefire-report:report
open target/site/surefire-report.html

mvn jacoco:report
open target/site/jacoco/index.html
```

## Application Start

### To run the backend API locally:
```
Change connection to locally installed database in application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/bettingdb

mvn clean install 
java -jar target/spring-boot-junit5-1.0-SNAPSHOT.jar

```
### Run with docker container
```
open home directory i.e bettingapp

To build applciation: 
docker-compose build

To deploy application:
docker-compose up -d
```

# Server check
```
To access to the database postgres with web client:

open http://localhost:5050/login
use credentials from .env
```

API Documentation (Swagger):

```
open http://localhost:8080/swagger 
```