package eu.revamp.spigot.logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingOutputStream extends ByteArrayOutputStream {
    private static final String separator = System.getProperty("line.separator");

    private final Logger logger;

    private final Level level;

    public LoggingOutputStream(Logger logger, Level level) {
        this.logger = logger;
        this.level = level;
    }

    public LoggingOutputStream(int size, Logger logger, Level level) {
        super(size);
        this.logger = logger;
        this.level = level;
    }

    public void flush() throws IOException {
        String contents = toString("utf-8");
        reset();
        if (!contents.isEmpty() && !contents.equals(separator))
            this.logger.logp(this.level, "", "", contents);
    }
}

