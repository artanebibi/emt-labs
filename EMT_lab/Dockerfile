FROM openjdk:17-jdk-slim
# im not sure if JAR_FILE is actually used or not
ARG JAR_FILE=target/*.jar
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

