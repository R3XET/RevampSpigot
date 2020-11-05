package eu.revamp.spigot.utils.time;

import org.apache.commons.lang.time.DurationFormatUtils;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class TimeUtils {
    public static long convert(int value, char unit) {
        switch (unit) {

            case 'y': {
                return value * TimeUnit.DAYS.toMillis(365L);
            }

            case 'M': {
                return value * TimeUnit.DAYS.toMillis(30L);
            }

            case 'd': {
                return value * TimeUnit.DAYS.toMillis(1L);
            }

            case 'h': {
                return value * TimeUnit.HOURS.toMillis(1L);
            }

            case 'm': {
                return value * TimeUnit.MINUTES.toMillis(1L);
            }

            case 's': {
                return value * TimeUnit.SECONDS.toMillis(1L);
            }

            default: {
                return -1L;
            }
        }
    }

    public static long parse(String input) {
        if (input == null || input.isEmpty()) {
            return -1L;
        }

        long result = 0L;

        StringBuilder number = new StringBuilder();

        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);

            if (Character.isDigit(c)) {
                number.append(c);
            } else {
                String str;

                if (Character.isLetter(c) && !(str = number.toString()).isEmpty()) {
                    result += TimeUtils.convert(Integer.parseInt(str), c);
                    number = new StringBuilder();
                }
            }
        }

        return result;
    }

    public static String getRemaining(long millis, boolean milliseconds) {
        return getRemaining(millis, milliseconds, true);
    }

    public static String getRemaining(long duration, boolean milliseconds, boolean trail) {
        if (milliseconds && duration < TimeUnit.MINUTES.toMillis(1)) {
            return (trail ? remaining_seconds_trailing : remaining_seconds).get().format((double) duration * 0.001) + 's';
        }

        return DurationFormatUtils.formatDuration(duration, (duration >= TimeUnit.HOURS.toMillis(1) ? "HH:" : "") + "mm:ss");
    }


    public static String formatMilisecondsToSeconds(Long time) {
        float seconds = (time + 0.0f) / 1000.0f;
        return String.format("%1$.1f", seconds);
    }

    public static String formatMilisecondsToMinutes(Long time) {
        int seconds = (int)(time / 1000L % 60L);
        int minutes = (int)(time / 1000L / 60L);
        return String.format("%02d:%02d", minutes, seconds);
    }

    public static String formatSecondsToMinutes(int time) {
        int seconds = time % 60;
        int minutes = time / 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public static String formatSecondsToHours(int time) {
        int hours = time / 3600;
        int minutes = time % 3600 / 60;
        int seconds = time % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static String formatMinutes(int time) {
        int minutes = time / 60;
        return String.format("%02d", minutes);
    }

    public static String formatInt(int i) {
        int r = i * 1000;
        int sec = r / 1000 % 60;
        int min = r / 60000 % 60;
        int h = r / 3600000 % 24;

        return (h > 0 ? h + ":" : "") + (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);
    }

    public static Object getTime(int seconds) {
        if (seconds < 60) {
            return seconds + "seconds";
        }

        int minutes = seconds / 60;
        int s = 60 * minutes;
        int secondsLeft = seconds - s;

        if (minutes < 60) {
            if (secondsLeft > 0) {
                return minutes + "minutes " + secondsLeft + "seconds";
            }

            return minutes + "minutes";
        }

        if (minutes < 1440) {
            String time = "";

            int hours = minutes / 60;
            time = hours + "hours";
            int inMins = 60 * hours;
            int leftOver = minutes - inMins;

            if (leftOver >= 1) {
                time = time + " " + leftOver + "minutes";
            }

            if (secondsLeft > 0) {
                time = time + " " + secondsLeft + "seconds";
            }

            return time;
        }
        String time = "";
        int days = minutes / 1440;
        time = days + "days";
        int inMins = 1440 * days;
        int leftOver = minutes - inMins;

        if (leftOver >= 1) {
            if (leftOver < 60) {
                time = time + " " + leftOver + "minutes";
            } else {
                int hours = leftOver / 60;
                time = time + " " + hours + "hours";
                int hoursInMins = 60 * hours;
                int minsLeft = leftOver - hoursInMins;

                if (leftOver >= 1) {
                    time = time + " " + minsLeft + "minutes";
                }
            }
        }

        if (secondsLeft > 0) {
            time = time + " " + secondsLeft + "seconds";
        }

        return time;
    }

    public static String formatTime(int timer) {
        int hours = timer / 3600;
        int secondsLeft = timer - hours * 3600;
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String formattedTime = "";

        if (hours > 0) {
            if (hours < 10)
                formattedTime += "0";
            formattedTime += hours + ":";
        }

        if (minutes < 10)
            formattedTime += "0";
        formattedTime += minutes + ":";

        if (seconds < 10)
            formattedTime += "0";
        formattedTime += seconds;

        return formattedTime;
    }

    public static ThreadLocal<DecimalFormat> remaining_seconds = new ThreadLocal<DecimalFormat>() {
        protected DecimalFormat initialValue() {
            return new DecimalFormat("0.#");
        }
    };

    public static ThreadLocal<DecimalFormat> remaining_seconds_trailing = new ThreadLocal<DecimalFormat>() {
        protected DecimalFormat initialValue() {
            return new DecimalFormat("0.0");
        }
    };
}
