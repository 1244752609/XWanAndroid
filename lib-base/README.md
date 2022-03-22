# 基本模块
基类封装
框架架构

## 基类
- Application基类 > BaseApplication
- Activity基类 > BaseActivity
- Fragment基类 > BaseFragment
- 对话框基础类 > BaseDialog

## 适配器
- 动画监听适配器 > AnimationAdapter
- 公共PagerAdapter适配器 > CommonViewPagerAdapter
- OnPageChangeListener 适配器 > PageChangeAdapter
- 输入监听适配器 > TextWatcherAdapter
- ViewPager2适配器 > ViewPager2Adapter
- ViewPager适配器 > ViewPagerAdapter

## DataBinding适配器
- 公共BindingAdapter > CommonBindingAdapter
- Drawables BindingAdapter > DrawablesBindingAdapter

## domain
- ARouter传输对象时使用 > JsonServiceImpl

## data
- 专用于数据层返回结果给 domain 层或 ViewModel 用 > DataResult
- 加载状态 > LoadState
- 结果来源 > ResultSource
- 网络状态管理 > NetworkStateManager
- 网络变化监听 > NetworkStateReceive
