package com.aaronraffdev.squote;

import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        updateFragment(item);
                        return true;
                    }
                });
    }

    private void updateFragment(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_content, new SearchFragment())
                        .commit();
        }
    }


}
