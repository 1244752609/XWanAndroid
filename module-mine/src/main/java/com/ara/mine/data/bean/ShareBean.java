package com.ara.mine.data.bean;

import com.ara.project_common.data.bean.ArticleBean;
import com.ara.project_common.data.bean.PagingBean;

/**
 * Created by XieXin on 2022/3/17.
 */
public class ShareBean {
    private IntegralBean coinInfo;
    private PagingBean<ArticleBean> shareArticles;

    public IntegralBean getCoinInfo() {
        if (coinInfo == null) setCoinInfo(new IntegralBean());
        return coinInfo;
    }

    public void setCoinInfo(IntegralBean coinInfo) {
        this.coinInfo = coinInfo;
    }

    public PagingBean<ArticleBean> getShareArticles() {
        if (shareArticles == null)
            setShareArticles(new PagingBean<ArticleBean>());
        return shareArticles;
    }

    public void setShareArticles(PagingBean<ArticleBean> shareArticles) {
        this.shareArticles = shareArticles;
    }
}
