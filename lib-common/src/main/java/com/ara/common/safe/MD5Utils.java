package com.ara.common.safe;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by XieXin on 2018/12/10.
 * MD5加密工具
 */

public class MD5Utils {

    private MD5Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * MD5加密
     *
     * @param str 明文字符串
     * @return 16进制密文
     */
    public static String encryptMD5ToString(final String str) {
        return encryptMD5ToString(str.getBytes());
    }

    /**
     * MD5加密
     *
     * @param data 明文字符串
     * @param salt 盐
     * @return 16进制加盐密文
     */
    public static String encryptMD5ToString(final String data, final String salt) {
        return bytes2HexString(encryptMD5((data + salt).getBytes()));
    }

    /**
     * MD5加密
     *
     * @param bytes 明文字节数组
     * @return 16进制密文
     */
    public static String encryptMD5ToString(final byte[] bytes) {
        return bytes2HexString(encryptMD5(bytes));
    }

    /**
     * MD5加密
     *
     * @param bytes 明文字节数组
     * @param salts 盐字节数组
     * @return 16进制加盐密文
     */
    public static String encryptMD5ToString(final byte[] bytes, final byte[] salts) {
        if (bytes == null || salts == null) return null;
        byte[] dataSalt = new byte[bytes.length + salts.length];
        System.arraycopy(bytes, 0, dataSalt, 0, bytes.length);
        System.arraycopy(salts, 0, dataSalt, bytes.length, salts.length);
        return bytes2HexString(encryptMD5(dataSalt));
    }

    /**
     * MD5加密
     *
     * @param data 明文字节数组
     * @return 密文字节数组
     */
    private static byte[] encryptMD5(final byte[] data) {
        if (data == null || data.length <= 0) return null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 二进制转字符串
     *
     * @param bytes byte[]
     * @return String
     */
    private static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }
}
