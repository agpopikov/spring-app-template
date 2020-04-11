FROM adoptopenjdk/openjdk8:alpine

ADD docker-entrypoint.sh /docker-entrypoint.sh
ADD app.jar /app.jar

ENTRYPOINT ["./docker-entrypoint.sh"]
