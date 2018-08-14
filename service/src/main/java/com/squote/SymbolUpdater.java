package com.squote;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.SECONDS;

public class SymbolUpdater {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void runDailyJob() {
        // IEXAPI updates symbols at 7:45 EST
        // Run a few minutes after to have a buffer
        ZonedDateTime timeToRun = ZonedDateTime.now(ZoneId.of("America/New York"))
                .withHour(8).withMinute(0).withSecond(0);

        // Used to schedule at the same time every day.
        long initialDelay = getInitialDelay(timeToRun);

        final Runnable job = new Runnable() {
            @Override
            public void run() {
                runJob();
            }
        };

        final ScheduledFuture<?> jobHandler =
                scheduler.scheduleAtFixedRate(job, initialDelay, 24 * 60 * 60, SECONDS);
    }

    private long getInitialDelay(ZonedDateTime timeToRun) {
        ZonedDateTime estTime = ZonedDateTime.now(ZoneId.of("America/New York"));

        // Time has already passed for the day
        if (timeToRun.compareTo(estTime) < 0) {
            timeToRun.plusDays(1);
        }

        long initialDelay = Duration.between(timeToRun, estTime).getSeconds();
        return initialDelay;
    }

    private void runJob() {

    }
}
