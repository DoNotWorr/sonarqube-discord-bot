package org.five.sonarqubot.events;

import org.five.sonarqubot.client.ProjectResponse;
import org.five.sonarqubot.client.TokenResponse;
import reactor.core.publisher.Mono;

public interface WebClientService {

    Mono<TokenResponse> createToken();

    Mono<ProjectResponse> createProject();

    Mono<Analysis> projectAnalysis();

}
