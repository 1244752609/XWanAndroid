package com.ara.square.data.bean;

import com.ara.project_common.data.bean.ArticleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XieXin on 2022/3/15.
 */
public class NavigationBean {
    /**
     * children : [{"children":[],"courseId":13,"id":60,"name":"Android Studio相关","order":1000,"parentChapterId":150,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":169,"name":"gradle","order":1001,"parentChapterId":150,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":269,"name":"官方发布","order":1002,"parentChapterId":150,"userControlSetTop":false,"visible":1},{"children":[],"courseId":13,"id":529,"name":"90-120hz","order":1003,"parentChapterId":150,"userControlSetTop":false,"visible":1}]
     * courseId : 13
     * id : 150
     * name : 开发环境
     * order : 1
     * parentChapterId : 0
     * userControlSetTop : false
     * visible : 1
     */
    /*共用*/
    private String name;


    /*体系*/
    private String courseId;
    private String id;
    private int order;
    private int parentChapterId;
    private boolean userControlSetTop;
    private int visible;
    private List<ChildrenBean> children;

    /*导航*/
    private String cid;
    private List<ArticleBean> articles;

    public String getName() {
        if (name == null) setName("");
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseId() {
        if (courseId == null) setCourseId("");
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getId() {
        if (id == null) setId("");
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getParentChapterId() {
        return parentChapterId;
    }

    public void setParentChapterId(int parentChapterId) {
        this.parentChapterId = parentChapterId;
    }

    public boolean isUserControlSetTop() {
        return userControlSetTop;
    }

    public void setUserControlSetTop(boolean userControlSetTop) {
        this.userControlSetTop = userControlSetTop;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    public String getCid() {
        if (cid == null) setCid("");
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public List<ArticleBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleBean> articles) {
        this.articles = articles;
    }
}
