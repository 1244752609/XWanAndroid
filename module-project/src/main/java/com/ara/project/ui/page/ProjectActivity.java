package com.ara.project.ui.page;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ara.project.R;
import com.ara.project.data.api.ProjectRouterApi;

/**
 * Created by XieXin on 2022/2/24.
 * 复制模块Main
 */
@Route(path = ProjectRouterApi.API_PROJECT)
public class ProjectActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pro_activity_project);
    }
}
