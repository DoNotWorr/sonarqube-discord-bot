package org.five.sonarqubot.scanner;

import reactor.core.publisher.Mono;

public interface SonarScanner {
    Mono<Void> scan(String projectKey, String projectToken, String fileName);
}