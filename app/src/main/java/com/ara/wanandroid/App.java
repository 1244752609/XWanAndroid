package com.ara.wanandroid;

import android.content.Context;
import android.view.Gravity;

import androidx.core.content.ContextCompat;
import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.ara.base.BaseApplication;
import com.ara.common.system.GlobalCatchException;
import com.ara.project_common.R;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/**
 * Created by XieXin on 2022/2/16.
 */
public class App extends BaseApplication {
    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
            return new MaterialHeader(context).setColorSchemeResources(R.color.colorPrimary);
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context);
        });
    }

    @Override
    protected void init() {
        ToastUtils.getDefaultMaker()
                .setGravity(Gravity.CENTER, 0, 0)
                .setBgColor(ContextCompat.getColor(this, com.ara.base.R.color.black_81_transparent))
                .setTextColor(ContextCompat.getColor(this, com.ara.base.R.color.white));
        GlobalCatchException.getInstance().init(this, BuildConfig.DEBUG);
        initARouter();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //初始化DEX
        MultiDex.install(this);
    }

    /**
     * 初始化路由
     */
    private void initARouter() {
        if (BuildConfig.DEBUG) {// 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();// 打印日志
            ARouter.openDebug();// 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);// 尽可能早，推荐在Application中初始化
    }

    @Override
    protected boolean isLoggable() {
        return BuildConfig.DEBUG;
    }

    @Override
    protected String getBaseUrl() {
        return BuildConfig.BASE_URL;
    }
}
