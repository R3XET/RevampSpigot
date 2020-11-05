package eu.revamp.spigot.logger;

import jline.console.ConsoleReader;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public final class RevampLogger extends Logger {
    private final LogDispatcher dispatcher = new LogDispatcher(this);

    public RevampLogger(String loggerName, String filePattern, ConsoleReader reader) {
        super(loggerName, null);
        setLevel(Level.ALL);
        try {
            FileHandler fileHandler = new FileHandler(filePattern, 16777216, 8, true);
            fileHandler.setFormatter(new ConciseFormatter(false));
            addHandler(fileHandler);
            ColouredWriter consoleHandler = new ColouredWriter(reader);
            consoleHandler.setLevel(Level.INFO);
            consoleHandler.setFormatter(new ConciseFormatter(true));
            addHandler(consoleHandler);
        } catch (IOException ex) {
            System.err.println("Could not register logger!");
            ex.printStackTrace();
        }
        this.dispatcher.start();
    }

    public void log(LogRecord record) {
        this.dispatcher.queue(record);
    }

    void doLog(LogRecord record) {
        super.log(record);
    }

    public void info(String message, Object... objects) {
        log(Level.INFO, "[RevampSpigot] " + message, objects);
    }

    public void warning(String message, Object... objects) {
        log(Level.WARNING, message, objects);
    }

    public void warn(String message, Object... objects) {
        log(Level.WARNING, message, objects);
    }

    public void warn(String message) {
        log(Level.WARNING, message);
    }

    public void warn(String message, Throwable thrown) {
        log(Level.WARNING, message, thrown);
    }

    public void error(String message, Throwable thrown) {
        log(Level.WARNING, message, thrown);
    }

    public void warning(String message, Throwable thrown) {
        log(Level.WARNING, message, thrown);
    }
}
