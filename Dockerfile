# define base docker image
#FROM adoptopenjdk/openjdk11
#FROM mcr.microsoft.com/java/jdk:11-zulu-windowsservercore
FROM openjdk:8-jdk-alpine
RUN apk update
RUN apk add \
    tesseract-ocr \
    ghostscript
LABEL maintainer="Vipul"
ARG JAR_FILE=build/libs/ocr-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} document-ocr.jar
ENTRYPOINT ["java","-jar","/document-ocr.jar"]