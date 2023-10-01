FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY build/libs/rinha-de-backend-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=production

CMD ["java", "-jar", "app.jar"]
#CMD ["tail", "-f", "/dev/null"]





