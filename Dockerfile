## container ##
FROM openjdk:17-alpine
#non-root user
RUN addgroup --system spring && adduser --system --ingroup spring spring
USER spring:spring

COPY /target/sonarqubot-0.0.1-SNAPSHOT.jar sonarqubot.jar
ENTRYPOINT ["java", "-jar", "/sonarqubot.jar"]