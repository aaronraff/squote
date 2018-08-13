package com.aaronraffdev.squote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {
    private IEXApiClient apiClient;
    private String baseURL = "https://api.iextrading.com/1.0/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiClient = retrofit.create(IEXApiClient.class);

        setListeners();
    }

    private void setListeners() {
        EditText symbolField = findViewById(R.id.symbol);
        symbolField.addTextChangedListener(new SymbolWatcher(symbolField, apiClient, this));
    }
}