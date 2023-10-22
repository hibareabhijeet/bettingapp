FROM maven:3.8.3-openjdk-11 AS build

COPY . .
RUN mvn clean package -DskipTests

FROM adoptopenjdk/openjdk11:alpine-jre
COPY --from=build /target/*.jar bettingapp.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "bettingapp.jar"]


