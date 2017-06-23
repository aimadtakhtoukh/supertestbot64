package fr.iai.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sx.blah.discord.Discord4J;

@SpringBootApplication
public class DicerollbotApplication {

	public static void main(String[] args) {
        Discord4J.disableChannelWarnings();
		SpringApplication.run(DicerollbotApplication.class, args);
	}
}
