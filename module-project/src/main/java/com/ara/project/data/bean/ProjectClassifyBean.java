package com.ara.project.data.bean;

/**
 * Created by XieXin on 2022/3/14.
 * 项目分类
 */
public class ProjectClassifyBean {
    /**
     * children : []
     * courseId : 13
     * id : 294
     * name : 完整项目
     * order : 145000
     * parentChapterId : 293
     * userControlSetTop : false
     * visible : 0
     */

    private String courseId;
    private String id;
    private String name;
    private int order;
    private String parentChapterId;
    private boolean userControlSetTop;
    private int visible;

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

    public String getParentChapterId() {
        if (parentChapterId == null) setParentChapterId("");
        return parentChapterId;
    }

    public void setParentChapterId(String parentChapterId) {
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

}
