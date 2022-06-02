package org.five.sonarqubot.events;

import reactor.core.publisher.Mono;


public interface FileService {

    void createDirectory();

    Mono<String> createFile(String message);
}
