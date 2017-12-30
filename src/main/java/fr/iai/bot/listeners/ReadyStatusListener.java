package fr.iai.bot.listeners;

import fr.iai.bot.listeners.annotation.Listener;
import org.apache.log4j.Logger;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

@Listener
public class ReadyStatusListener {
    private static Logger logger = Logger.getLogger(ReadyStatusListener.class);

    @EventSubscriber
    public void onReadyEvent(ReadyEvent event) throws RateLimitException, MissingPermissionsException {
        logger.info("Logged in as " + event.getClient().getOurUser().getName());
        event.getClient().getGuilds()
                .forEach(guild -> {
                            logger.info("Guilde : " + guild.getName() + ":" + guild.getLongID());
                            guild.getChannels().forEach(
                                    channel -> logger.info(
                                            "\t Channel : " + channel.getName() + " : " + channel.getLongID()
                                    )
                            );
                        }
                );
    }
}
