package com.ara.mine.ui.page.adapter;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ara.mine.R;
import com.ara.mine.data.bean.IntegralBean;
import com.ara.mine.databinding.MineItemIntegralRankBinding;

import pw.xiaohaozi.adapter_plus.adapter.SimpleAdapter;
import pw.xiaohaozi.adapter_plus.holder.ViewHolder;

/**
 * Created by XieXin on 2022/3/11.
 * 积分排行榜
 */
public class IntegralRankAdapter extends SimpleAdapter<MineItemIntegralRankBinding, IntegralBean> {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder<MineItemIntegralRankBinding> holder, int position,
                                    @NonNull MineItemIntegralRankBinding binding, @Nullable IntegralBean data, int checkIndex) {
        if (position < 3) {
            binding.tvRank.setVisibility(View.GONE);
            binding.ivIcon.setVisibility(View.VISIBLE);
            switch (position) {
                case 0:
                    binding.ivIcon.setImageResource(R.mipmap.ic_crown_gold);
                    break;
                case 1:
                    binding.ivIcon.setImageResource(R.mipmap.ic_crown_silver);
                    break;
                case 2:
                    binding.ivIcon.setImageResource(R.mipmap.ic_crown_cooper);
                    break;
            }
        } else {
            binding.tvRank.setVisibility(View.VISIBLE);
            binding.ivIcon.setVisibility(View.GONE);
        }
        binding.tvRank.setText(position + "");
        binding.tvName.setText(data.getUsername());
        binding.tvIntegral.setText(data.getCoinCount());
    }
}
