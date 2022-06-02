package org.five.sonarqubot.events;

import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class HandleMessageCreateEvent implements EventListener<MessageCreateEvent> {

    // private final ScannerService scannerService;
    private final MessageService messageService;
    private final FileService fileService;

    public HandleMessageCreateEvent(MessageService messageService, FileService fileService) {
        //this.scannerService = scannerService;
        this.messageService = messageService;
        this.fileService = fileService;
    }

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> handle(MessageCreateEvent event) {
        return messageService.onlyCodeMessages(event.getMessage()).flatMap(messageService::getCode).flatMap((String code) -> fileService.createFile(code, "code_")).then();
        //1. Hämta meddelande med kod -> koden
        //2. Skapa fil med kod        -> filnamn
        //3. Skapa projekt            -> project key (eller den infon man behöver för att starta scan)
        //4. Starta scan              -> void
        //5. Hämta svar från sonarqube server -> svaret
        //6. Bot skickar svar         -> void
    }
}