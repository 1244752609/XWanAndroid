package com.ara.project.ui.page.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ara.project.data.bean.ProjectClassifyBean;
import com.ara.project.databinding.ProItemProjectClassifyBinding;

import pw.xiaohaozi.adapter_plus.adapter.SimpleAdapter;
import pw.xiaohaozi.adapter_plus.holder.ViewHolder;

/**
 * Created by XieXin on 2022/3/11.
 * 搜索列表
 */
public class ClassifyAdapter extends SimpleAdapter<ProItemProjectClassifyBinding, ProjectClassifyBean> {
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder<ProItemProjectClassifyBinding> holder, int position,
                                    @NonNull ProItemProjectClassifyBinding binding, @Nullable ProjectClassifyBean data, int checkIndex) {
        binding.tvContent.setText(data.getName());
    }
}
