package com.ara.base.ui.page.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XieXin on 2020/7/17.
 * ViewPager适配器
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> titles;

    public ViewPagerAdapter(AppCompatActivity activity, List<Fragment> fragments) {
        super(activity.getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragments;
        if (this.fragments == null) this.fragments = new ArrayList<>();
    }

    public ViewPagerAdapter(AppCompatActivity activity, List<Fragment> fragments, List<String> titles) {
        super(activity.getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.fragments = fragments;
        this.titles = titles;
        if (this.fragments == null) this.fragments = new ArrayList<>();
        if (this.titles == null) this.titles = new ArrayList<>();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (titles.size() > 0) {
            return titles.get(position);
        } else {
            return super.getPageTitle(position);
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
