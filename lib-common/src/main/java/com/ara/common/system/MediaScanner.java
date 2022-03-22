package com.ara.common.system;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

/**
 * Created by XieXin on 2021/5/19.
 * 更新多媒体数据库
 */
public class MediaScanner {

    private MediaScannerConnection mediaScanConn = null;
    private MediaScannerClient client = null;
    private String filePath = null;
    private String fileType = null;
    private static MediaScanner mediaScanner = null;

    private MediaScannerConnection.OnScanCompletedListener onScanCompletedListener;

    /**
     * 然后调用MediaScanner.scanFile("/sdcard/2.mp3");
     */

    public MediaScanner(Context context) {
        // 创建MusicScannerClient
        if (client == null) {
            client = new MediaScannerClient();
        }
        if (mediaScanConn == null) {
            mediaScanConn = new MediaScannerConnection(context, client);
        }
    }

    public static MediaScanner getInstance(Context context) {
        if (mediaScanner == null) {
            mediaScanner = new MediaScanner(context);
        }
        return mediaScanner;
    }

    private class MediaScannerClient implements MediaScannerConnection.MediaScannerConnectionClient {

        public void onMediaScannerConnected() {
            if (filePath != null) {
                mediaScanConn.scanFile(filePath, fileType);
            }
            filePath = null;
            fileType = null;
        }

        public void onScanCompleted(String path, Uri uri) {
            if (onScanCompletedListener != null) onScanCompletedListener.onScanCompleted(path, uri);
            System.out.println("扫描成功：" + path);
            mediaScanConn.disconnect();
        }

    }

    /**
     * 扫描文件标签信息
     *
     * @param filePath 文件路径 eg:/sdcard/MediaPlayer/dahai.mp3
     * @param fileType 文件类型 eg: audio/mp3 media/* application/ogg
     */

    public void scanFile(String filePath, String fileType) {
        this.filePath = filePath;
        this.fileType = fileType;
        // 连接之后调用MusicSannerClient的onMediaScannerConnected()方法
        mediaScanConn.connect();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public MediaScannerConnection getMediaScanConn() {
        return mediaScanConn;
    }

    public void setMediaScanConn(MediaScannerConnection mediaScanConn) {
        this.mediaScanConn = mediaScanConn;
    }

    public MediaScannerClient getClient() {
        return client;
    }

    public void setClient(MediaScannerClient client) {
        this.client = client;
    }

    public static MediaScanner getMediaScanner() {
        return mediaScanner;
    }

    public static void setMediaScanner(MediaScanner mediaScanner) {
        MediaScanner.mediaScanner = mediaScanner;
    }

    public MediaScannerConnection.OnScanCompletedListener getOnScanCompletedListener() {
        return onScanCompletedListener;
    }

    public void setOnScanCompletedListener(MediaScannerConnection.OnScanCompletedListener onScanCompletedListener) {
        this.onScanCompletedListener = onScanCompletedListener;
    }
}
