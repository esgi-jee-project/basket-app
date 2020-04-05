FROM openjdk:8-alpine

USER root

COPY target/basket-0.0.1-SNAPSHOT.jar /usr/src/basket-0.0.1-SNAPSHOT.jar

RUN chgrp 0  /usr/src/basket-0.0.1-SNAPSHOT.jar && \
    chmod +x  /usr/src/basket-0.0.1-SNAPSHOT.jar && \
    chmod g=u /usr/src/basket-0.0.1-SNAPSHOT.jar

EXPOSE 8080

USER 1001

CMD ["java", "-jar", "/usr/src/basket-0.0.1-SNAPSHOT.jar"]