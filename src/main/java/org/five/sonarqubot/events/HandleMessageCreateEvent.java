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

    public HandleMessageCreateEvent(SonarScanner sonarScanner, MessageService messageService, FileService fileService) {
        this.sonarScanner = sonarScanner;
        this.messageService = messageService;
        this.fileService = fileService;
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
                .flatMap(fileName -> sonarScanner.scan("key", "a8d983ea21012b881ae8123d0db6938b04205903", fileName) //todo replace hardcoded projectKey, projectToken
                        .onErrorResume(throwable -> event.getMessage()
                                .getChannel()
                                .flatMap(messageChannel -> messageChannel.createMessage("I couldn't analyze your message."))
                                .then()));
    }
}