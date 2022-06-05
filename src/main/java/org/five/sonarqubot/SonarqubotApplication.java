package org.five.sonarqubot;


import org.five.sonarqubot.events.WebClientServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SonarqubotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SonarqubotApplication.class, args);

       WebClientServiceImpl webClientServiceImpl  = new WebClientServiceImpl();
        webClientServiceImpl.createToken().subscribe(project -> System.out.println(project));
       // .map(tokenResponse -> tokenResponse.getToken()
    }
}