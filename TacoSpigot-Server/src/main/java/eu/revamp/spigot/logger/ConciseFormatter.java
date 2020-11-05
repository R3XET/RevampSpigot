package eu.revamp.spigot.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class ConciseFormatter extends Formatter {
    private final DateFormat date = new SimpleDateFormat("HH:mm:ss");

    private final boolean coloured;

    public ConciseFormatter(boolean coloured) {
        this.coloured = coloured;
    }

    public String format(LogRecord record) {
        StringBuilder formatted = new StringBuilder();
        formatted.append(this.date.format(record.getMillis()));
        formatted.append(" [");
        appendLevel(formatted, record.getLevel());
        formatted.append("] ");
        formatted.append(formatMessage(record));
        formatted.append('\n');
        if (record.getThrown() != null) {
            StringWriter writer = new StringWriter();
            record.getThrown().printStackTrace(new PrintWriter(writer));
            formatted.append(writer);
        }
        return formatted.toString();
    }

    private void appendLevel(StringBuilder builder, Level level) {
        String color;
        if (!this.coloured) {
            builder.append(level.getLocalizedName());
            return;
        }
        if (level == Level.INFO) {
            color = "\033[34m";
        } else if (level == Level.WARNING) {
            color = "\033[33m";
        } else if (level == Level.SEVERE) {
            color = "\033[31m";
        } else {
            color = "\033[36m";
        }
        builder.append(color).append(level.getLocalizedName()).append("\033[0m");
    }
}
