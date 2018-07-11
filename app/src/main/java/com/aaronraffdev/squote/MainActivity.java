package com.aaronraffdev.squote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setListeners();
    }

    private void setListeners() {
        EditText symbolField = findViewById(R.id.symbol);
        TextView priceView = findViewById(R.id.price);

        symbolField.addTextChangedListener(new SymbolWatcher(symbolField, priceView));
    }
}
