package org.five.sonarqubot.events;

import discord4j.core.event.domain.message.MessageCreateEvent;
import org.five.sonarqubot.scanner.SonarScanner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class HandleMessageCreateEvent implements EventListener<MessageCreateEvent> {
    private final SonarScanner sonarScanner;
    private final MessageService messageService;
    private final FileService fileService;
    private final WebClientService webClientService;

    public HandleMessageCreateEvent(SonarScanner sonarScanner, MessageService messageService, FileService fileService, WebClientService webClientService) {
        this.sonarScanner = sonarScanner;
        this.messageService = messageService;
        this.fileService = fileService;
        this.webClientService = webClientService;
    }

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> handle(MessageCreateEvent event) {
        return messageService.onlyCodeMessages(event.getMessage())
                .flatMap(messageService::getCode)
                .flatMap(fileService::createFile)
                .flatMap(fileName -> webClientService.createToken()
                        .flatMap(token -> webClientService.createProject()
                                .flatMap(key -> sonarScanner.scan(key, token, fileName))
                                .onErrorResume(throwable -> event.getMessage()
                                        .getChannel()
                                        .flatMap(messageChannel -> messageChannel.createMessage("I couldn't analyze your message."))
                                        .then()))
                );
    }
}