package com.ara.mine.ui.page.adapter;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ara.mine.databinding.MineItemMyCollectBinding;
import com.ara.project_common.data.bean.ArticleBean;

import pw.xiaohaozi.adapter_plus.adapter.SimpleAdapter;
import pw.xiaohaozi.adapter_plus.holder.ViewHolder;

/**
 * Created by XieXin on 2022/3/11.
 * 我的收藏
 */
public class MyCollectAdapter extends SimpleAdapter<MineItemMyCollectBinding, ArticleBean> {
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder<MineItemMyCollectBinding> holder, int position,
                                    @NonNull MineItemMyCollectBinding binding, @Nullable ArticleBean data, int checkIndex) {
        binding.tvAuthor.setText(TextUtils.isEmpty(data.getAuthor()) ? data.getShareUser() : data.getAuthor());
        binding.tvContent.setText(data.getTitle());
        binding.tvChapter.setText(String.format("%s·%s", data.getSuperChapterName(), data.getChapterName()));
        binding.tvTime.setText(data.getNiceDate());
        binding.tvRefresh.setVisibility(data.isFresh() ? View.VISIBLE : View.GONE);
    }
}
