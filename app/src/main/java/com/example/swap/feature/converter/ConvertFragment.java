package com.example.swap.feature.converter;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.example.swap.R;
import com.example.swap.common.CryptoListAdapter;
import com.example.swap.common.FiatListAdapter;
import com.example.swap.databinding.FragmentConvertBinding;
import com.example.swap.model.FiatInfo;
import com.example.swap.model.MarketInfo;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ConvertFragment extends Fragment {
    private FragmentConvertBinding binding;
    private ConverterViewModel viewModel;
    private CryptoListAdapter currencyListAdapter;
    private FiatListAdapter fiatListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentConvertBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ConverterViewModel.class);
        setupObservers();
        setupUi();
    }

    private void setupUi() {

        binding.containerFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                currencyListAdapter = new CryptoListAdapter(new CryptoListAdapter.OnClickListener() {
                    @Override
                    public void onItemClicked(MarketInfo marketInfo) {
                        binding.currencyName.setText(marketInfo.currencySymbol);
                        binding.currencyValue.setText(marketInfo.value);
                        dialog.dismiss();
                    }
                });

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.popup_crypto_list);
                RecyclerView recyclerView = dialog.findViewById(R.id.recyclerViewCity);
                recyclerView.setAdapter(currencyListAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                viewModel.getMarketValues();
                dialog.show();
            }

        });

        binding.containerTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                fiatListAdapter = new FiatListAdapter(new FiatListAdapter.OnClickListener() {
                    @Override
                    public void onItemClicked(FiatInfo fiatInfo) {
                        binding.fiatCurrencyName.setText(fiatInfo.symbol);
                        binding.fiatCurrencyValue.setText(fiatInfo.rate);
                        dialog.dismiss();
                    }
                });

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.popup_crypto_list);
                RecyclerView recyclerView = dialog.findViewById(R.id.recyclerViewCity);
                recyclerView.setAdapter(fiatListAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                viewModel.getFiatValues();
                dialog.show();
            }
        });


        binding.convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.convertFrom.getText().toString().isEmpty()) {
                    convert();
                } else {
                    binding.convertFrom.setError(getString(R.string.required));
                }
            }
        });
    }
    private void convert() {
        float valueToDouble = Float.parseFloat(binding.currencyValue.getText().toString());
        double inputValue = Double.parseDouble(binding.convertFrom.getText().toString());
        double fiatValueToDouble = Double.parseDouble(binding.fiatCurrencyValue.getText().toString());
        double fiatExchangeRate = fiatValueToDouble * 100;
        double cryptoConversion = inputValue * valueToDouble;
        double conversion = cryptoConversion / fiatExchangeRate * 100;

        DecimalFormat decimalFormatMarketCap = new DecimalFormat("###,###.##");
        String conversionToString = decimalFormatMarketCap.format(conversion);

        binding.convertTo.setText(conversionToString);
    }
    private void setupObservers() {
        viewModel.cryptoResult.observe(getViewLifecycleOwner(), result -> {
            currencyListAdapter.addAll(result);
        });
        viewModel.fiatResult.observe(getViewLifecycleOwner(), fiatResult -> {
            fiatListAdapter.addAll(fiatResult);
        });
    }

}