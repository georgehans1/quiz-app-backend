#FROM openjdk:17-ea-10-alpine
##ENV DATABASE_URL=
##ENV DATABASE_USERNAME=
##ENV DATABASE_PASSWORD=
#WORKDIR /quiz-app-backend
#
#COPY build/libs/*.jar app.jar
#EXPOSE 8080
#
##ENTRYPOINT ["sleep","100"]
#
#ENTRYPOINT ["java","-jar", "app.jar"]

FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-21-jdk -y
COPY . .
RUN chmod +x gradlew
RUN ./gradlew bootJar --no-daemon

FROM openjdk:21-slim

EXPOSE 8080

COPY --from=build /build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]