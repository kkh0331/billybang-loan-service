FROM openjdk:17
ARG JAR_FILE=target/*.jar
VOLUME /tmp
COPY ${JAR_FILE} billybang-loan-service.jar
EXPOSE 3002
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/billybang-loan-service.jar"]