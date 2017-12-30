package fr.iai.bot.listeners;

import fr.iai.bot.listeners.annotation.Listener;
import org.apache.log4j.Logger;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MentionEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.*;

//@Listener
public class RepeaterListener {
    private static Logger logger = Logger.getLogger(RepeaterListener.class);

    @EventSubscriber
    public void onMentionEvent(MentionEvent event) {
        IMessage message = event.getMessage();
        IChannel channel = message.getChannel();
        if (!message.getAuthor().equals(event.getClient().getOurUser())) {
            try {
                new MessageBuilder(event.getClient())
                        .withChannel(channel)
                        .withContent(message.getAuthor().getDisplayName(message.getGuild()) + " m'a dit : '" + message.getContent() + "'")
                        .build();
            } catch (DiscordException | RateLimitException | MissingPermissionsException e) {
                logger.error(e);
            }
            logger.info(event.getMessage());
        }
    }


}
