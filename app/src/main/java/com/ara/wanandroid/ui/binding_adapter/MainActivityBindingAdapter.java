package com.ara.wanandroid.ui.binding_adapter;

import android.view.View;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.ara.account.data.api.AccountRouterApi;
import com.ara.project_common.data.util.AccountUtils;
import com.ara.wanandroid.R;
import com.ara.wanandroid.ui.page.adapter.MainFragmentStateAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Created by XieXin on 2022/2/21.
 */
public class MainActivityBindingAdapter {

    @BindingAdapter(value = "initViewPager")
    public static void initViewPager(ViewPager2 vp, boolean userInputEnabled) {

        //去除滑动阴影
        View childAt = vp.getChildAt(0);
        if (childAt instanceof RecyclerView) childAt.setOverScrollMode(View.OVER_SCROLL_NEVER);

        vp.setUserInputEnabled(userInputEnabled);
        BottomNavigationView nav = vp.getRootView().findViewById(R.id.nav_view);
        vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        nav.setSelectedItemId(R.id.tab1);
                        break;
                    case 1:
                        nav.setSelectedItemId(R.id.tab2);
                        break;
                    case 2:
                        nav.setSelectedItemId(R.id.tab3);
                        break;
                    case 3:
                        nav.setSelectedItemId(R.id.tab4);
                        break;
                    case 4:
                        nav.setSelectedItemId(R.id.tab5);
                        break;
                }
            }
        });
    }

    @BindingAdapter(value = "initBottomNav")
    public static void initBottomNav(BottomNavigationView nav, MainFragmentStateAdapter adapter) {
        View view = nav.getChildAt(0);
        view.findViewById(R.id.tab1).setOnLongClickListener(v -> true);
        view.findViewById(R.id.tab2).setOnLongClickListener(v -> true);
        view.findViewById(R.id.tab3).setOnLongClickListener(v -> true);
        view.findViewById(R.id.tab4).setOnLongClickListener(v -> true);

        ViewPager2 pager2 = nav.getRootView().findViewById(R.id.view_pager);
        nav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.tab1) {
                pager2.setCurrentItem(0, false);
            } else if (item.getItemId() == R.id.tab2) {
                pager2.setCurrentItem(1, false);
            } else if (item.getItemId() == R.id.tab3) {
                pager2.setCurrentItem(2, false);
            } else if (item.getItemId() == R.id.tab4) {
                pager2.setCurrentItem(3, false);
            } else if (item.getItemId() == R.id.tab5) {
                pager2.setCurrentItem(4, false);
            }
            return true;
        });
    }

    @BindingAdapter(value = "navCurrentItem")
    public static void setNavCurrentItem(ViewPager2 vp, int navCurrentItem) {
        vp.setCurrentItem(navCurrentItem);
    }
}
