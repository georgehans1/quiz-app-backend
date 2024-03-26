FROM openjdk:17-ea-10-alpine
#ENV DATABASE_URL=
#ENV DATABASE_USERNAME=
#ENV DATABASE_PASSWORD=
WORKDIR /quiz-app-backend

COPY build/libs/*.jar app.jar
EXPOSE 8080

#ENTRYPOINT ["sleep","100"]

ENTRYPOINT ["java","-jar", "app.jar"]
