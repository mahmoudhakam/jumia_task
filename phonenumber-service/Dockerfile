FROM openjdk:8-jdk-alpine

COPY target/phone-number-service.jar /phone-number-service.jar
COPY sample.db /sample.db

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/phone-number-service.jar"]