package com.ara.square.ui.page.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ara.project_common.data.api.CommonRouterApi;
import com.ara.project_common.data.bean.ArticleBean;
import com.ara.square.R;
import com.ara.square.data.api.SquareRouterApi;
import com.ara.square.data.bean.ChildrenBean;
import com.ara.square.data.bean.NavigationBean;
import com.ara.square.databinding.SquItemNavigationBinding;
import com.google.android.flexbox.FlexboxLayout;

import pw.xiaohaozi.adapter_plus.adapter.SimpleAdapter;
import pw.xiaohaozi.adapter_plus.holder.ViewHolder;

/**
 * Created by XieXin on 2022/3/15.
 */
public class NavigationAdapter extends SimpleAdapter<SquItemNavigationBinding, NavigationBean> {
    private LayoutInflater inflater;

    public NavigationAdapter() {
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder<SquItemNavigationBinding> holder, int position, @NonNull SquItemNavigationBinding binding, @Nullable NavigationBean data, int checkIndex) {
        binding.title.setText(data.getName());
        if (data.getArticles() != null) {
            binding.flex.removeAllViews();
            for (ArticleBean article : data.getArticles()) {
                TextView tv = createLabel(binding.flex);
                tv.setText(article.getTitle());
                tv.setOnClickListener(v -> CommonRouterApi.jumpWebActivity(article.getLink()));
                binding.flex.addView(tv);
            }
        }

        if (data.getChildren() != null) {
            binding.flex.removeAllViews();
            for (ChildrenBean bean : data.getChildren()) {
                TextView tv = createLabel(binding.flex);
                tv.setText(bean.getName());
                tv.setOnClickListener(v -> SquareRouterApi.jumpSquareListActivity(bean));
                binding.flex.addView(tv);
            }
        }

    }

    private TextView createLabel(FlexboxLayout flex) {
        if (inflater == null) inflater = LayoutInflater.from(flex.getContext());
        TextView tv = (TextView) inflater.inflate(R.layout.squ_item_label, flex, false);
        ViewGroup.LayoutParams lp = tv.getLayoutParams();
        if (lp instanceof FlexboxLayout.LayoutParams) {
            FlexboxLayout.LayoutParams flexboxLp = (FlexboxLayout.LayoutParams) lp;
            flexboxLp.setFlexGrow(1.0f);
        }
        return tv;
    }
}
