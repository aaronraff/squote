package com.aaronraffdev.squote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IEXApiClient {
    @GET("stock/{symbol}/quote")
    Call<Stock> price(@Path("symbol") String symbol);
}
