package org.five.sonarqubot.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class ProjectWebClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectWebClient.class);

    WebClient client = WebClient.create("http://localhost:8080");


    public void consume() {

        Mono<String> helloMono = client.get()
                .uri("/")
                .retrieve()
                .bodyToMono(String.class);

        helloMono.subscribe(hello -> LOGGER.info("This should be hello world: {}", hello));

        Flux<Object> getAllProjects = client.get()
                .uri("/search")
                .retrieve()
                .bodyToFlux(Object.class);

        getAllProjects.subscribe(project -> LOGGER.info("All existing projects: {}", project));


//Project newProject= new Project("hej","hej","public");
//        Mono<Object> createProject = client.post()
//                .uri(builder -> builder.path("/create")
//                        .build()).bodyValue(newProject)
//                .retrieve()
//                .bodyToMono(Object.class);
//
//        createProject.subscribe(project -> LOGGER.info("Project: {}", project));

        Flux<Object> projectAnalysis = client.get()
                .uri(builder -> builder.path("/analysis")
                        .queryParam("project", "First")
                        .build())
                .retrieve()
                .bodyToFlux(Object.class);

        projectAnalysis.subscribe(project -> LOGGER.info("Project analysis result: {}", project));
    }


}