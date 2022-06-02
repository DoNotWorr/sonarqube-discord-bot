package org.five.sonarqubot.client;

import org.five.sonarqubot.client.Project;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProjectClient {


    public WebClient webClient= WebClient.create("http://localhost:8080");

    public Mono<List<Object>> getProjects()
    {
        return this.webClient.get()
                .uri("/search")

                .retrieve().bodyToFlux(Object.class).collectList();
    }
}
