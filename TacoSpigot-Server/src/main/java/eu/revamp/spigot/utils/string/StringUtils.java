package eu.revamp.spigot.utils.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringUtils {

    public static String stringFromList(List<String> source) {
        return Arrays.toString(source.toArray()).replace("[", "").replace("]", "");
    }

    public static List<String> stringToList(String source) {
        String[] splitted = source.split(", ");
        return Arrays.stream(splitted).collect(Collectors.toList());
    }

    public static String firstUpperCase(String source) {
        return source.substring(0, 1).toUpperCase() + source.substring(1).toLowerCase();
    }
    public static String getStringFromList(List<String> source) {
        if (source.size() == 0) {
            return "Empty";
        }
        StringBuilder builder = new StringBuilder();
        int i = 0;
        for (String piece : source) {
            builder.append(piece.replace(",", "#%&$"));
            if (i < source.size() - 1) {
                builder.append(", ");
            }
            i++;
        }
        return builder.toString();
    }

    public static List<String> getListFromString(String source) {
        if (source.equals("Empty")) return new ArrayList<>();

        return Arrays.stream(source.split(", ")).map(s -> s.replace("#%&$", ",")).collect(Collectors.toList());
    }

    public static String convertFirstUpperCase(String source) {
        return source.substring(0, 1).toUpperCase() + source.substring(1);
    }
}

