FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/config-service-1.0.0.jar app.jar
COPY config-repo ./config-repo

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
