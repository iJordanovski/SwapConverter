package com.example.swap.model;

import com.google.gson.annotations.SerializedName;

public class MarketInfo {
    @SerializedName("name")
    public String currencyName;
    @SerializedName("symbol")
    public String currencySymbol;
    @SerializedName("priceUsd")
    public String value;
    @SerializedName("marketCapUsd")
    public String marketCapital;
    @SerializedName("changePercent24Hr")
    public String changePercent;


}

