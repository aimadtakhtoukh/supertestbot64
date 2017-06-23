package fr.iai.bot.config;

import fr.iai.bot.listeners.annotation.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;

@Component
public class EventDispatcherConfig {
    private final IDiscordClient client;
    private final ApplicationContext applicationContext;

    @Autowired
    public EventDispatcherConfig(IDiscordClient client, ApplicationContext applicationContext) {
        this.client = client;
        this.applicationContext = applicationContext;
    }

    @Bean
    public EventDispatcher eventDispatcher() {
        EventDispatcher eventDispatcher = client.getDispatcher();
        applicationContext.getBeansWithAnnotation(Listener.class).values()
                .forEach(eventDispatcher::registerListener);
        return eventDispatcher;
    }
}
