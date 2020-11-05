package eu.revamp.spigot.utils.generic;

import com.google.common.base.CharMatcher;

import java.util.regex.Pattern;

public class JavaUtils {
    private static Pattern UUID_PATTERN = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[34][0-9a-fA-F]{3}-[89ab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}");
    private static CharMatcher CHAR_MATCHER_ASCII = CharMatcher.inRange('0', '9').or(CharMatcher.inRange('a', 'z')).or(CharMatcher.inRange('A', 'Z')).or(CharMatcher.WHITESPACE).precomputed();

    public static boolean isUUID(String string) {
        return JavaUtils.UUID_PATTERN.matcher(string).find();
    }

    public static boolean isAlphanumeric(String string) {
        return JavaUtils.CHAR_MATCHER_ASCII.matchesAllOf(string);
    }
}
