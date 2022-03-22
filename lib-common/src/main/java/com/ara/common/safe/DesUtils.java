package com.ara.common.safe;

import android.text.TextUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by XieXin on 2018/12/10.
 * Des加解密工具类
 */
public class DesUtils {
    private DesUtils() {
    }

    /**
     * 加密
     *
     * @param srcStr 加密字符串
     * @param sKey   秘钥
     * @return String
     */
    public static String encrypt(String srcStr, String sKey) {
        if (TextUtils.isEmpty(srcStr) || TextUtils.isEmpty(sKey)) {
            return "";
        }
        byte[] src = srcStr.getBytes(StandardCharsets.UTF_8);
        byte[] buf = encrypt(src, sKey);
        return parseByte2HexStr(buf);
    }

    /**
     * 解密
     *
     * @param hexStr 解密字符串
     * @param sKey   秘钥
     * @return String
     * @throws Exception String
     */
    public static String decrypt(String hexStr, String sKey) throws Exception {
        if (TextUtils.isEmpty(hexStr) || TextUtils.isEmpty(sKey)) {
            return "";
        }
        byte[] src = parseHexStr2Byte(hexStr);
        byte[] buf = decrypt(src, sKey);
        return new String(buf, StandardCharsets.UTF_8);
    }

    /**
     * 加密
     *
     * @param srcStr  加密字符串
     * @param charset 字符编码
     * @param sKey    秘钥
     * @return String
     */
    public static String encrypt(String srcStr, Charset charset, String sKey) {
        if (TextUtils.isEmpty(srcStr) || TextUtils.isEmpty(sKey)) {
            return "";
        }
        byte[] src = srcStr.getBytes(charset);
        byte[] buf = encrypt(src, sKey);
        return parseByte2HexStr(buf);
    }

    /**
     * 解密
     *
     * @param hexStr 解密字符串
     * @param sKey   秘钥
     * @return String
     * @throws Exception Exception
     */
    public static String decrypt(String hexStr, Charset charset, String sKey) throws Exception {
        if (TextUtils.isEmpty(hexStr) || TextUtils.isEmpty(sKey) || charset == null) {
            return "";
        }
        byte[] src = parseHexStr2Byte(hexStr);
        byte[] buf = decrypt(src, sKey);
        return new String(buf, charset);
    }


    /**
     * 加密
     *
     * @param bytes byte[]
     * @param sKey  秘钥
     * @return byte[]
     */
    public static byte[] encrypt(byte[] bytes, String sKey) {
        if (bytes == null || TextUtils.isEmpty(sKey)) {
            return null;
        }
        try {
            byte[] key = sKey.getBytes();
            // 初始化向量
            IvParameterSpec iv = new IvParameterSpec(key);
            DESKeySpec desKey = new DESKeySpec(key);
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成securekey
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);
            // 现在，获取数据并加密
            // 正式执行加密操作
            return cipher.doFinal(bytes);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param bytes byte[]
     * @param sKey  秘钥
     * @return byte[]
     * @throws Exception Exception
     */
    public static byte[] decrypt(byte[] bytes, String sKey) throws Exception {
        if (bytes == null || TextUtils.isEmpty(sKey)) {
            return null;
        }
        byte[] key = sKey.getBytes();
        // 初始化向量
        IvParameterSpec iv = new IvParameterSpec(key);
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(key);
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
        // 真正开始解密操作
        return cipher.doFinal(bytes);
    }

    /**
     * 将二进制转换成16进制
     *
     * @param bytes byte[]
     * @return String
     */
    public static String parseByte2HexStr(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr 字符串
     * @return byte[]
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (TextUtils.isEmpty(hexStr)) {
            return null;
        }
        if (hexStr.length() < 1) return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
