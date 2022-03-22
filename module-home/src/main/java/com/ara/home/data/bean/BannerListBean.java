package com.ara.home.data.bean;

import java.util.ArrayList;
import java.util.List;

import pw.xiaohaozi.adapter_plus.data.ViewTyper;

/**
 * Created by XieXin on 2022/3/4.
 * 广告
 */
public class BannerListBean implements ViewTyper {
    private List<BannerBean> beans;

    @Override
    public int getItemViewType() {
        return 0;
    }

    public List<BannerBean> getBeans() {
        if (beans == null) setBeans(new ArrayList<>());
        return beans;
    }

    public void setBeans(List<BannerBean> beans) {
        this.beans = beans;
    }
}
