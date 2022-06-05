package org.five.sonarqubot.events;


import reactor.core.publisher.Mono;

public interface WebClientService {

    Mono<String> createToken();

    Mono<String> createProject();

    Mono<String> projectAnalysis();

}
