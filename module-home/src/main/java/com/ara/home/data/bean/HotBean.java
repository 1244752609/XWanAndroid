package com.ara.home.data.bean;

/**
 * Created by XieXin on 2022/3/11.
 * 热搜
 */
public class HotBean {
    /**
     * id : 6
     * link :
     * name : 面试
     * order : 1
     * visible : 1
     */

    private long id;
    private String link;
    private String name;
    private int order;
    private int visible;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLink() {
        if (link == null) setLink("");
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        if (name == null) setName("");
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }
}
