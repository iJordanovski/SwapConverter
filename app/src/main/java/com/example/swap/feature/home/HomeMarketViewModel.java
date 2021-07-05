package com.example.swap.feature.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.swap.model.MarketInfo;
import com.example.swap.network.MarketApi;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@HiltViewModel
public class HomeMarketViewModel extends ViewModel {

    private Disposable disposable;
    public final MutableLiveData<Boolean> success;
    public final MutableLiveData<List<MarketInfo>> marketInfoList;
    public final MutableLiveData<Boolean>showError;
    public final MutableLiveData<Boolean> showProgressBar;
    private final MarketApi marketApi;

    @Inject
    public HomeMarketViewModel(MarketApi marketApi) {
        marketInfoList = new MutableLiveData<>();
        success = new MutableLiveData<>();
        showError = new MutableLiveData<>();
        showProgressBar = new MutableLiveData<>();
        this.marketApi = marketApi;

    }

    void getMarketInfo() {
        showProgressBar.setValue(true);
        disposable = marketApi
                .getMarketValue()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result ->{
                    showProgressBar.setValue(false);
                    success.setValue(true);
                    if (result.market != null && !result.market.isEmpty()) {
                        marketInfoList.setValue(result.market);
                    }
                    showError.setValue(false);
                },error ->{
                    showProgressBar.setValue(false);
                    showError.setValue(true);
                });
    }
    @Override
    protected void onCleared() {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onCleared();
    }
}
