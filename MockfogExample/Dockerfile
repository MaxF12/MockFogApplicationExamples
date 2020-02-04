FROM openjdk:8-jre-alpine

RUN adduser -D -g '' app && mkdir /app && chown -R app /app
USER app
COPY --chown=app ./target/MockfogExample-1.0-SNAPSHOT.jar /app/exampleapp.jar
COPY --chown=app config.yaml /app/config.yaml

WORKDIR /app

ENTRYPOINT ["java", "-server", "-XX:MaxRAMPercentage=75.00", "-jar", "exampleapp.jar"]
