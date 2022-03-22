package com.ara.network.download;

import android.util.Log;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * Created by XieXin on 2021/12/1.
 * 下载观察者
 */
public class DownloadObserver implements Observer<DownloadInfo> {
    private final String TAG = DownloadObserver.class.getSimpleName();
    public Disposable d;//可以用于取消注册的监听者
    public DownloadInfo downloadInfo;

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.d = d;
    }

    @Override
    public void onNext(@NonNull DownloadInfo value) {
        Log.d(TAG, "onNext progress:" + value.getProgress() + "/" + value.getTotal());
        this.downloadInfo = value;
        downloadInfo.setDownloadStatus(DownloadInfo.DOWNLOAD);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        Log.e(TAG, "onError " + e.getMessage());
        if (DownloadManager.getInstance().getDownloadUrl(downloadInfo.getUrl())) {
            DownloadManager.getInstance().pauseDownload(downloadInfo.getUrl());
            downloadInfo.setDownloadStatus(DownloadInfo.DOWNLOAD_ERROR);
        } else {
            downloadInfo.setDownloadStatus(DownloadInfo.DOWNLOAD_PAUSE);
        }

    }

    @Override
    public void onComplete() {
        Log.d(TAG, "onComplete");
        if (downloadInfo != null) {
            downloadInfo.setDownloadStatus(DownloadInfo.DOWNLOAD_OVER);
        }
    }
}
