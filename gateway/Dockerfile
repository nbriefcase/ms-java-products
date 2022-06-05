FROM openjdk:11
VOLUME /tmp
EXPOSE 8090
ADD ./target/gateway-0.0.1-SNAPSHOT.jar gateway-service.jar
ENTRYPOINT ["java", "-jar", "/gateway-service.jar"]