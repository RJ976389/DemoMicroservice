FROM openjdk:11
EXPOSE 8080
ADD om-demo-service-adapter-service/target/om-demo-service-adapter-service-1.0-SNAPSHOT.jar om-demo-service-adapter-service.jar
ENTRYPOINT ["java","-jar","/om-demo-service-adapter-service.jar"]
