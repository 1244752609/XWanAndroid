package com.ara.square.ui.page;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ara.square.R;
import com.ara.square.data.api.SquareRouterApi;

/**
 * Created by XieXin on 2022/2/24.
 * 复制模块Main
 */
@Route(path = SquareRouterApi.API_SQUARE)
public class SquareMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.squ_activity_main);
    }
}