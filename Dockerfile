FROM openjdk:8

COPY ./target/*.jar application.jar
COPY ./scripts/entry-point.sh entry-point.sh

CMD [ "chmod", "+x", "entry-point.sh"]

EXPOSE 8080

ENTRYPOINT [ "/bin/bash", "entry-point.sh" ]