package org.five.sonarqubot.events;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public interface MessageService {
    Mono<Message> onlyCodeMessages(Message eventMessage);

    Mono<String> getCode(Message message);
}
