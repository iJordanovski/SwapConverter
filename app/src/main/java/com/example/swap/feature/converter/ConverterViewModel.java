package com.example.swap.feature.converter;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.swap.model.FiatInfo;
import com.example.swap.model.MarketInfo;
import com.example.swap.network.MarketApi;

import java.util.List;


import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@HiltViewModel
public class ConverterViewModel extends ViewModel {
    private Disposable disposable;
    public final MutableLiveData<Boolean> showError;
    public final MutableLiveData<List<MarketInfo>> cryptoResult;
    public final MutableLiveData<Boolean> onSuccess;
    public final MutableLiveData<List<FiatInfo>> fiatResult;
    private MarketApi marketApi;

    @Inject
    public ConverterViewModel(MarketApi marketApi) {
        this.showError = new MutableLiveData<>();
        this.cryptoResult = new MutableLiveData<>();
        onSuccess = new MutableLiveData<>();
        fiatResult = new MutableLiveData<>();
        this.marketApi = marketApi;

    }

    public void getFiatValues() {
        disposable = marketApi
                .getRates()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fiatResponse ->{
                    onSuccess.setValue(true);
                    showError.setValue(false);
                    fiatResult.setValue(fiatResponse.rates);
                },error->{
                    showError.setValue(true);
                });
    }
    public void getMarketValues() {
        disposable = marketApi
                .getMarketValue()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> {
                    onSuccess.setValue(true);
                    cryptoResult.setValue(success.market);
                }, error -> {
                    onSuccess.setValue(false);
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
