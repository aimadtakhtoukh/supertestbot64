package fr.iai.bot.listeners;

import fr.iai.bot.listeners.annotation.Listener;
import fr.iai.bot.listeners.utils.DiscordMessageGenerator;
import org.apache.log4j.Logger;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.guild.channel.message.MentionEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Listener
public class RollDiceListener {
    private static Logger logger = Logger.getLogger(RollDiceListener.class);
    private Pattern dicePattern = Pattern.compile("(?<number>[0-9]+)d(?<faces>[0-9]+)");

    @EventSubscriber
    public void onMentionEvent(MentionEvent event) {
        IMessage message = event.getMessage();
        IChannel channel = message.getChannel();
        try {
            String content = message.getContent().replaceAll("<@.*?> ", "");
            Matcher diceMatcher = dicePattern.matcher(content);
            if (diceMatcher.matches()) {
                int number = Integer.parseInt(diceMatcher.group("number"));
                int faces = Integer.parseInt(diceMatcher.group("faces"));
                DiscordMessageGenerator.sendMessage(
                        event.getClient(),
                        channel,
                        explainResults(randomResults(number, faces))
                );
            } else {
                DiscordMessageGenerator.sendMessage(
                        event.getClient(), channel,
                        "Il y a un problème avec l'entrée.");
            }
        } catch (DiscordException | MissingPermissionsException | RateLimitException | NumberFormatException e) {
            logger.error(e);
            DiscordMessageGenerator.sendMessage(event.getClient(), channel, e.toString());
        }
        logger.info(event.getMessage());
    }

    private int randomResult(int faces) {
        return (int) (Math.random() * faces) + 1;
    }

    private Map<Integer, Long> randomResults(int number, int faces) {
        return Arrays.asList(new Integer[number]).parallelStream()
                .map(c -> randomResult(faces))
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));
    }

    private String explainResults(Map<Integer, Long> results) {
        return asSortedList(results.keySet()).stream()
                .map(entry -> results.get(entry) + " pour " + entry)
                .collect(Collectors.joining(",\n"));
    }

    private static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
        List<T> list = new ArrayList<>(c);
        java.util.Collections.sort(list);
        return list;
    }
}
