FROM openjdk:11-jdk

RUN git clone -b deploy https://github.com/Ottug-i/Curry_Back.git

WORKDIR /Curry_Back

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]