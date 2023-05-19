FROM openjdk:17-jdk-slim-buster AS builder

RUN apt-get update -y
RUN apt-get install -y binutils

WORKDIR /app

COPY . .

RUN ./gradlew build -i --stacktrace
RUN ./gradlew jlink -i --stacktrace

# lightweight image
FROM debian:stretch-slim

COPY --from=builder /app/app/build/image /app

ENTRYPOINT /app/bin/app