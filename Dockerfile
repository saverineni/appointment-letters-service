FROM openjdk:8u151-jdk-alpine3.7

ENV APP_USER java

RUN adduser -S $APP_USER

RUN mkdir -p /usr/local/apps/appointment-letters

# Add the service itself
ARG JAR_FILE
ADD ${JAR_FILE} /usr/local/apps/appointment-letters/app.jar
RUN sh -c 'touch /usr/local/apps/appointment-letters/app.jar'

RUN mkdir -p /var/log && chown $APP_USER /var/log

USER $APP_USER
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/usr/local/apps/appointment-letters/app.jar"]
EXPOSE 8000
