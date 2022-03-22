package com.ara.home.ui.page.adapter;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.ViewDataBinding;

import com.ara.common.image.GlideUtils;
import com.ara.home.R;
import com.ara.project_common.data.bean.ArticleBean;
import com.ara.home.data.bean.BannerBean;
import com.ara.home.data.bean.BannerListBean;
import com.ara.home.databinding.HomeItemHomeArticleBinding;
import com.ara.home.databinding.HomeItemHomeBannerBinding;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import pw.xiaohaozi.adapter_plus.adapter.MultiTypeAdapter;
import pw.xiaohaozi.adapter_plus.data.ViewTyper;
import pw.xiaohaozi.adapter_plus.holder.ViewHolder;

/**
 * Created by XieXin on 2022/3/4.
 * 首页
 */
public class HomeAdapter extends MultiTypeAdapter {
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder<ViewDataBinding> holder, int position,
                                    @NonNull ViewDataBinding binding, @Nullable ViewTyper data, int checkIndex) {
        if (data == null) return;
        if (data.getItemViewType() == 0) {
            HomeItemHomeBannerBinding bannerBinding = (HomeItemHomeBannerBinding) binding;
            BannerListBean item = (BannerListBean) data;
            bannerBinding.banner.setAdapter(new BannerImageAdapter<BannerBean>(item.getBeans()) {
                @Override
                public void onBindView(BannerImageHolder holder, BannerBean data, int position, int size) {
                    GlideUtils.loadImage(mContext, data.getImagePath(), holder.imageView);
                }
            });
        } else {
            HomeItemHomeArticleBinding articleBinding = (HomeItemHomeArticleBinding) binding;
            ArticleBean item = (ArticleBean) data;
            articleBinding.tvAuthor.setText(TextUtils.isEmpty(item.getAuthor()) ? item.getShareUser() : item.getAuthor());
            articleBinding.tvContent.setText(item.getTitle());
            articleBinding.tvChapter.setText(String.format("%s·%s", item.getSuperChapterName(), item.getChapterName()));
            articleBinding.tvTime.setText(item.getNiceDate());
            articleBinding.tvRefresh.setVisibility(item.isFresh() ? View.VISIBLE : View.GONE);
            articleBinding.btnCollect.setOnClickListener(holder);
            articleBinding.btnCollect.setText(item.isCollect() ? "取消收藏" : "收藏");
            articleBinding.btnCollect.setTextColor(!item.isCollect() ?
                    ContextCompat.getColor(mContext, com.ara.base.R.color.colorPrimary) :
                    ContextCompat.getColor(mContext, com.ara.base.R.color.grey));
        }
    }

    @Override
    protected int getLayoutRes(int viewType) {
        if (viewType == 0) {
            return R.layout.home_item_home_banner;
        } else {
            return R.layout.home_item_home_article;
        }
    }
}
