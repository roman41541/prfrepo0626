FROM gradle:8.9-jdk17 AS build
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src
# RUN gradle gatlingClasses --no-daemon -q

FROM gradle:8.9-jdk17
WORKDIR /app
COPY --from=build /root/.gradle /root/.gradle
COPY --from=build /app /app
# отчёты останутся в volume
# VOLUME ["/app/build/reports/gatling"]
CMD ["gradle", "gatlingRun", "--simulation", "com.perftask.gatling.KafkaLoadSimulation", "--no-daemon"]


# FROM gradle:8.9-jdk17 AS build

# USER root
# RUN apt-get update && apt-get install -y ca-certificates curl && update-ca-certificates
# USER gradle

# WORKDIR /app

# ENV GRADLE_OPTS="\
# -Dhttps.protocols=TLSv1.2,TLSv1.3 \
# -Djava.net.preferIPv4Stack=true \
# -Dorg.gradle.internal.http.connectionTimeout=120000 \
# -Dorg.gradle.internal.http.socketTimeout=120000 \
# -Dorg.gradle.internal.repository.max.retries=10"

# COPY build.gradle.kts settings.gradle.kts ./
# COPY src ./src

# RUN gradle gatlingClasses --no-daemon --refresh-dependencies

# FROM gradle:8.9-jdk17

# WORKDIR /app

# COPY --from=build /app /app

# VOLUME ["/app/build/reports/gatling"]

# CMD ["gradle", "gatlingRun-com.perftask.gatling.KafkaLoadSimulation", "--no-daemon"]