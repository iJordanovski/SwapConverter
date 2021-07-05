package com.example.swap.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swap.databinding.ItemFiatValuesBinding;
import com.example.swap.model.FiatInfo;

import java.util.ArrayList;
import java.util.List;

public class FiatListAdapter extends RecyclerView.Adapter<FiatListAdapter.FiatListViewHolder> {

    private final OnClickListener onClickListener;
    private ItemFiatValuesBinding binding;
    private List<FiatInfo> fiatList = new ArrayList<>();

    public FiatListAdapter(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onItemClicked(FiatInfo fiatInfo);
    }

    public void addAll(List<FiatInfo> fiatInfoList) {
        fiatList.clear();
        fiatList.addAll(fiatInfoList);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FiatListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemFiatValuesBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new FiatListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FiatListViewHolder holder, int position) {
        holder.bind(fiatList.get(position));
    }

    @Override
    public int getItemCount() {
        return fiatList.size();
    }

    class FiatListViewHolder extends RecyclerView.ViewHolder {

        private ItemFiatValuesBinding binding;

        public FiatListViewHolder(@NonNull ItemFiatValuesBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(FiatInfo fiatInfo) {
            binding.currencyName.setText(fiatInfo.symbol);
            binding.currencyName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onItemClicked(fiatInfo);
                }
            });
        }
    }
}
