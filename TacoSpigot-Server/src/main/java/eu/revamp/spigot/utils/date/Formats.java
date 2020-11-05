package eu.revamp.spigot.utils.date;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.time.FastDateFormat;

import java.text.DecimalFormat;
import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicBoolean;

public class Formats
{
    private static AtomicBoolean loaded = new AtomicBoolean(false);
    public static FastDateFormat DAY_MTH_HR_MIN_SECS;
    public static FastDateFormat DAY_MTH_YR_HR_MIN_AMPM;
    public static FastDateFormat DAY_MTH_HR_MIN_AMPM;
    public static FastDateFormat HR_MIN_AMPM;
    public static FastDateFormat MNT_DAY_HR_MIN_AMPH;
    public static FastDateFormat HR_MIN_AMPM_TIMEZONE;
    public static FastDateFormat HR_MIN;
    public static FastDateFormat KOTH_FORMAT;
    public static ThreadLocal<DecimalFormat> SECONDS = ThreadLocal.withInitial(() -> new DecimalFormat("0"));
    public static ThreadLocal<DecimalFormat> REMAINING_SECONDS = ThreadLocal.withInitial(() -> new DecimalFormat("0.#"));
    public static ThreadLocal<DecimalFormat> REMAINING_SECONDS_TRAILING = ThreadLocal.withInitial(() -> new DecimalFormat("0.0"));
    public static TimeZone SERVER_TIME_ZONE = TimeZone.getTimeZone("EST");
    public static ZoneId SERVER_ZONE_ID = Formats.SERVER_TIME_ZONE.toZoneId();

    public static void reload(TimeZone timeZone) throws IllegalStateException {
        Preconditions.checkArgument(!Formats.loaded.getAndSet(true), "Already loaded");
        Formats.DAY_MTH_HR_MIN_SECS = FastDateFormat.getInstance("dd/MM HH:mm:ss", timeZone, Locale.US);
        Formats.MNT_DAY_HR_MIN_AMPH = FastDateFormat.getInstance("MM/dd HH:mm:ss", timeZone, Locale.US);
        Formats.DAY_MTH_YR_HR_MIN_AMPM = FastDateFormat.getInstance("dd/MM hh:mma", timeZone, Locale.US);
        Formats.DAY_MTH_HR_MIN_AMPM = FastDateFormat.getInstance("dd/MM hh:mma", timeZone, Locale.US);
        Formats.HR_MIN_AMPM = FastDateFormat.getInstance("hh:mma", timeZone, Locale.US);
        Formats.HR_MIN_AMPM_TIMEZONE = FastDateFormat.getInstance("hh:mma z", timeZone, Locale.US);
        Formats.HR_MIN = FastDateFormat.getInstance("hh:mm", timeZone, Locale.US);
        Formats.KOTH_FORMAT = FastDateFormat.getInstance("m:ss", timeZone, Locale.US);
    }
}

