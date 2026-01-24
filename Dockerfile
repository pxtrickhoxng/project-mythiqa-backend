FROM maven:3.9-eclipse-temurin-24 AS builder
WORKDIR /app

COPY pom.xml ./
RUN mvn -q -e -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -q -e -DskipTests package

FROM eclipse-temurin:24-jre
WORKDIR /app

RUN useradd -r -u 1001 spring && mkdir -p /app && chown -R spring:spring /app
USER spring

COPY --from=builder /app/target/*-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]
