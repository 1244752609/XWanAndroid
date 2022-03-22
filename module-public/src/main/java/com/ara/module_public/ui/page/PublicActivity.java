package com.ara.module_public.ui.page;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ara.module_public.R;
import com.ara.module_public.data.api.PublicRouterApi;

/**
 * Created by XieXin on 2022/2/24.
 * 复制模块Main
 */
@Route(path = PublicRouterApi.API_PUBLIC)
public class PublicActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_activity_public);
    }
}
