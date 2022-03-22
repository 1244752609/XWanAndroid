package com.ara.home.ui.page.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ara.db.entity.SearchRecord;
import com.ara.home.databinding.HomeItemLabelBinding;
import com.google.android.flexbox.FlexboxLayoutManager;

import pw.xiaohaozi.adapter_plus.adapter.SimpleAdapter;
import pw.xiaohaozi.adapter_plus.holder.ViewHolder;

/**
 * Created by XieXin on 2022/3/11.
 * 搜索记录
 */
public class SearchRecordAdapter extends SimpleAdapter<HomeItemLabelBinding, SearchRecord> {
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder<HomeItemLabelBinding> holder, int position,
                                    @NonNull HomeItemLabelBinding binding, @Nullable SearchRecord data, int checkIndex) {
        ViewGroup.LayoutParams lp = binding.libel.getLayoutParams();
        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams) lp;
            flexboxLp.setFlexGrow(1.0f);
        }

        if (data != null) binding.libel.setText(data.content);
        binding.libel.setOnClickListener(holder);
    }
}
