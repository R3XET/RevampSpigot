package eu.revamp.spigot.utils.chat.color;

import eu.revamp.spigot.utils.string.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Color;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CCUtils {

    public static Map<ChatColor, String> colorMap = new HashMap<>();

    static {
        colorMap.put(ChatColor.BLACK, "BLACK");
        colorMap.put(ChatColor.DARK_BLUE, "DARK_BLUE");
        colorMap.put(ChatColor.DARK_GREEN, "DARK_GREEN");
        colorMap.put(ChatColor.DARK_AQUA, "DARK_AQUA");
        colorMap.put(ChatColor.DARK_RED, "DARK_RED");
        colorMap.put(ChatColor.DARK_PURPLE, "DARK_PURPLE");
        colorMap.put(ChatColor.GOLD, "GOLD");
        colorMap.put(ChatColor.GRAY, "GRAY");
        colorMap.put(ChatColor.DARK_GRAY, "DARK_GRAY");
        colorMap.put(ChatColor.BLUE, "BLUE");
        colorMap.put(ChatColor.GREEN, "GREEN");
        colorMap.put(ChatColor.AQUA, "AQUA");
        colorMap.put(ChatColor.RED, "RED");
        colorMap.put(ChatColor.LIGHT_PURPLE, "LIGHT_PURPLE");
        colorMap.put(ChatColor.YELLOW, "YELLOW");
        colorMap.put(ChatColor.WHITE, "WHITE");
        colorMap.put(ChatColor.RESET, "RESET");
        colorMap.put(ChatColor.ITALIC, "ITALIC");
        colorMap.put(ChatColor.UNDERLINE, "UNDERLINE");
        colorMap.put(ChatColor.STRIKETHROUGH, "STRIKETHROUGH");
        colorMap.put(ChatColor.MAGIC, "MAGIC");
        colorMap.put(ChatColor.BOLD, "BOLD");
    }

    public static String convertChatColor(ChatColor color) {
        return colorMap.get(color);
    }

    public static String convertChatColor(ChatColor color, boolean fixed) {
        if (!fixed) return convertChatColor(color);

        String name = convertChatColor(color).toLowerCase();
        if (!name.contains("_")) {
            return StringUtils.convertFirstUpperCase(name);
        }
        StringBuilder builder = new StringBuilder();
        String[] attributes = name.split("_");
        for (String attribute : attributes) {
            builder.append(StringUtils.convertFirstUpperCase(attribute));
            builder.append(" ");
        }
        builder.setLength(builder.length() - 1);
        return builder.toString();
    }

    public static String buildMessage(String[] args, int start) {
        if (start >= args.length) {
            return "";
        }
        return ChatColor.stripColor(String.join(" ", Arrays.copyOfRange(args, start, args.length)));
    }

    public static Color getBukkitColor(String n) {
        String name = n.toUpperCase();
        switch (name) {
            case "RED":
                return Color.RED;
            case "BLUE":
                return Color.BLUE;
            case "BLACK":
                return Color.BLACK;
            case "YELLOW":
                return Color.YELLOW;
            case "AQUA":
                return Color.AQUA;
            case "FUCHSIA":
                return Color.FUCHSIA;
            case "GRAY":
                return Color.GRAY;
            case "GREEN":
                return Color.GREEN;
            case "MAROON":
                return Color.MAROON;
            case "NAVY":
                return Color.NAVY;
            case "ORANGE":
                return Color.ORANGE;
            case "LIME":
                return Color.LIME;
            case "OLIVE":
                return Color.OLIVE;
            case "PURPLE":
                return Color.PURPLE;
            case "TEAL":
                return Color.TEAL;
            case "SILVER":
                return Color.SILVER;
        }
        return Color.WHITE;
    }
}
