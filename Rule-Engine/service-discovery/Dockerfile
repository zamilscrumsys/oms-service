FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/service-discovery-1.0.0.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
