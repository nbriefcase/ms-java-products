FROM openjdk:11
VOLUME /tmp
EXPOSE 9100
ADD ./target/oauth2-0.0.1-SNAPSHOT.jar oauth2-service.jar
ENTRYPOINT ["java", "-jar", "/oauth2-service.jar"]