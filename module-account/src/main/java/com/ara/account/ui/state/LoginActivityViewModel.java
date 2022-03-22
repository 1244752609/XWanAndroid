package com.ara.account.ui.state;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.ara.account.domain.request.AccountRequest;
import com.ara.base.data.response.LoadState;

/**
 * Created by XieXin on 2022/2/18.
 * 启动页
 * 每个页面都要单独准备一个 state-ViewModel，
 * 来托管 DataBinding 绑定的临时状态，以及视图控制器重建时状态的恢复。
 * <p>
 * 此外，state-ViewModel 的职责仅限于 状态托管，不建议在此处理 UI 逻辑，
 * UI 逻辑只适合在 Activity/Fragment 等视图控制器中完成，是 “数据驱动” 的一部分，
 * 将来升级到 Jetpack Compose 更是如此。
 * <p>
 */
public class LoginActivityViewModel extends ViewModel {
    public final ObservableField<String> username = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();
    public final ObservableBoolean isReLogin = new ObservableBoolean();
    public final ObservableField<LoadState> loadState = new ObservableField<>();
    // 将 request 作为 ViewModel 的成员暴露给 Activity/Fragment，
    // 如此便于语义的明确，以及实现多个 request 在 ViewModel 中的组合和复用。
    public final AccountRequest accountRequest = new AccountRequest();
}
