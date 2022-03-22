package com.ara.common.system;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

/**
 * Created by XieXin on 2019/3/22.
 * 视频工具类
 */
public class VideoUtils {
    private VideoUtils() {
    }

    /**
     * 获取第一个关键帧
     *
     * @param path 本地路径
     * @return Bitmap
     */
    public static Bitmap getFirstFps(String path) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path);
        return retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
    }

    /**
     * 获取最大关键帧
     *
     * @param path 本地路径
     * @return Bitmap
     */
    public static Bitmap getMaxFps(String path) {
        return ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MINI_KIND);
    }

    /**
     * 删除文件
     *
     * @param path 本地路径
     * @return boolean
     */
    public static boolean deleteVideo(String path) {
        if (TextUtils.isEmpty(path)) {
            Log.i("VideoUtils", "path not");
            return true;
        }
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        } else {
            return true;
        }
    }
}
