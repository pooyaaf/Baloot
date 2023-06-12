FROM openjdk:21
COPY target/Baloot.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080