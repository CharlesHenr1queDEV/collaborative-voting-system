FROM openjdk:17-oracle
MAINTAINER charlesdev
COPY target/collaborative-voting-system-0.0.1-SNAPSHOT.jar collaborative-voting-system-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/collaborative-voting-system-0.0.1-SNAPSHOT.jar"]
