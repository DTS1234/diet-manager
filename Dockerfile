FROM java:8

VOLUME /tmp

EXPOSE 8080

ADD /build/libs/diet-manager-0.0.1-SNAPSHOT.jar diet-manager-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "diet-manager-0.0.1-SNAPSHOT.jar"]
