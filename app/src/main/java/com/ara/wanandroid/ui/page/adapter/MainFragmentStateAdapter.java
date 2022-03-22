package com.ara.wanandroid.ui.page.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ara.home.ui.page.HomeFragment;
import com.ara.mine.ui.page.MineFragment;
import com.ara.module_public.ui.page.PublicFragment;
import com.ara.project.ui.page.ProjectFragment;
import com.ara.square.ui.page.SquareFragment;

import org.jetbrains.annotations.NotNull;

/**
 * Created by XieXin on 2021/6/11.
 * 主页Fragment切换适配器
 */
public class MainFragmentStateAdapter extends FragmentStateAdapter {
    private Fragment[] fragments;

    public MainFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragments = new Fragment[]{
                new HomeFragment(),
                new ProjectFragment(),
                new SquareFragment(),
                new PublicFragment(),
                new MineFragment()
        };
    }

    @NotNull
    @Override
    public Fragment createFragment(int position) {
        return fragments[position];
    }

    @Override
    public int getItemCount() {
        return fragments.length;
    }
}
