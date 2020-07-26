FROM maven:3.6.3-openjdk-8 AS build
COPY src /usr/src/app/src  
COPY pom.xml /usr/src/app  
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

FROM openjdk:8-jdk-alpine AS production
COPY --from=build /usr/src/app/target/sso-0.0.1-SNAPSHOT.jar sso-0.0.1-SNAPSHOT.jar  
EXPOSE 8081  
ENTRYPOINT ["java","-jar","sso-0.0.1-SNAPSHOT.jar"]  
