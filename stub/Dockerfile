FROM gradle:8.9-jdk17 AS build
WORKDIR /app
COPY build.gradle.kts settings.gradle.kts ./
COPY src ./src
RUN gradle bootJar --no-daemon -q

FROM eclipse-temurin:17-jre


RUN apt-get update \
 && apt-get install -y wget \
 && rm -rf /var/lib/apt/lists/* \
 && mkdir -p /dumps

 
WORKDIR /app
COPY --from=build /app/build/libs/perf-stub.jar app.jar
EXPOSE 8080
VOLUME ["/dumps"]
ENTRYPOINT ["java", \
  "-Xmx750m", \
  "-XX:+HeapDumpOnOutOfMemoryError", \
  "-XX:HeapDumpPath=/dumps/heap-dump.hprof", \
  "-jar", "app.jar"]
