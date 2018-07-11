package com.aaronraffdev.squote;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by aaronraff on 7/10/18.
 */

public class SymbolWatcher implements TextWatcher {
    private EditText symbolField;
    private TextView priceView;

    private Timer timer = new Timer();

    public SymbolWatcher(EditText symbolField, TextView priceView) {
        this.symbolField = symbolField;
        this.priceView = priceView;
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
                                String symbol = symbolField.getText().toString();
                                priceView.setText(symbol);
                            }
                        });
                    }
                }, 1000
        );

    }
}
