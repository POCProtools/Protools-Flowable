FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ADD target/flowableDemo-0.0.1-SNAPSHOT.jar flowableDemo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/flowableDemo-0.0.1-SNAPSHOT.jar"]
