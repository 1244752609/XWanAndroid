package com.ara.project_common.data.util;

import android.text.TextUtils;

import com.ara.common.util.GsonUtils;
import com.ara.common.util.MMKVUtils;
import com.ara.network.RetrofitHolder;
import com.ara.project_common.data.bean.AccountBean;
import com.ara.project_common.data.config.Configs;


/**
 * Created by XieXin on 2018/12/27.
 * 账户 工具类
 */
public class AccountUtils {
    /*** 是否登录 登录-true 未登录-false */
    private static boolean isLogin = false;
    /*** 登录账号 */
    private static String loginAccount = "";
    /*** Token */
    private static String token = "";
    /*** 账户 */
    private static AccountBean account;

    public static boolean isLogin() {
        isLogin = MMKVUtils.decodeBoolean(Configs.ACCOUNT_IS_LOGIN, false);
        return isLogin;
    }

    public static void setIsLogin(boolean isLogin) {
        AccountUtils.isLogin = isLogin;
        MMKVUtils.encode(Configs.ACCOUNT_IS_LOGIN, isLogin);
    }

    public static String getLoginAccount() {
        if (TextUtils.isEmpty(loginAccount))
            loginAccount = MMKVUtils.decodeString(Configs.LOGIN_ACCOUNT, "");
        return loginAccount;
    }

    public static void setLoginAccount(String loginAccount) {
        AccountUtils.loginAccount = loginAccount;
        MMKVUtils.encode(Configs.LOGIN_ACCOUNT, loginAccount);
    }

    public static String getToken() {
        if (TextUtils.isEmpty(token))
            token = MMKVUtils.decodeString(RetrofitHolder.TOKEN_KEY, "");
        return token;
    }

    public static void setToken(String token) {
        AccountUtils.token = token;
        MMKVUtils.encode(RetrofitHolder.TOKEN_KEY, token);
    }

    public static AccountBean getAccount() {
        if (account == null) {
            String json = MMKVUtils.decodeString(Configs.ACCOUNT, "");
            if (TextUtils.isEmpty(json)) return new AccountBean();
            account = GsonUtils.toBean(json, AccountBean.class);
        }
        return account;
    }

    public static void setAccount(AccountBean account) {
        if (account == null) return;
        AccountUtils.account = account;
        MMKVUtils.encode(Configs.ACCOUNT, GsonUtils.toString(account));
    }

    public static void logout() {
        MMKVUtils.remove(Configs.ACCOUNT_IS_LOGIN);
        MMKVUtils.remove(Configs.ACCOUNT);
        isLogin = false;
        account = null;
        setToken("");
    }
}
