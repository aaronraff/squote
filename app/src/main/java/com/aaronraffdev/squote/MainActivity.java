package com.aaronraffdev.squote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private IEXApiClient apiClient;
    private String baseURL = "https://api.iextrading.com/1.0/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setListeners();
    }


    private void setListeners() {

    }
}
