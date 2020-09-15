# bower
FROM node:13 AS asseble-js

RUN npm install --global bower@1.8.2

COPY --chown=node:node ./bower.json /home/bower/
WORKDIR /home/bower

USER node
RUN bower install --force-latest

# the stage of our build will use a gradle
FROM gradle:4.1.0-jdk8-alpine AS build

# copy src code to the container
COPY --chown=gradle:gradle . /home/gradle/src
COPY --from=asseble-js --chown=gradle:gradle /home/bower/bower_components /home/gradle/src/main/resources/static/bower_components

WORKDIR /home/gradle/src

# package our application code
RUN gradle clean build -x test --no-daemon 

# the second stage of our build will use open jdk 8
FROM openjdk:8-jre-slim

EXPOSE 8080

RUN mkdir /app

# copy only the artifacts we need from the first stage and discard the rest
COPY --from=build /home/gradle/src/build/libs/*.jar /app/hanbaikanri.jar

# set the startup command to execute the jar
ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Dspring.profiles.active=production","-jar","/app/hanbaikanri.jar"]
