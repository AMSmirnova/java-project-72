FROM gradle:7.4.0-jdk17

WORKDIR /app

COPY /app .

RUN gradle installDist

CMD java -jar build/libs/app-1.0-SNAPSHOT-all.jar