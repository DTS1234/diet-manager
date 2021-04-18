FROM hypriot/rpi-java

ADD diet-manager-0.0.1-SNAPSHOT.jar /opt/diet-manager-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/diet-manager-0.0.1-SNAPSHOT.jar"]
