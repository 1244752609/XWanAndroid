package com.ara.account.ui.state;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.ara.account.domain.request.AccountRequest;
import com.ara.base.data.response.LoadState;

/**
 * Created by XieXin on 2022/3/3.
 * 注册
 */
public class RegisterActivityViewModel extends ViewModel {
    public final ObservableField<String> username = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();
    public final ObservableField<String> repassword = new ObservableField<>();
    public final ObservableField<LoadState> loadState = new ObservableField<>();

    public final AccountRequest accountRequest = new AccountRequest();
}
