# 基于Java+Jetpack+MVVM+组件化实现的WANAndroid项目

本项目基于 Java+Jetpack+MVVM+组件化+DataBinding+Arouter+Retrofit 等框架实现的一个项目。

# 项目结构
~~~
|- WanAndroid
||-- app // app 入口
  ||-- librarys //library库
      ||-- lib-base // 基础封装
      ||-- lib-common //通用库
      ||-- lib-db // Room数据库封装
      ||-- lib-network // 网络请求封装（LiveData+Rxjava+Retrofit）
      ||-- lib-widget // 控件封装
      ||-- lib-project-common // 项目共用
  ||-- modules // 功能模块
    ||-- module-copy // 复制模块：用于新模块复制快速搭建项目模块
    ||-- module-account // 账户模块
    ||-- module-home // 首页模块
    ||-- module-mine // 我的模块
    ||-- module-public // 公众号模块
    ||-- module-project // 项目模块
    ||-- module-square // 广场模块
    ||-- module-web // 网页模块
||-- README.md
~~~

# 主要功能
- 首页、项目、广场、公众号、我的
- 登录、注册
- 整体采用[Material Design]([https://www.material.io/](https://www.material.io/))设计风格

# 各个模块可以单独编译运行

gradle.properties 有配置"集成开发模式" 和 "组件开发模式"的切换开关 true表示组件独立运行，false表示一个library

```
 #isRunModule=true
 isRunModule=false
```

## 主要开源框架

- [RxJava](https://github.com/ReactiveX/RxJava)

- [RxAndroid](https://github.com/ReactiveX/RxAndroid)

- [Retrofit](https://github.com/square/retrofit)

- [okhttp](https://github.com/square/okhttp)

- [PersistentCookieJar](https://github.com/franmontiel/PersistentCookieJar)

- [Glide](https://github.com/bumptech/glide)

- [MMKV](https://github.com/Tencent/MMKV)

- [Luban](https://github.com/Curzibn/Luban)

- [Android_CN_OAID](https://github.com/gzu-liyujiang/Android_CN_OAID)

- [utilcodex](https://github.com/Blankj/AndroidUtilCode)

- [adapter_plus](https://github.com/xiaohaozi9825/adapter_plus)

- [flexbox-layout](https://github.com/google/flexbox-layout)

- [FlycoTabLayout](https://github.com/H07000223/FlycoTabLayout)

- [SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout)

- [banner](https://github.com/youth5201314/banner)

- [AgentWeb](https://github.com/Justson/AgentWeb)

- [Arouter](https://github.com/alibaba/ARouter)

# 感谢

- [阿里矢量图](https://links.jianshu.com/go?to=https%3A%2F%2Fwww.iconfont.cn%2F)
- [WanAndroid](https://www.wanandroid.com/blog/show/2)
- [Jetpack-MVVM-Best-Practice](https://github.com/KunMinX/Jetpack-MVVM-Best-Practice)