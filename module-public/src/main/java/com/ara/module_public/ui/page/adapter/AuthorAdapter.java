package com.ara.module_public.ui.page.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ara.module_public.data.bean.PublicAuthorBean;
import com.ara.module_public.databinding.PubItemPublicAuthorBinding;

import pw.xiaohaozi.adapter_plus.adapter.SimpleAdapter;
import pw.xiaohaozi.adapter_plus.holder.ViewHolder;

/**
 * Created by XieXin on 2022/3/11.
 * 搜索列表
 */
public class AuthorAdapter extends SimpleAdapter<PubItemPublicAuthorBinding, PublicAuthorBean> {
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder<PubItemPublicAuthorBinding> holder, int position,
                                    @NonNull PubItemPublicAuthorBinding binding, @Nullable PublicAuthorBean data, int checkIndex) {
        binding.tvContent.setText(data.getName());
    }
}
