package com.aaronraffdev.squote;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aaronraff on 7/10/18.
 */

public class SymbolWatcher implements TextWatcher {
    private EditText symbolField;
    private TextView priceView;
    private IEXApiClient apiClient;

    private UIUpdater<Stock> uiUpdater;
    private Timer timer = new Timer();

    public SymbolWatcher(EditText symbolField, IEXApiClient apiClient, Activity activity) {
        this.symbolField = symbolField;
        this.apiClient = apiClient;
        this.uiUpdater = new HomeUIUpdater(activity);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        timer.cancel();
        timer = new Timer();
        timer.schedule(
                // Update text after 1 second of no typing
                new TimerTask() {
                    @Override
                    public void run() {
                        // UI needs to be updated on main thread
                        Utils.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                updateUI(symbolField.getText().toString());
                            }
                        });
                    }
                }, 1000
        );
    }

    private void updateUI(String symbol) {
        apiClient.price(symbol).enqueue(new Callback<Stock>() {
            @Override
            public void onResponse(Call<Stock> call, Response<Stock> response) {
                if(response.body() != null) {
                    uiUpdater.update(response.body());
                }
                else {
                    Log.i("FAIL", response.toString());
                }
            }

            @Override
            public void onFailure(Call<Stock> call, Throwable t) {
                Log.e("ERR", t.toString());
            }
        });
    }
}
