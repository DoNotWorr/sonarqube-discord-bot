package org.five.sonarqubot;


import org.five.sonarqubot.events.WebClientServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class SonarqubotApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(SonarqubotApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SonarqubotApplication.class, args);

        WebClientServiceImpl webClientServiceImpl = new WebClientServiceImpl();
        webClientServiceImpl.createToken().block();
        webClientServiceImpl.createProject().block();


    }
}