package com.ara.network.download;

import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.FileUtils;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by XieXin on 2021/12/1.
 * DownloadManager
 */
public class DownloadManager {
    private static final AtomicReference<DownloadManager> INSTANCE = new AtomicReference<>();
    private final OkHttpClient mClient;
    private final HashMap<String, Call> downCalls; //用来存放各个下载的请求
    private String savePath; //保存路径

    public static DownloadManager getInstance() {
        DownloadManager current = INSTANCE.get();
        if (current != null) {
            return current;
        }
        current = new DownloadManager();
        INSTANCE.compareAndSet(null, current);
        return current;
    }

    private DownloadManager() {
        downCalls = new HashMap<>();
        mClient = new OkHttpClient.Builder().build();
    }

    /**
     * 查看是否在下载任务中
     *
     * @param url
     * @return
     */
    public boolean getDownloadUrl(String url) {
        return downCalls.containsKey(url);
    }

    /**
     * 开始下载
     *
     * @param url              下载请求的网址
     * @param savePath         保存地址
     * @param downLoadObserver 用来回调的接口
     */
    public void download(String url, String savePath, DownloadObserver downLoadObserver) {
        this.savePath = savePath;
        Log.d("download", "url: " + url);
        Log.d("download", "savePath: " + savePath);
        Observable.just(url)
                // 过滤 call的map中已经有了,就证明正在下载,则这次不下载
                .filter(s -> !downCalls.containsKey(s))
                // 生成 DownloadInfo
                .flatMap((Function<String, ObservableSource<?>>)
                        s -> Observable.just(createDownInfo(s)))
                // 如果已经下载，重新命名
                .map(o -> getRealFileName((DownloadInfo) o, null))
                // 下载
                .flatMap((Function<DownloadInfo, ObservableSource<DownloadInfo>>)
                        downloadInfo -> {
                            Log.i("download", "Observable.create(new DownloadSubscribe(downloadInfo))");
                            return Observable.create(new DownloadSubscribe(downloadInfo));
                        })
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程中回调
                .subscribeOn(Schedulers.io()) //  在子线程中执行
                .subscribe(downLoadObserver); //  添加观察者，监听下载进度
    }

    /**
     * 开始下载
     *
     * @param url              下载请求的网址
     * @param savePath         保存地址
     * @param downLoadObserver 用来回调的接口
     */
    public void download(String url, String savePath, String filename, DownloadObserver downLoadObserver) {
        this.savePath = savePath;
        Log.d("download", "url: " + url);
        Log.d("download", "savePath: " + savePath);
        Observable.just(url)
                // 过滤 call的map中已经有了,就证明正在下载,则这次不下载
                .filter(s -> !downCalls.containsKey(s))
                // 生成 DownloadInfo
                .flatMap((Function<String, ObservableSource<?>>)
                        s -> Observable.just(createDownInfo(s)))
                // 固定文件名
                .map(o -> getRealFileName((DownloadInfo) o, filename))
                // 下载
                .flatMap((Function<DownloadInfo, ObservableSource<DownloadInfo>>)
                        downloadInfo -> {
                            Log.i("download", "Observable.create(new DownloadSubscribe(downloadInfo))");
                            return Observable.create(new DownloadSubscribe(downloadInfo));
                        })
                .observeOn(AndroidSchedulers.mainThread()) // 在主线程中回调
                .subscribeOn(Schedulers.io()) //  在子线程中执行
                .subscribe(downLoadObserver); //  添加观察者，监听下载进度
    }

    /**
     * 下载取消或者暂停
     *
     * @param url
     */
    public void pauseDownload(String url) {
        Call call = downCalls.get(url);
        if (call != null) {
            call.cancel();//取消
        }
        downCalls.remove(url);
    }

    /**
     * 取消下载 删除本地文件
     *
     * @param info
     */
    public void cancelDownload(DownloadInfo info) {
        pauseDownload(info.getUrl());
        info.setProgress(0);
        info.setDownloadStatus(DownloadInfo.DOWNLOAD_CANCEL);
        FileUtils.delete(savePath + info.getFileName());
    }

    /**
     * 创建DownInfo
     *
     * @param url 请求网址
     * @return DownInfo
     */
    private DownloadInfo createDownInfo(String url) {
        DownloadInfo downloadInfo = new DownloadInfo(url);
        long contentLength = getContentLength(url);//获得文件大小
        downloadInfo.setTotal(contentLength);
        String fileName = url.substring(url.lastIndexOf("/"));
        downloadInfo.setFileName(fileName);
        return downloadInfo;
    }

    /**
     * 如果文件已下载重新命名新文件名
     *
     * @param downloadInfo
     * @return
     */
    private DownloadInfo getRealFileName(DownloadInfo downloadInfo, String filename) {
        if (!TextUtils.isEmpty(filename)) {
            downloadInfo.setFileName(filename);
            FileUtils.delete(savePath + File.separator + filename);
            return downloadInfo;
        }

        String fileName = downloadInfo.getFileName();
        long downloadLength = 0, contentLength = downloadInfo.getTotal();
        File path = new File(savePath);
        if (!path.exists()) {
            path.mkdir();
        }
        File file = new File(savePath, fileName);
        if (file.exists()) {
            //找到了文件,代表已经下载过,则获取其长度
            downloadLength = file.length();
        }
        //之前下载过,需要重新来一个文件
        int i = 1;
        while (downloadLength >= contentLength) {
            int dotIndex = fileName.lastIndexOf(".");
            String fileNameOther;
            if (dotIndex == -1) {
                fileNameOther = fileName + "(" + i + ")";
            } else {
                fileNameOther = fileName.substring(0, dotIndex)
                        + "(" + i + ")" + fileName.substring(dotIndex);
            }
            File newFile = new File(savePath, fileNameOther);
            file = newFile;
            downloadLength = newFile.length();
            i++;
        }
        //设置改变过的文件名/大小
        downloadInfo.setProgress(downloadLength);
        downloadInfo.setFileName(file.getName());
        return downloadInfo;
    }

    private class DownloadSubscribe implements ObservableOnSubscribe<DownloadInfo> {
        private final DownloadInfo downloadInfo;

        public DownloadSubscribe(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
        }

        @Override
        public void subscribe(ObservableEmitter<DownloadInfo> e) throws Exception {
            String url = downloadInfo.getUrl();
            long downloadLength = downloadInfo.getProgress();//已经下载好的长度
            long contentLength = downloadInfo.getTotal();//文件的总长度
            //初始进度信息
            e.onNext(downloadInfo);
            Request request = new Request.Builder()
                    //确定下载的范围,添加此头,则服务器就可以跳过已经下载好的部分
                    .addHeader("RANGE", "bytes=" + downloadLength + "-" + contentLength)
                    .url(url)
                    .build();
            Call call = mClient.newCall(request);
            downCalls.put(url, call);//把这个添加到call里,方便取消
            Response response = call.execute();
            if (!response.isSuccessful()) {
                e.onError(new Exception("下载失败: code=" + response.code() + ", msg=" + response.message()));
                return;
            }
            File file = new File(savePath, downloadInfo.getFileName());
            InputStream is = null;
            FileOutputStream fileOutputStream = null;
            try {
                is = response.body().byteStream();
                fileOutputStream = new FileOutputStream(file, true);
                byte[] buffer = new byte[1024 * 4];//缓冲数组2kB
                int len;
                while ((len = is.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, len);
                    downloadLength += len;
                    int curProgress = (int) (downloadLength * 100 / downloadInfo.getTotal());
                    int oldProgress = (int) (downloadInfo.getProgress() * 100 / downloadInfo.getTotal());
                    if (curProgress - oldProgress >= 1) {
                        downloadInfo.setProgress(downloadLength);
                        e.onNext(downloadInfo);
                    }
                }
                fileOutputStream.flush();
                downCalls.remove(url);
            } finally {
                //关闭IO流
                closeStream(is, fileOutputStream);

            }
            e.onComplete();//完成
        }
    }

    /**
     * 获取下载长度
     *
     * @param downloadUrl 下载链接
     * @return 文件大小
     */
    private long getContentLength(String downloadUrl) {
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        try {
            Response response = mClient.newCall(request).execute();
            if (response.isSuccessful()) {
                long contentLength = response.body().contentLength();
                response.close();
                return contentLength == 0 ? DownloadInfo.TOTAL_ERROR : contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DownloadInfo.TOTAL_ERROR;
    }

    /**
     * 关闭IO流
     *
     * @param closeables 所有流
     */
    public static void closeStream(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
