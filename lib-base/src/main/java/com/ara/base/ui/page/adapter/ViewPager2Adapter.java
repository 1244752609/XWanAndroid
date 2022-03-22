package com.ara.base.ui.page.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XieXin on 2020/7/17.
 * ViewPager2适配器
 */
public class ViewPager2Adapter extends FragmentStateAdapter {
    private List<Fragment> fragments;

    public ViewPager2Adapter(AppCompatActivity activity, List<Fragment> fragments) {
        super(activity);
        this.fragments = fragments;
        if (this.fragments == null) this.fragments = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
