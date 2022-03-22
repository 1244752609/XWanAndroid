package com.ara.common.system;

import android.content.Context;
import android.os.Looper;

import com.ara.common.util.DateUtils;
import com.ara.common.util.LoggerUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.PathUtils;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by XieXin on 2020/6/30.
 * 全局异常捕获
 */
public class GlobalCatchException implements Thread.UncaughtExceptionHandler {
    //本类实例
    private static GlobalCatchException mInstance;
    //系统默认的uncatchException
    private Thread.UncaughtExceptionHandler mDefaultException;

    private Context mContext;
    private boolean isDebug;

    //单例模式
    public static GlobalCatchException getInstance() {
        if (mInstance == null) mInstance = new GlobalCatchException();
        return mInstance;
    }

    //获取系统默认的异常处理器,并且设置本类为系统默认处理器
    public void init(Context ctx, boolean isDebug) {
        this.mContext = ctx;
        this.isDebug = isDebug;
        mDefaultException = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    //自定义错误处理器
    private boolean handlerException(Throwable ex) {
        if (ex == null) {  //如果已经处理过这个Exception,则让系统处理器进行后续关闭处理
            return false;
        }

        //获取错误原因
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        final String msg = result;

        new Thread() {
            public void run() {
                // Toast 显示需要出现在一个线程的消息队列中
                Looper.prepare();
                if (isDebug) {
                    ToastUtils.showLong(mContext, "程序出错，保存路径:" + PathUtils.getInternalAppCachePath() + "/log\n" + msg);
                    LoggerUtils.e("程序出错，保存路径:" + PathUtils.getInternalAppCachePath() + "log\n" + msg);
                }
                //将异常记录到本地的数据库或者文件中.或者直接提交到后台服务器
                String filename = DateUtils.millis2String(System.currentTimeMillis()) + ".log";
                FileIOUtils.writeFileFromString(PathUtils.getInternalAppCachePath() + File.separator + "log" +
                        File.separator + filename, msg);
                Looper.loop();
            }
        }.start();
        return true;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handlerException(ex) && mDefaultException != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultException.uncaughtException(thread, ex);
        } else { //否则自己进行处理
            try {  //Sleep 来让线程停止一会是为了显示Toast信息给用户，然后Kill程序
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                String filename = DateUtils.millis2String(System.currentTimeMillis()) + ".log";
                FileIOUtils.writeFileFromString(PathUtils.getInternalAppCachePath() + File.separator + "log" +
                        File.separator + filename, e.getMessage());
                LoggerUtils.d("uncaughtException: " + e.getMessage());
            } catch (Exception e) {
                String filename = DateUtils.millis2String(System.currentTimeMillis()) + ".log";
                FileIOUtils.writeFileFromString(PathUtils.getInternalAppCachePath() + File.separator + "log" +
                        File.separator + filename, e.getMessage());
                LoggerUtils.d("Exception: " + e.getMessage());
            }
            //如果不关闭程序,会导致程序无法启动,需要完全结束进程才能重新启动
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }

    }
}
