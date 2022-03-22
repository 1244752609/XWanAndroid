package com.ara.home.ui.page;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableArrayList;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.ara.base.ui.page.BaseActivity;
import com.ara.db.entity.SearchRecord;
import com.ara.home.BR;
import com.ara.home.R;
import com.ara.home.data.api.HomeRouterApi;
import com.ara.home.data.bean.HotBean;
import com.ara.home.ui.page.adapter.SearchHotAdapter;
import com.ara.home.ui.page.adapter.SearchRecordAdapter;
import com.ara.home.ui.state.SearchActivityViewModel;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.kunminx.architecture.ui.page.DataBindingConfig;

/**
 * Created by XieXin on 2022/3/10.
 * 搜索页面
 */
@Route(path = HomeRouterApi.API_SEARCH)
public class SearchActivity extends BaseActivity {
    private SearchActivityViewModel mState;

    private FlexboxLayoutManager recordLayoutManager;
    private SearchRecordAdapter recordAdapter;

    private FlexboxLayoutManager hotLayoutManager;
    private SearchHotAdapter hotAdapter;

    private final ObservableArrayList<HotBean> hotData = new ObservableArrayList<>();
    private final ObservableArrayList<SearchRecord> searchRecordData = new ObservableArrayList<>();

    private TextView.OnEditorActionListener onEditorActionListener;

    @Override
    protected void initViewModel() {
        mState = getActivityScopeViewModel(SearchActivityViewModel.class);

        recordLayoutManager = new FlexboxLayoutManager(this);
        recordLayoutManager.setFlexWrap(FlexWrap.WRAP);
        recordLayoutManager.setFlexDirection(FlexDirection.ROW);
        recordLayoutManager.setAlignItems(AlignItems.STRETCH);
        recordLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recordAdapter = new SearchRecordAdapter();
        recordAdapter.refresh(searchRecordData);

        hotLayoutManager = new FlexboxLayoutManager(this);
        hotLayoutManager.setFlexWrap(FlexWrap.WRAP);
        hotLayoutManager.setFlexDirection(FlexDirection.ROW);
        hotLayoutManager.setAlignItems(AlignItems.STRETCH);
        hotLayoutManager.setJustifyContent(JustifyContent.FLEX_START);

        hotAdapter = new SearchHotAdapter();
        hotAdapter.refresh(hotData);

        onEditorActionListener = (v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String keyword = v.getText().toString();
                if (TextUtils.isEmpty(keyword)) {
                    ToastUtils.showLong(R.string.home_input_search_keyword);
                    return true;
                }
                mState.searchRequest.insertSearchRecord(new SearchRecord(v.getText().toString()));
                mState.searchRequest.getSearchRecord();
                HomeRouterApi.jumpSearchListActivity(keyword);
            }
            return true;
        };
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.home_activity_search, BR.vm, mState)
                .addBindingParam(BR.click, new ClickProxy())
                .addBindingParam(BR.onEditorActionListener, onEditorActionListener)
                .addBindingParam(BR.recordAdapter, recordAdapter)
                .addBindingParam(BR.recordLayoutManager, recordLayoutManager)
                .addBindingParam(BR.hotAdapter, hotAdapter)
                .addBindingParam(BR.hotLayoutManager, hotLayoutManager);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(mState.searchRequest);

        recordAdapter.setOnItemLongClickListener((v, homeItemLabelBinding, position) -> {
            mState.searchRequest.deleteSearchRecord(searchRecordData.get(position).id);
            mState.searchRequest.getSearchRecord();
        });

        recordAdapter.setOnItemClickListener((view, homeItemLabelBinding, position) -> {
            HomeRouterApi.jumpSearchListActivity(searchRecordData.get(position).content);
        });
        hotAdapter.setOnItemClickListener((view, homeItemLabelBinding, position) -> {
            HomeRouterApi.jumpSearchListActivity(hotData.get(position).getName());
        });

        mState.searchRequest.getSearchRecordLiveData().observe(this, result -> {
            if (!result.isSuccess()) {
                ToastUtils.showLong(result.getError().getMsg());
                return;
            }
            searchRecordData.clear();
            searchRecordData.addAll(result.getResult());
        });
        mState.searchRequest.getHotLiveData().observe(this, result -> {
            if (!result.isSuccess()) {
                ToastUtils.showLong(result.getError().getMsg());
                return;
            }
            hotData.clear();
            hotData.addAll(result.getResult().getData());
        });

        mState.searchRequest.getSearchRecord();
        mState.searchRequest.getHotSearch();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mState.searchRequest.getSearchRecord();
    }

    public class ClickProxy {
        public void back() {
            finish();
        }

        public void cleanRecord() {
            mState.searchRequest.deleteAllSearchRecord();
            searchRecordData.clear();
        }
    }

}