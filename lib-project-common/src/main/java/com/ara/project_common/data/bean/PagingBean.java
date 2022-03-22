package com.ara.project_common.data.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XieXin on 2019/1/24.
 * 列表 实体类
 */
public class PagingBean<T> {
    /*** 是否最后一页 */
    @SerializedName("over")
    private boolean last;
    /*** 每页数量 */
    private int size;
    /*** 当前页数 */
    @SerializedName("curPage")
    private int current;
    /*** 总页数 */
    @SerializedName("pageCount")
    private int pages;
    /*** 总数 */
    private int total;
    /*** 列表 */
    @SerializedName("datas")
    private List<T> list;

    public boolean isLast() {
        return last;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getList() {
        if (list == null) setList(new ArrayList<>());
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
