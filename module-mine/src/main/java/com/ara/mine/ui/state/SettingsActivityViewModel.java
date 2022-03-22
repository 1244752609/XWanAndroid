package com.ara.mine.ui.state;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ara.mine.domain.request.MineRequest;

/**
 * Created by XieXin on 2022/2/23.
 * Main
 * 每个页面都要单独准备一个 state-ViewModel，
 * 来托管 DataBinding 绑定的临时状态，以及视图控制器重建时状态的恢复。
 * <p>
 * 此外，state-ViewModel 的职责仅限于 状态托管，不建议在此处理 UI 逻辑，
 * UI 逻辑只适合在 Activity/Fragment 等视图控制器中完成，是 “数据驱动” 的一部分，
 * 将来升级到 Jetpack Compose 更是如此。
 * <p>
 */
public class SettingsActivityViewModel extends ViewModel {
    // 此处用于绑定的状态，使用 LiveData 而不是 ObservableField，
    // 主要是考虑到 ObservableField 具有防抖的特性，不适合该场景。
    public final MutableLiveData<String> version = new MutableLiveData<>();
    public final MutableLiveData<String> language = new MutableLiveData<>();
    public final MutableLiveData<String> cacheSize = new MutableLiveData<>();

    public final MineRequest mineRequest = new MineRequest();
}
