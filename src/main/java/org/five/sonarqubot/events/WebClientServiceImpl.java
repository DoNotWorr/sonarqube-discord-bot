package org.five.sonarqubot.events;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@PropertySource("classpath:secret.properties")
public class WebClientServiceImpl implements WebClientService {

    private final String uuid = String.valueOf(UUID.randomUUID());
    private final WebClient client;

    public WebClientServiceImpl(@Value("${sonar.user}") String user, @Value("${sonar.password}") String password, @Value("${sonar.api.url}") String sonarAPI) {
        this.client = WebClient.builder()
                .baseUrl(sonarAPI)
                .filter(ExchangeFilterFunctions
                        .basicAuthentication(user, password))
                .build();
    }

    @Override
    public Mono<String> createToken() {
        LinkedMultiValueMap<String, String> tokenData = new LinkedMultiValueMap<>();
        tokenData.add("name", uuid);

        return client.post()
                .uri(uriBuilder -> uriBuilder.path("/user_tokens/generate")
                        .build())
                .bodyValue(tokenData)
                .retrieve()
                .bodyToMono(Object.class)
                .map(result -> result.toString()
                        .split(",")[2].split("=")[1]);

    }

    @Override
    public Mono<String> createProject() {
        LinkedMultiValueMap<String, String> projectData = new LinkedMultiValueMap<>();
        projectData.add("name", uuid);
        projectData.add("project", uuid);

        return client.post()
                .uri("/projects/create")
                .bodyValue(projectData)
                .retrieve()
                .bodyToMono(Object.class)
                .map(result -> result.toString()
                        .split(",")[1].split("=")[1]);

    }

    @Override
    public Mono<String> projectAnalysis() {
        return client.get()
                .uri(builder -> builder.path("/measures/component")
                        .queryParam("component", createProject().toString())
                        .queryParam("metricKeys", "code_smells", "bugs", "duplicated_lines")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .log();

    }
}