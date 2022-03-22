package com.ara.common.safe;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by XieXin on 2018/12/10.
 * 正则表达式工具类
 */
public final class RegexUtils {
    /*** 密码(以字母开头，长度在6~16之间，只能包含字母、数字和下划线) */
    public static final String REGEX_PASSWORD = "^[a-zA-Z]\\w{5,15}$";
    /*** 帐号是否合法(字母开头，允许5-16字节，允许字母数字下划线) */
    public static final String REGEX_ACCOUNT = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";
    /*** 手机号码 */
    public static final String REGEX_MOBILE = "^(13|14|15|17|18|19)[0-9]{9}$";
    /*** 只包含汉字英文数字 */
    public static final String REGEX_CONTAIN_CHINESE_ENGLISH_NUMBER = "^[a-zA-Z0-9\u4E00-\u9FA5]+$";
    /*** 只包含汉字 */
    public static final String REGEX_CONTAIN_CHINESE = "^[\u4E00-\u9FA5]+$";
    /*** VIN */
    public static final String REGEX_VIN = "^[A-Z0-9]{8}\\d[A-Z0-9]{8}$";
    /*** 身份证 */
    public static final String REGEX_ID_CARD = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$";

    private RegexUtils() {
    }

    /**
     * 校验密码
     *
     * @param password 密码
     * @return 校验通过为true，否则为false
     */
    public static boolean isPassword(String password) {
        if (TextUtils.isEmpty(password)) return false;
        Pattern pattern = Pattern.compile(REGEX_PASSWORD);
        return pattern.matcher(password).matches();
    }

    /**
     * 校验账号
     *
     * @param account 账号
     * @return 校验通过为true，否则为false
     */
    public static boolean isAccount(String account) {
        if (TextUtils.isEmpty(account)) return false;
        Pattern pattern = Pattern.compile(REGEX_ACCOUNT);
        return pattern.matcher(account).matches();
    }

    /**
     * 校验手机号码
     *
     * @param mobile
     * @return 校验通过为true，否则为false
     */
    public static boolean isMobile(String mobile) {
        if (TextUtils.isEmpty(mobile)) return false;
        Pattern pattern = Pattern.compile(REGEX_MOBILE);
        return pattern.matcher(mobile).matches();
    }

    /**
     * 是否只包含汉字英文数字
     *
     * @param str
     * @return 校验通过为true，否则为false
     */
    public static boolean isContainChineseEnglishNumber(String str) {
        if (TextUtils.isEmpty(str)) return false;
        Pattern pattern = Pattern.compile(REGEX_CONTAIN_CHINESE_ENGLISH_NUMBER);
        return pattern.matcher(str).matches();
    }

    /**
     * 是否只包含汉字
     *
     * @param str
     * @return 校验通过为true，否则为false
     */
    public static boolean isContainChinese(String str) {
        if (TextUtils.isEmpty(str)) return false;
        Pattern pattern = Pattern.compile(REGEX_CONTAIN_CHINESE);
        return pattern.matcher(str).matches();
    }

    /**
     * 校验VIN
     *
     * @param vin
     * @return 校验通过为true，否则为false
     */
    public static boolean isVIN(String vin) {
        if (TextUtils.isEmpty(vin)) return false;
        Pattern pattern = Pattern.compile(REGEX_VIN);
        return pattern.matcher(vin).matches();
    }

    /**
     * 校验身份证
     *
     * @param iddCard
     * @return 校验通过为true，否则为false
     */
    public static boolean isIdCard(String iddCard) {
        if (TextUtils.isEmpty(iddCard)) return false;
        Pattern pattern = Pattern.compile(REGEX_ID_CARD);
        return pattern.matcher(iddCard).matches();
    }
}
