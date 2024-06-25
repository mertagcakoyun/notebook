FROM openjdk:17-jdk-slim
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src
RUN ./gradlew build
ENTRYPOINT ["java", "-jar", "build/libs/notebook-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080