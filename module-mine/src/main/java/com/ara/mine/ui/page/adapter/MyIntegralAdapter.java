package com.ara.mine.ui.page.adapter;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ara.common.util.DateUtils;
import com.ara.mine.data.bean.IntegralBean;
import com.ara.mine.databinding.MineItemIntegralBinding;

import pw.xiaohaozi.adapter_plus.adapter.SimpleAdapter;
import pw.xiaohaozi.adapter_plus.holder.ViewHolder;

/**
 * Created by XieXin on 2022/3/11.
 * 我的积分
 */
public class MyIntegralAdapter extends SimpleAdapter<MineItemIntegralBinding, IntegralBean> {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder<MineItemIntegralBinding> holder, int position,
                                    @NonNull MineItemIntegralBinding binding, @Nullable IntegralBean data, int checkIndex) {
        binding.tvTitle.setText(data.getReason());
        binding.tvTime.setText(DateUtils.millis2String(data.getDate()));
        binding.tvIntegral.setText("+" + data.getCoinCount());
    }
}
