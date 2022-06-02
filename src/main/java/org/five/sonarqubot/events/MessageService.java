package org.five.sonarqubot.events;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public interface MessageService {
    public Mono<Message> onlyCodeMessages(Message eventMessage);

    public Mono<String> getCode(Message message);
}
