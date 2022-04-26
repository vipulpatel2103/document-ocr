# define base docker image
# FROM openjdk:11
FROM mcr.microsoft.com/java/jdk:11-zulu-windowsservercore
LABEL maintainer="Vipul"
ARG JAR_FILE=build/libs/ocr-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} document-ocr.jar
ENTRYPOINT ["java","-jar","/document-ocr.jar"]