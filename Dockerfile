### jar ###
FROM maven:3.8.1-jdk-11 AS MAVEN_BUILD
COPY pom.xml .
COPY src src
RUN mvn package

## container ##
FROM openjdk:11
#non-root user
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring:spring

COPY --from=MAVEN_BUILD /target/sonarqubot-0.0.1-SNAPSHOT.jar sonarqubot.jar
ENTRYPOINT ["java", "-jar", "/sonarqubot.jar"]