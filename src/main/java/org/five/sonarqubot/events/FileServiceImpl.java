package org.five.sonarqubot.events;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class FileServiceImpl implements FileService {
    @Override
    public Mono<String> createFile(String fileName, String content) {
        return Mono.just("C:\\Users\\alexa\\IdeaProjects\\examensarbete\\sonarqubot\\files-to-scan\\ExampleFile.java");
    }
}
