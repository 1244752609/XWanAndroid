package com.ara.project_common.domain.message;

import androidx.lifecycle.ViewModel;

import com.kunminx.architecture.ui.callback.UnPeekLiveData;

/**
 * Created by XieXin on 2022/3/21.
 * event-ViewModel 的职责仅限于在 "跨页面通信" 的场景下，承担 "唯一可信源"，
 * 所有跨页面的 "状态同步请求" 都交由该可信源在内部决策和处理，并统一分发给所有订阅者页面。
 */
public class ShareViewModel extends ViewModel {
    // 通过 UnPeekLiveData 配合 SharedViewModel 来发送 生命周期安全的、
    // 确保消息同步一致性和可靠性的 "跨页面" 通知。

    // 并且，在 "页面通信" 的场景下，使用全局 ViewModel，是因为它被封装在 base 页面中，
    // 避免页面之外的组件拿到，从而造成不可预期的推送。
    // 而且尽可能使用单例或 ViewModel 托管 liveData，这样能利用好 LiveData "读写分离" 的特性
    // 来实现 "唯一可信源" 单向数据流的决策和分发，从而避免只读数据被篡改 导致的其他页面拿到脏数据。

    /*** 首页底部导航位置 */
    private final UnPeekLiveData<Integer> toHomeBottomNavigationIndex =
            new UnPeekLiveData.Builder<Integer>().setAllowNullValue(false).create();
    /*** 登录成功 */
    private final UnPeekLiveData<Boolean> toLoginSuccess =
            new UnPeekLiveData.Builder<Boolean>().setAllowNullValue(false).create();


    public UnPeekLiveData<Integer> getToHomeBottomNavigationIndex() {
        return toHomeBottomNavigationIndex;
    }

    public void requestToHomeBottomNavigationIndex(int index) {
        toHomeBottomNavigationIndex.setValue(index);
    }

    public UnPeekLiveData<Boolean> getToLoginSuccess() {
        return toLoginSuccess;
    }

    public void requestToLoginSuccess(boolean isLogin) {
        toLoginSuccess.setValue(isLogin);
    }
}
