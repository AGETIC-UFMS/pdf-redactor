FROM gradle:8.4-jdk21-alpine AS build
WORKDIR /home/gradle/src

COPY build.gradle settings.gradle ./
RUN gradle dependencies --no-daemon

COPY src ./src
RUN gradle bootJar --no-daemon

FROM eclipse-temurin:21-jre-alpine

ARG VERSION_TAG

LABEL org.opencontainers.image.title="PDF-Redactor-Service"
LABEL org.opencontainers.image.description="A service for redacting sensitive information from PDF documents."
LABEL org.opencontainers.image.source="https://github.com/AGETIC-UFMS/pdf-redactor"
LABEL org.opencontainers.image.licenses="AGPL-3.0"
LABEL org.opencontainers.image.version="$VERSION_TAG"

WORKDIR /app

COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
