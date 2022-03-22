package com.ara.project_common.ui.page.helper;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.ara.base.R;
import com.ara.common.util.LoggerUtils;
import com.ara.network.RetrofitHolder;
import com.ara.network.bean.BaseBean;
import com.ara.network.download.DownloadInfo;
import com.ara.network.download.DownloadManager;
import com.ara.network.download.DownloadObserver;
import com.ara.project_common.data.api.UpgradeService;
import com.ara.project_common.data.bean.UpgradeBean;
import com.ara.project_common.ui.page.UpgradeDialog;
import com.ara.project_common.ui.page.adapter.PullXMLService;
import com.ara.widget.dialog.LoadingDialog;
import com.ara.widget.dialog.XPromptDialog;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subscribers.DisposableSubscriber;
import okhttp3.ResponseBody;

/**
 * Created by XieXin on 2021/5/7.
 * APP升级管理
 */
public class UpgradeManager {
    public static String APK_FILENAME = "app.apk";
    public static String UPGRADE_URL = "UPGRADE_URL NULL";

    private final AppCompatActivity activity;

    private XPromptDialog xPromptDialog;
    private final int manageId = 123336;
    private final String channelId = "x_version_update";

    private UpgradeDialog dialog;
    private UpgradeBean bean;
    /*** 加载对话框 */
    private LoadingDialog loadingDialog;
    private String apkUrl;
    private final String url;
    private final boolean isApi;

    public UpgradeManager(AppCompatActivity activity) {
        this.activity = activity;
        this.url = UPGRADE_URL;
        this.isApi = false;
    }

    public UpgradeManager(AppCompatActivity activity, boolean isApi) {
        this.activity = activity;
        this.url = UPGRADE_URL;
        this.isApi = isApi;
    }

    /**
     * 开始更新
     */
    public void startUpdate(final boolean isShowDialog) {
        if (isApi) {
            apiUpgrade(isShowDialog);
        } else {
            xmlUpgrade(isShowDialog);
        }
    }

    /**
     * xml升级
     */
    private void xmlUpgrade(final boolean isShowDialog) {
        if (isShowDialog) {
            if (loadingDialog == null)
                loadingDialog = new LoadingDialog(activity.getString(R.string.check_version));
            loadingDialog.show(activity);
        }
        RetrofitHolder.getInstance().create(UpgradeService.class).downloadFile(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        bean = PullXMLService.getUpdate(body.byteStream());
                        if (bean == null) {
                            if (isShowDialog)
                                ToastUtils.showLong(R.string.data_load_error);
                            if (isShowDialog && loadingDialog != null)
                                loadingDialog.dismiss();
                            return;
                        }
                        if (bean.getVersion() > AppUtils.getAppVersionCode()) {
                            apkUrl = bean.getUrl();
                            if (mAppUpdateListener != null)
                                mAppUpdateListener.onNewVersion(bean.isStatus());
                            if (dialog == null) {
                                dialog = new UpgradeDialog(bean,
                                        new UpgradeDialog.Callback() {
                                            @Override
                                            public void upgrade() {
                                                startDownload(bean.isStatus());
                                                ToastUtils.showLong(AppUtils.getAppName() + "下载中...");
                                                dialog.dismiss();
                                            }

                                            @Override
                                            public void cancel() {
                                                dialog.dismiss();
                                                if (bean.isStatus()) {
                                                    System.exit(0);
                                                }
                                            }
                                        });
                                dialog.setCancelable(!bean.isStatus());
                            }
                            dialog.setUpgradeBean(bean);
                            dialog.show(activity);
                        } else {
                            if (mAppUpdateListener != null) mAppUpdateListener.onNotVersion();
                            if (isShowDialog)
                                ToastUtils.showShort(R.string.is_new_version);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mAppUpdateListener != null)
                            mAppUpdateListener.onError(e.getMessage());
                        if (isShowDialog && loadingDialog != null)
                            loadingDialog.dismiss();
                        if (isShowDialog)
                            ToastUtils.showShort(R.string.download_failure);
                    }

                    @Override
                    public void onComplete() {
                        if (isShowDialog && loadingDialog != null)
                            loadingDialog.dismiss();
                    }
                });
    }

    /**
     * api升级
     */
    private void apiUpgrade(final boolean isShowDialog) {
        if (isShowDialog) {
            if (loadingDialog == null)
                loadingDialog = new LoadingDialog(activity.getString(R.string.check_version));
            loadingDialog.show(activity);
        }
        RetrofitHolder.getInstance().create(UpgradeService.class).getVersion(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<BaseBean<UpgradeBean>>() {
                    @Override
                    public void onNext(BaseBean<UpgradeBean> base) {
                        if (base == null || base.getData() == null) {
                            if (isShowDialog)
                                ToastUtils.showLong(R.string.data_load_error);
                            if (isShowDialog && loadingDialog != null)
                                loadingDialog.dismiss();
                            return;
                        }
                        if (base.getCode() != BaseBean.CODE_OK) {
                            if (isShowDialog)
                                ToastUtils.showLong(base.getMsg());
                            if (isShowDialog && loadingDialog != null)
                                loadingDialog.dismiss();
                            return;
                        }
                        bean = base.getData();
                        if (bean.getVersion() > AppUtils.getAppVersionCode()) {
                            apkUrl = bean.getUrl();
                            if (mAppUpdateListener != null)
                                mAppUpdateListener.onNewVersion(bean.isStatus());
                            if (dialog == null) {
                                dialog = new UpgradeDialog(bean,
                                        new UpgradeDialog.Callback() {
                                            @Override
                                            public void upgrade() {
                                                startDownload(bean.isStatus());
                                                ToastUtils.showLong(AppUtils.getAppName() + "下载中...");
                                                dialog.dismiss();
                                            }

                                            @Override
                                            public void cancel() {
                                                dialog.dismiss();
                                                if (bean.isStatus()) {
                                                    System.exit(0);
                                                }
                                            }
                                        });
                                dialog.setCancelable(!bean.isStatus());
                            }
                            dialog.setUpgradeBean(bean);
                            dialog.show(activity);
                        } else {
                            if (mAppUpdateListener != null) mAppUpdateListener.onNotVersion();
                            if (isShowDialog)
                                ToastUtils.showShort(R.string.is_new_version);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mAppUpdateListener != null)
                            mAppUpdateListener.onError(e.getMessage());
                        if (isShowDialog && loadingDialog != null)
                            loadingDialog.dismiss();
                        if (isShowDialog)
                            ToastUtils.showShort(R.string.download_failure);
                    }

                    @Override
                    public void onComplete() {
                        if (isShowDialog && loadingDialog != null)
                            loadingDialog.dismiss();
                    }
                });
    }

    /**
     * 开始下载
     *
     * @param isForceUpdate 是否强制更新
     */
    private void startDownload(final boolean isForceUpdate) {
        LoggerUtils.i("apkUrl " + apkUrl);
        NotificationUtils.showProgressNotification(activity,
                AppUtils.getAppName(),
                "下载中...",
                manageId,
                channelId,
                AppUtils.getAppName() + "下载",
                0);

        if (isForceUpdate) {
            xPromptDialog = XPromptDialog.Builder.create(activity)
                    .content(AppUtils.getAppName() + "下载中...")
                    .cancelable(false)
                    .dismiss(false)
                    .textPositive(R.string.immediately_update)
                    .onPositive(d -> {
                        NotificationUtils.cancelNotification(manageId);
                        installApk();
                    })
                    .textNegative(bean.isStatus() ? R.string.exit : R.string.cancel)
                    .onNegative(d -> {
                        if (bean.isStatus()) {
                            System.exit(0);
                        } else {
                            d.dismiss();
                        }
                    })
                    .show();
        }
        DownloadManager.getInstance().download(apkUrl, PathUtils.getInternalAppCachePath() + File.separator + "apk",
                UpgradeManager.APK_FILENAME, new DownloadObserver() {
                    @Override
                    public void onNext(@NonNull DownloadInfo value) {
                        super.onNext(value);
                        NotificationUtils.showProgressNotification(activity,
                                AppUtils.getAppName(),
                                "下载中...",
                                manageId,
                                channelId,
                                AppUtils.getAppName() + "下载",
                                (int) (value.getProgress() * 100 / value.getTotal()));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        super.onError(e);
                        ToastUtils.showLong("下载失败");
                        NotificationUtils.cancelNotification(manageId);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        NotificationUtils.showProgressNotification(activity,
                                AppUtils.getAppName(),
                                "下载完成，点击安装",
                                manageId,
                                channelId,
                                AppUtils.getAppName() + "下载",
                                100);

                        if (isForceUpdate && xPromptDialog != null) {
                            xPromptDialog = XPromptDialog.Builder.create(activity)
                                    .content(AppUtils.getAppName() + "下载完成")
                                    .cancelable(false)
                                    .dismiss(false)
                                    .textPositive(R.string.immediately_update)
                                    .onPositive(v -> {
                                        NotificationUtils.cancelNotification(manageId);
                                        installApk();
                                    })
                                    .show();
                        }
                        installApk();
                    }
                });
//        DownloadTask downloadTask = new DownloadTask.Builder(apkUrl, FileUtils.getApkCacheDir(activity), APK_FILENAME)
//                // the minimal interval millisecond for callback progress
//                .setMinIntervalMillisCallbackProcess(30)
//                // do re-download even if the task has already been completed in the past.
//                .setPassIfAlreadyCompleted(false)
//                .build();
//        downloadTask.enqueue(new DownloadListener1() {
//            @Override
//            public void taskStart(@NonNull DownloadTask task, @NonNull Listener1Assist.Listener1Model model) {
//                LoggerUtils.i("taskStart");
//
//            }
//
//            @Override
//            public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {
//                LoggerUtils.i("retry");
//
//            }
//
//            @Override
//            public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {
//                LoggerUtils.i("connected");
//
//            }
//
//            @Override
//            public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
//                int p = (int) ((double) currentOffset / totalLength * 100f);
//                LoggerUtils.i("progress Total: " + totalLength + " Download: " + currentOffset + " 进度：" + p + " % ");
//                NotificationUtils.showProgressNotification(activity,
//                        AppUtils.getAppName(activity),
//                        "下载中...",
//                        manageId,
//                        channelId,
//                        AppUtils.getAppName(activity) + "下载",
//                        p);
//            }
//
//            @Override
//            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull Listener1Assist.Listener1Model model) {
//                LoggerUtils.i("taskEnd Total: " + model.getTotalLength());
//                NotificationUtils.showProgressNotification(activity,
//                        AppUtils.getAppName(activity),
//                        "下载完成，点击安装",
//                        manageId,
//                        channelId,
//                        AppUtils.getAppName(activity) + "下载",
//                        100);
//
//                if (isForceUpdate && xPromptDialog != null && !xPromptDialog.isShowing()) {
//                    xPromptDialog = new XPromptDialog.Builder(activity)
//                            .content(AppUtils.getAppName(activity) + "下载完成")
//                            .cancelable(false)
//                            .dismiss(false)
//                            .textPositive(R.string.immediately_update)
//                            .onPositive(v -> {
//                                NotificationUtils.cancelNotification(manageId);
//                                installApk();
//                            })
//                            .show();
//                }
//                installApk();
//
//            }
//        });
    }

    /**
     * 安装apk
     */
    // 安装
    public void installApk() {
        File apkFile = new File(getApkPath());
        if (apkFile.exists()) {
            openFile(apkFile);
        } else {
            ToastUtils.showShort(R.string.download_failure);
        }
    }

    public void openFile(File file) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileProvider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        activity.startActivity(intent);
    }

    private String getMIMEType(File file) {
        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    public static String getApkPath() {
        return PathUtils.getInternalAppCachePath() + File.separator + "apk" + File.separator + UpgradeManager.APK_FILENAME;
    }

    private AppUpdateListener mAppUpdateListener;

    public void setAppUpdateListener(AppUpdateListener listener) {
        this.mAppUpdateListener = listener;
    }

    public interface AppUpdateListener {
        void onNewVersion(boolean status);

        void onNotVersion();

        void onError(String msg);
    }
}
