package com.example.swap.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swap.databinding.ItemCryptoCurrenciesBinding;
import com.example.swap.model.MarketInfo;

import java.util.ArrayList;
import java.util.List;

public class CryptoListAdapter extends RecyclerView.Adapter<CryptoListAdapter.CryptoListViewHolder> {

    private final OnClickListener onClickListener;

    private ItemCryptoCurrenciesBinding binding;
    private List<MarketInfo> marketInfoList = new ArrayList<>();

    public CryptoListAdapter(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onItemClicked(MarketInfo marketInfo);
    }

    public void addAll(List<MarketInfo> marketInfo) {
        marketInfoList.clear();
        marketInfoList.addAll(marketInfo);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CryptoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemCryptoCurrenciesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CryptoListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoListViewHolder holder, int position) {
        holder.bind(marketInfoList.get(position));
    }

    @Override
    public int getItemCount() {
        return marketInfoList.size();
    }

    class CryptoListViewHolder extends RecyclerView.ViewHolder {
        private ItemCryptoCurrenciesBinding binding;

        public CryptoListViewHolder(@NonNull ItemCryptoCurrenciesBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(MarketInfo marketInfo) {
            binding.currencyName.setText(marketInfo.currencySymbol);
            binding.currencyName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onClickListener.onItemClicked(marketInfo);
                }
            });
        }
    }
}
