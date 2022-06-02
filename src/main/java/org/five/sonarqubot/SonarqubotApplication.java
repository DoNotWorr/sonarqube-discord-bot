package org.five.sonarqubot;

import org.five.sonarqubot.client.ProjectWebClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SonarqubotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SonarqubotApplication.class, args);

        ProjectWebClient projectWebClient = new ProjectWebClient();
        projectWebClient.consume();
    }
}