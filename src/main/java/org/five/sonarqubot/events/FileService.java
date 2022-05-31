package org.five.sonarqubot.events;

import reactor.core.publisher.Mono;

public interface FileService {
    public Mono<String> createFile(String fileName, String content);
}
