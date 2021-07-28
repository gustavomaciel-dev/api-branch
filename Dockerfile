FROM openjdk:8-alpine
ADD target/api-branch.jar /usr/share/app.jar
ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/app.jar"]