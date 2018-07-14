package com.aaronraffdev.squote;

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

    private Timer timer = new Timer();

    public SymbolWatcher(EditText symbolField, TextView priceView, IEXApiClient apiClient) {
        this.symbolField = symbolField;
        this.priceView = priceView;
        this.apiClient = apiClient;
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
                                getStockInfo(symbolField.getText().toString());
                            }
                        });
                    }
                }, 1000
        );
    }

    private void getStockInfo(String symbol) {
        apiClient.price(symbol).enqueue(new Callback<Stock>() {
            @Override
            public void onResponse(Call<Stock> call, Response<Stock> response) {
                if(response.body() != null) {
                    Log.i("DONE", response.body().getSector());
                    updateUI(response);
                }
                else {
                    Log.i("FAIL", response.toString());
                }
            }

            @Override
            public void onFailure(Call<Stock> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }

    private void updateUI(Response<Stock> response)
    {
        String priceString = new StringBuilder()
                .append("$")
                .append(response.body().getLatestPrice())
                .toString();

        priceView.setText(priceString);
    }
}
