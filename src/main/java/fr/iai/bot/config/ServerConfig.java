package fr.iai.bot.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

@Configuration
public class ServerConfig {
    @Value("${token}") private String token;

    private static Logger logger = Logger.getLogger(ServerConfig.class);

    @Bean
    public IDiscordClient client() {
        ClientBuilder clientBuilder = new ClientBuilder();
        clientBuilder.withToken(token);
        try {
            return clientBuilder.login();
        } catch (DiscordException e) {
            logger.error("Impossible de se connecter à Discord", e);
            return null;
        }
    }


}
