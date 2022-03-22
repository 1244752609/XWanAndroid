package com.ara.common.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.ara.common.util.LoggerUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by XieXin on 2018/12/10.
 * 图片工具类
 */
public class ImageUtils {

    private ImageUtils() {
    }

    /**
     * 图片路径转Base64
     *
     * @param imgPath 图片路径
     * @return String
     */
    public static String toBase64(String imgPath) {
        if (TextUtils.isEmpty(imgPath)) {
            return "";
        }
        Bitmap bitmap;
        bitmap = readBitmap(imgPath);
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
            }

            out.flush();
            out.close();

            byte[] imgBytes = out.toByteArray();
            LoggerUtils.i(((float) imgBytes.length / 1024) + "KB************" + ((float) imgBytes.length / 1024 / 1024) + "MB");
            String base64String = Base64.encodeToString(imgBytes, Base64.DEFAULT);
//            LoggerUtils.e(base64String);
            return base64String;
        } catch (Exception e) {
            LoggerUtils.e(e.getMessage());
            return null;
        } finally {
            try {
                if (out != null) out.flush();
                if (out != null) out.close();
            } catch (IOException e) {
                LoggerUtils.e(e.getMessage());
            }
        }
    }

    private static Bitmap readBitmap(String imgPath) {
        try {
            return BitmapFactory.decodeFile(imgPath);
        } catch (Exception e) {
            LoggerUtils.e(e.getMessage());
            return null;
        }

    }

    /**
     * Base64转Bitmap
     *
     * @param base64 Base64
     */
    public static Bitmap base64ToBitmap(String base64) {
        byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * 获取图片类型
     *
     * @param filePath 路径
     * @return int
     */
    public static int getImageType(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        String type = options.outMimeType;
        LoggerUtils.i(filePath + "\n image type " + type);
        if (TextUtils.equals(type, "image/png")) {
            return 0;
        } else if (TextUtils.equals(type, "image/jpeg")) {
            return 1;
        } else if (TextUtils.equals(type, "image/gif")) {
            return 2;
        } else if (TextUtils.equals(type, "image/tif")) {
            return 3;
        } else if (TextUtils.equals(type, "image/bmp")) {
            return 4;
        } else {
            return -1;
        }
    }

    /**
     * Bitmap转Bytes
     *
     * @param bm Bitmap
     * @return byte[
     */
    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        } catch (Exception e) {
            Log.e("ImageUtils", e.getMessage());
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    Log.e("ImageUtils", e.getMessage());
                }
            }
        }
        if (baos == null) {
            return new byte[]{};
        } else {
            return baos.toByteArray();
        }
    }
    /**
     * Bitmap转Bytes
     *
     * @param bm Bitmap
     * @return byte[
     */
    public static byte[] bitmap2Bytes(Bitmap bm, Bitmap.CompressFormat format) {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bm.compress(format, 100, baos);
        } catch (Exception e) {
            Log.e("ImageUtils", e.getMessage());
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    Log.e("ImageUtils", e.getMessage());
                }
            }
        }
        if (baos == null) {
            return new byte[]{};
        } else {
            return baos.toByteArray();
        }
    }

    /**
     * Bytes转Bitmap
     *
     * @param b bate数组
     * @return Bitmap
     */
    public static Bitmap bytes2Bitmap(byte[] b) {
        if (b == null || b.length == 0) return null;
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }


    /**
     * 将文件转换成byte数组
     *
     * @param filePath 文件File类 通过new File(文件路径)
     * @return byte数组
     */
    public static byte[] file2byte(File filePath) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(filePath);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    //把白色转换成透明
    public static Bitmap getImageToChange(Bitmap mBitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        if (mBitmap != null) {
            int mWidth = mBitmap.getWidth();
            int mHeight = mBitmap.getHeight();
            int colorStart = mBitmap.getPixel(0, 0);
            int colorEnd = mBitmap.getPixel(mWidth - 1, mHeight - 1);
            int minStart = Math.min(Color.green(colorStart), Color.blue(colorStart));
            int minEnd = Math.min(Color.green(colorEnd), Color.blue(colorEnd));
            int min = Math.min(minStart, minEnd);
            if (min > 150) min -= 20;
            if (min <= 130) min += 10;
            Log.i("FileUtils", "----- min" + min);
            for (int i = 0; i < mHeight; i++) {
                for (int j = 0; j < mWidth; j++) {
                    int color = mBitmap.getPixel(j, i);
                    int g = Color.green(color);
                    int r = Color.red(color);
                    int b = Color.blue(color);
                    int a = Color.alpha(color);
                    if (i == 0 && j == 0) {
                        Log.i("FileUtils", "----- r" + r + " g" + g + " b" + b + " a" + a);
                    }
                    if (r >= 100) {
                        if (g >= min || b >= min)
                            a = 0;
                        if (r < 200) r = 220;
                    } else {
                        a = 0;
                    }
                    color = Color.argb(a, r, g, b);
                    createBitmap.setPixel(j, i, color);
                }
            }
        }
        return createBitmap;
    }
}
