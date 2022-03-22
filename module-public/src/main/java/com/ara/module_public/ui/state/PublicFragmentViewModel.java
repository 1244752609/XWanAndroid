package com.ara.module_public.ui.state;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ara.base.data.response.LoadState;
import com.ara.module_public.domain.request.PublicRequest;

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
public class PublicFragmentViewModel extends ViewModel {
    public final ObservableField<String> title = new ObservableField<>();
    public final MutableLiveData<Boolean> autoRefresh = new MutableLiveData<>();
    public final ObservableField<LoadState> listLoadState = new ObservableField<>();
    public final ObservableField<LoadState> loadState = new ObservableField<>();

    public final PublicRequest publicRequest = new PublicRequest();
}
