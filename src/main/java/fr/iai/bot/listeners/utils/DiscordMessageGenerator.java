package fr.iai.bot.listeners.utils;

import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.MessageBuilder;

public class DiscordMessageGenerator {

    public static void sendMessage(IDiscordClient client, IChannel channel, String message) {
        new MessageBuilder(client)
                .withChannel(channel)
                .withContent(message)
                .build();
    }

}
