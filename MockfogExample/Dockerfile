FROM openjdk:8-jre-alpine

RUN adduser -D -g '' app && mkdir /app && chown -R app /app
USER app
COPY --chown=app ./target/Heartrate-1.0-jar-with-dependencies.jar /app/heartrate.jar
COPY --chown=app config.yaml /app/config.yaml

WORKDIR /app

ENTRYPOINT ["java", "-server", "-XX:MaxRAMPercentage=75.00", "-jar", "heartrate.jar"]
