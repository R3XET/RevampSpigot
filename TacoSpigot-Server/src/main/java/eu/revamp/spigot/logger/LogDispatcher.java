package eu.revamp.spigot.logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.LogRecord;

public class LogDispatcher extends Thread {
    private final RevampLogger logger;

    private final BlockingQueue<LogRecord> queue = new LinkedBlockingQueue<>();

    public LogDispatcher(RevampLogger logger) {
        super("RevampSpigot Logger Thread");
        this.logger = logger;
    }

    public void run() {
        while (!isInterrupted()) {
            LogRecord record;
            try {
                record = this.queue.take();
            } catch (InterruptedException interruptedException) {
                continue;
            }
            this.logger.doLog(record);
        }
        for (LogRecord record : this.queue)
            this.logger.doLog(record);
    }

    public void queue(LogRecord record) {
        if (!isInterrupted())
            this.queue.add(record);
    }
}

