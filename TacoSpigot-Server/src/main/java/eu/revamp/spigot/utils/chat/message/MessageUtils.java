package eu.revamp.spigot.utils.chat.message;

import com.google.common.base.Preconditions;
import eu.revamp.spigot.utils.chat.color.CC;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MessageUtils {
    public static void sendMessage(String message) {
        Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(CC.translate(message)));
    }

    public static void sendMessage(String message, String permission) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.hasPermission(permission)) {
                player.sendMessage(CC.translate(message));
            }
        });
    }

    public static void sendMessageWithoutPlayer(Player player, String message) {
        Bukkit.getOnlinePlayers().forEach(online -> {
            if (online != player) {
                online.sendMessage(CC.translate(message));
            }
        });
    }

    public static void sendMessageWithoutPlayer(Player player, String message, String permission) {
        Bukkit.getOnlinePlayers().forEach(online -> {
            if (online != player && online.hasPermission(permission)) {
                online.sendMessage(CC.translate(message));
            }
        });
    }
    public static boolean endsWith(String word, String... suffix) {
        for (String message : suffix) {
            if (word.toLowerCase().endsWith(message)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(String word, String... suffix) {
        for (String message : suffix) {
            if (word.contains(message)) {
                return true;
            }
        }
        return false;
    }

    public static boolean equalsIgnoreCase(String word, String... suffix) {
        for (String message : suffix) {
            if (word.equalsIgnoreCase(message)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> formatMessage(List<String> messages) {
        List<String> buffered = new ArrayList<>();
        for (String message : messages) {
            buffered.add(CC.translate("&r" + message));
        }
        return buffered;
    }

    public static String[] formatMessage(String... messages) {
        return formatMessage(Arrays.asList(messages)).toArray(new String[0]);
    }

    public static void broadcastSender(String... value) {
        Preconditions.checkNotNull((Object)value, "Argument cannot be null");
        Bukkit.getConsoleSender().sendMessage(formatMessage(value));
    }

    public static void info(String... value) {
        Preconditions.checkNotNull((Object)value, "Argument cannot be null");
        Bukkit.getConsoleSender().sendMessage(formatMessage(value));
    }

    public static String capitalizeFullyWords(String string) {
        return WordUtils.capitalizeFully(string);
    }

    public static List<String> splitText(int length, String text, String linePrefix, String wordSuffix) {
        if (text.length() <= length) {
            return Collections.singletonList(linePrefix + text);
        }
        List<String> lines = new ArrayList<>();
        String[] split = text.split(" ");
        StringBuilder builder = new StringBuilder(linePrefix);
        for (int i = 0; i < split.length; ++i) {
            if (builder.length() + split[i].length() >= length) {
                lines.add(builder.toString());
                builder = new StringBuilder(linePrefix);
            }
            builder.append(split[i]).append(wordSuffix);
            if (i == split.length - 1) {
                builder.replace(builder.length() - wordSuffix.length(), builder.length(), "");
            }
        }
        if (builder.length() != 0) {
            lines.add(builder.toString());
        }
        return lines;
    }
}
