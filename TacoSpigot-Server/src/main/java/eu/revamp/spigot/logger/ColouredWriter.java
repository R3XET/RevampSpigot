package eu.revamp.spigot.logger;

import jline.console.ConsoleReader;
import org.fusesource.jansi.Ansi;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class ColouredWriter extends Handler {
    private final ConsoleReader console;

    public ColouredWriter(ConsoleReader console) {
        this.console = console;
    }

    public void print(String s) {
        try {
            this.console.print(String.valueOf(Ansi.ansi().eraseLine(Ansi.Erase.ALL).toString()) + '\r' + s + Ansi.ansi().reset().toString());
            this.console.drawLine();
            this.console.flush();
        } catch (IOException iOException) {}
    }

    public void publish(LogRecord record) {
        if (isLoggable(record))
            print(getFormatter().format(record));
    }

    public void flush() {}

    public void close() throws SecurityException {}
}

