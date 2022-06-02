package org.five.sonarqubot.events;

import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MessageServiceImpl implements MessageService {
    @Override
    public Mono<Message> onlyCodeMessages(Message eventMessage) {
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().startsWith("```") && message.getContent().endsWith("```"));
    }

    @Override
    public Mono<String> getCode(Message message) {
        return Mono.just(message.getContent().split("```")[1].trim());
    }
}