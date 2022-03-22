package com.ara.square.ui.page;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ara.base.ui.page.BaseFragment;
import com.ara.square.BR;
import com.ara.square.R;
import com.ara.square.ui.state.SquareFragmentViewModel;
import com.kunminx.architecture.ui.page.DataBindingConfig;

/**
 * Created by XieXin on 2022/2/24.
 * 广场
 */
public class SquareFragment extends BaseFragment {

    private SquareFragmentViewModel mState;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(SquareFragmentViewModel.class);
        mState.fragments.add(new NavigationFragment());
        mState.fragments.add(new NavigationFragment());
        Bundle bundle1 = new Bundle();
        bundle1.putString("type", NavigationFragment.TYPE_SYSTEM);
        mState.fragments.get(0).setArguments(bundle1);
        Bundle bundle2 = new Bundle();
        bundle2.putString("type", NavigationFragment.TYPE_NAVIGATION);
        mState.fragments.get(1).setArguments(bundle2);
        mState.titles[0] = getString(R.string.squ_system);
        mState.titles[1] = getString(R.string.squ_navigation);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.squ_fragment_square, BR.vm, mState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}