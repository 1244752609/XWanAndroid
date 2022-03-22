package com.ara.mine.data.bean;

import com.ara.project_common.data.bean.AccountBean;

/**
 * Created by XieXin on 2022/3/16.
 */
public class UserInfoBean {
    private IntegralBean coinInfo;
    private AccountBean userInfo;

    public IntegralBean getCoinInfo() {
        if (coinInfo == null) setCoinInfo(new IntegralBean());
        return coinInfo;
    }

    public void setCoinInfo(IntegralBean coinInfo) {
        this.coinInfo = coinInfo;
    }

    public AccountBean getUserInfo() {
        if (userInfo == null) setUserInfo(new AccountBean());
        return userInfo;
    }

    public void setUserInfo(AccountBean userInfo) {
        this.userInfo = userInfo;
    }
}
