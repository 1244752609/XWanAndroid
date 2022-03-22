package com.ara.square.ui.state;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;


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
public class SquareFragmentViewModel extends ViewModel {
    public final ArrayList<Fragment> fragments = new ArrayList<>();
    public final String[] titles = new String[2];
}
