package com.ara.home.ui.page.adapter;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ara.project_common.data.bean.ArticleBean;
import com.ara.home.databinding.HomeItemHomeArticleBinding;

import pw.xiaohaozi.adapter_plus.adapter.SimpleAdapter;
import pw.xiaohaozi.adapter_plus.holder.ViewHolder;

/**
 * Created by XieXin on 2022/3/11.
 * 搜索列表
 */
public class SearchListAdapter extends SimpleAdapter<HomeItemHomeArticleBinding, ArticleBean> {
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder<HomeItemHomeArticleBinding> holder, int position,
                                    @NonNull HomeItemHomeArticleBinding binding, @Nullable ArticleBean data, int checkIndex) {
        binding.tvAuthor.setText(TextUtils.isEmpty(data.getAuthor()) ? data.getShareUser() : data.getAuthor());
        binding.tvContent.setText(data.getTitle());
        binding.tvChapter.setText(String.format("%s·%s", data.getSuperChapterName(), data.getChapterName()));
        binding.tvTime.setText(data.getNiceDate());
        binding.tvRefresh.setVisibility(data.isFresh() ? View.VISIBLE : View.GONE);
        binding.btnCollect.setOnClickListener(holder);
        binding.btnCollect.setText(data.isCollect() ? "取消收藏" : "收藏");
        binding.btnCollect.setTextColor(!data.isCollect() ?
                ContextCompat.getColor(mContext, com.ara.base.R.color.colorPrimary) :
                ContextCompat.getColor(mContext, com.ara.base.R.color.grey));
    }
}
