package com.example.swap.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FiatResponse {
    @SerializedName("data")
    public List<FiatInfo> rates;

    public FiatResponse(List<FiatInfo> ratesInfoList) {
        this.rates = ratesInfoList;
    }
}
