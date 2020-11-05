package eu.revamp.spigot.utils.generic;

public class ConversionUtils {

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static boolean isLong(String s) {
        try {
            Long.parseLong(s);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static boolean isBoolean(String s) {
        try {
            Boolean.parseBoolean(s);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
}
