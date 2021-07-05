package com.example.swap.network;

import com.example.swap.model.MarketInfo;
import com.example.swap.model.MarketResponse;
import com.example.swap.model.FiatResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarketApi {
    @GET("v2/assets")
    Single<MarketResponse> getMarketValue();

    @GET("v2/rates")
    Single<FiatResponse> getRates();

}
