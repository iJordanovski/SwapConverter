package com.example.swap.feature.home;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swap.R;
import com.example.swap.databinding.ItemValuesBinding;
import com.example.swap.model.MarketInfo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeMarketAdapter extends RecyclerView.Adapter<HomeMarketAdapter.HomeMarketViewHolder> {

    public final List<MarketInfo> marketInfo = new ArrayList<>();


    public void addAll(List<MarketInfo> marketInfoList) {
        marketInfo.clear();
        marketInfo.addAll(marketInfoList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeMarketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemValuesBinding binding = ItemValuesBinding.inflate(inflater, parent, false);
        return new HomeMarketViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeMarketViewHolder holder, int position) {
        holder.bind(marketInfo.get(position));
    }

    @Override
    public int getItemCount() {
        return marketInfo.size();
    }

    static class HomeMarketViewHolder extends RecyclerView.ViewHolder {
        private ItemValuesBinding binding;

        public HomeMarketViewHolder(@NonNull ItemValuesBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(MarketInfo marketInfo) {
            binding.name.setText(marketInfo.currencyName);
            binding.symbol.setText(marketInfo.currencySymbol);
            binding.lastPrice.setText(marketInfo.value);
            binding.marketPrice.setText(marketInfo.marketCapital);
            binding.changePercent.setText(marketInfo.changePercent);

            if (marketInfo.changePercent != null) {
                DecimalFormat decimalFormatChangePercent = new DecimalFormat("###,###,##%");
                double changePercentToDouble = Double.parseDouble(marketInfo.changePercent);
                String newChangePercentFormat = decimalFormatChangePercent.format(changePercentToDouble);

                if (changePercentToDouble < 0) {
                    binding.changePercent.setTextColor(Color.parseColor("#E55F67"));
                    binding.changePercent.setText(newChangePercentFormat);
                } else {
                    binding.changePercent.setTextColor(Color.parseColor("#1AB775"));
                    binding.changePercent.setText("+" + newChangePercentFormat);
                }
            }

            if (marketInfo.value != null) {
                    DecimalFormat decimalFormatLastPrice = new DecimalFormat("$###,###.##");
                    BigDecimal valuePriceToDouble = new BigDecimal(marketInfo.value);
                    BigDecimal doubleToBigDecimal = valuePriceToDouble.setScale(2,BigDecimal.ROUND_CEILING);
                    String lastPriceToString = decimalFormatLastPrice.format(doubleToBigDecimal.doubleValue());
                    binding.lastPrice.setText(lastPriceToString);
                }

                if (marketInfo.marketCapital != null) {
                    DecimalFormat decimalFormatMarketCap = new DecimalFormat("$###,###.##");
                    BigDecimal marketCapToDouble = new BigDecimal(marketInfo.marketCapital);
                    BigDecimal doubleToBigDecimal = marketCapToDouble.setScale(2,BigDecimal.ROUND_CEILING);
                    String marketCapToString = decimalFormatMarketCap.format(doubleToBigDecimal);
                    binding.marketPrice.setText(marketCapToString);
                }


            }
        }
}
