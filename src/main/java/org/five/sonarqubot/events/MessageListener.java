package org.five.sonarqubot.events;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class MessageListener {
    public static String ping = "!ping";
    public static String code = "```!code```";

    public Mono<Void> processCommand(Message eventMessage) {
        return Mono.just(eventMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase(ping) || message.getContent().equalsIgnoreCase(code))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Pongelipong! \n" +
                "Your wish is my command\n"
                ))
                .then();
    }
}