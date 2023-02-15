FROM openjdk:18-slim as tempLayer

LABEL maintainer="AlexCh <AlexCh_work@gmail.com>"

ENV DIRPATH=/app

WORKDIR ${DIRPATH}

ARG JAR_FILE

COPY ${JAR_FILE} app.jar

#Extracting jar by layertools (.jar file should be layered jar)
# See https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/#packaging-executable.configuring.layered-archives
# java -Djarmode=layertools -jar app.jar list
# dependencies
# spring-boot-loader
# snapshot-dependencies
# application
RUN java -Djarmode=layertools -jar app.jar extract

FROM openjdk:18-slim

VOLUME /tmp

ENV DIRPATH=/app

WORKDIR ${DIRPATH}

COPY --from=tempLayer ${DIRPATH}/dependencies ./
COPY --from=tempLayer ${DIRPATH}/spring-boot-loader ./
COPY --from=tempLayer ${DIRPATH}/snapshot-dependencies ./
COPY --from=tempLayer ${DIRPATH}/application ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]

