package com.ara.square.ui.binding_adapter;

import android.widget.FrameLayout;

import androidx.databinding.BindingAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.ara.square.R;
import com.blankj.utilcode.util.BarUtils;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

/**
 * Created by XieXin on 2022/3/14.
 */
public class SquareFragmentBindingAdapter {
    @BindingAdapter(value = {"squTabTitles", "squTabFragments"}, requireAll = false)
    public static void squInitTab(SlidingTabLayout tab, String[] titles, ArrayList<Fragment> fragments) {
        FrameLayout title = tab.getRootView().findViewById(R.id.title);
        BarUtils.addMarginTopEqualStatusBarHeight(title);

        ViewPager pager = tab.getRootView().findViewById(R.id.squ_view_pager);
        tab.setViewPager(pager, titles, (FragmentActivity) tab.getContext(), fragments);
    }
}
