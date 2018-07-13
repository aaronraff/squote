package com.aaronraffdev.squote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private IEXApiClient apiClient;
    private String baseURL = "https://api.iextrading.com/1.0/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiClient = retrofit.create(IEXApiClient.class);

        apiClient.price("msft").enqueue(new Callback<Stock>() {
            @Override
            public void onResponse(Call<Stock> call, Response<Stock> response) {
                if(response.body() != null)
                    Log.i("DONE", response.body().sector);
                else
                    Log.i("FAIL", response.toString());
            }

            @Override
            public void onFailure(Call<Stock> call, Throwable t) {
                System.out.println(t.toString());
            }
        });

        setListeners();
    }


    private void setListeners() {
        EditText symbolField = findViewById(R.id.symbol);
        TextView priceView = findViewById(R.id.price);

        symbolField.addTextChangedListener(new SymbolWatcher(symbolField, priceView));
    }
}
