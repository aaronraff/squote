package com.squote;

import com.google.firebase.database.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.SECONDS;

public class SymbolUpdater {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final OkHttpClient client = new OkHttpClient();
    private final String refDataUrl = "https://api.iextrading.com/1.0/ref-data/symbols";
    private String symbolRefString;

    public SymbolUpdater(String symbolRefString) {
        this.symbolRefString = symbolRefString;
    }

    public void runDailyJob() {
        // IEXAPI updates symbols at 7:45 EST
        // Run a few minutes after to have a buffer
        ZonedDateTime timeToRun = ZonedDateTime.now(ZoneId.of("America/New_York"))
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
        ZonedDateTime estTime = ZonedDateTime.now(ZoneId.of("America/New_York"));

        // Time has already passed for the day
        if (timeToRun.compareTo(estTime) < 0) {
            timeToRun = timeToRun.plusDays(1);
        }

        long initialDelay = Duration.between(estTime, timeToRun).getSeconds();
        return initialDelay;
    }

    public void runJob() {
        try {
            String res = getRefData();
            JSONArray symbols = new JSONArray(res);
            writeData(symbols);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private String getRefData() throws IOException {
        Request request = new Request.Builder()
                .url(refDataUrl)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private void writeData(JSONArray symbols) {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference().child(symbolRefString);

        HashMap<String, Object> data = new HashMap<>();

        for (int index = 0; index < symbols.length(); index++) {
            JSONObject info = symbols.getJSONObject(index);

            String symbol = info.getString("symbol");
            String name = info.getString("name");
            Boolean isEnabled = info.getBoolean("isEnabled");
            String hash = Integer.toString(name.hashCode());

            // Only add stocks that are enabled on IEX
            if (isEnabled) {
                // Store key as hash to find it later when updating
                Stock stock = new Stock(symbol, name);
                data.put(hash, stock);
            } else {
                // Use null to remove the record if there is one
                data.put(hash, null);
            }
        }

        // Write all of the data at once
        try {
            // get method blocks
            ref.updateChildrenAsync(data).get();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
