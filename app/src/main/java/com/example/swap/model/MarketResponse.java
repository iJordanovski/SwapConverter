package com.example.swap.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarketResponse {

    @SerializedName("data")
    public final List<MarketInfo> market;

    public MarketResponse(List<MarketInfo> market) {
        this.market = market;
    }
}
