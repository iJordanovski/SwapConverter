package com.example.swap.model;

import com.google.gson.annotations.SerializedName;

public class FiatInfo {
    @SerializedName("id")
    public String currencyId;
    @SerializedName("symbol")
    public String symbol;
    @SerializedName("currencySymbol")
    public String currencySymbol;
    @SerializedName("type")
    public String currencyType;
    @SerializedName("rateUsd")
    public String rate;

}
