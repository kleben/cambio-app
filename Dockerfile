FROM amazoncorretto:17
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} cambio-1-0.jar
ENTRYPOINT ["java","-jar","/cambio-1-0.jar"]