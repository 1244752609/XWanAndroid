package com.ara.base.data.response;

/**
 * Created by XieXin on 2022/2/28.
 * 加载状态
 */
public enum LoadState {
    /*** 加载中 */
    LOADING,
    /*** 空数据 */
    EMPTY,
    /*** 加载失败 */
    ERROR,
    /*** 加载成功 */
    SUCCESS,

    /*** 加载更多没有更多数据 */
    LOAD_MORE_NO_MORE_DATA,
    /*** 加载更多成功 */
    LOAD_MORE_SUCCESS,
    /*** 加载更多失败 */
    LOAD_MORE_ERROR,
}
