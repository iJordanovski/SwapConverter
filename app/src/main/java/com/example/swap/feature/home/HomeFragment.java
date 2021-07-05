package com.example.swap.feature.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.swap.R;
import com.example.swap.databinding.FragmentHomeBinding;
import com.example.swap.databinding.ItemValuesBinding;
import com.example.swap.model.MarketInfo;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private HomeMarketAdapter adapter;
    private HomeMarketViewModel viewModel;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        swipeRefreshLayout = binding.swipeContainer;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeMarketViewModel.class);
        setupUi();
        setupObservers();
        viewModel.getMarketInfo();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getMarketInfo();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setupUi() {
        adapter = new HomeMarketAdapter();
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupObservers() {

        viewModel.showError.observe(getViewLifecycleOwner(), showError -> {
            if (showError) {
                Snackbar snackbar = Snackbar.make(binding.getRoot(), (getString(R.string.try_again)), BaseTransientBottomBar.LENGTH_INDEFINITE);
                snackbar.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewModel.getMarketInfo();
                        snackbar.dismiss();
                    }
                })
                        .show();
            }
        });
        viewModel.showProgressBar.observe(getViewLifecycleOwner(), showProgressBar -> {
            if (showProgressBar) {
                binding.progressView.getRoot().setVisibility(View.VISIBLE);
            } else {
                binding.progressView.getRoot().setVisibility(View.INVISIBLE);
            }
        });
        viewModel.marketInfoList.observe(getViewLifecycleOwner(), market -> {
            adapter.addAll(market);
        });

    }


}