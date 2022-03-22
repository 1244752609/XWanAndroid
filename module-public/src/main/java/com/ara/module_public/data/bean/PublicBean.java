package com.ara.module_public.data.bean;

import java.util.ArrayList;
import java.util.List;

import pw.xiaohaozi.adapter_plus.data.ViewTyper;

/**
 * Created by XieXin on 2022/3/14.
 * 项目
 */
public class PublicBean implements ViewTyper {
    private String apkLink;
    private int audit;
    private String author;
    private boolean canEdit;
    private String chapterId;
    private String chapterName;
    private boolean collect;
    private String courseId;
    private String desc;
    private String descMd;
    private String envelopePic;
    private boolean fresh;
    private String id;
    private String link;
    private String niceDate;
    private String niceShareDate;
    private String origin;
    private String prefix;
    private String projectLink;
    private long publishTime;
    private int realSuperChapterId;
    private int selfVisible;
    private Object shareDate;
    private String shareUser;
    private int superChapterId;
    private String superChapterName;
    private String title;
    private int type;
    private int userId;
    private int visible;
    private int zan;
    private List<?> tags;

    @Override
    public int getItemViewType() {
        return 1;
    }

    public String getApkLink() {
        if (apkLink == null) setApkLink("");
        return apkLink;
    }

    public void setApkLink(String apkLink) {
        this.apkLink = apkLink;
    }

    public int getAudit() {
        return audit;
    }

    public void setAudit(int audit) {
        this.audit = audit;
    }

    public String getAuthor() {
        if (author == null) setAuthor("");
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public String getChapterId() {
        if (chapterId == null) setChapterId("");
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        if (chapterName == null) setChapterName("");
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }

    public String getCourseId() {
        if (courseId == null) setCourseId("");
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getDesc() {
        if (desc == null) setDesc("");
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDescMd() {
        if (descMd == null) setDescMd("");
        return descMd;
    }

    public void setDescMd(String descMd) {
        this.descMd = descMd;
    }

    public String getEnvelopePic() {
        if (envelopePic == null) setEnvelopePic("");
        return envelopePic;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }

    public boolean isFresh() {
        return fresh;
    }

    public void setFresh(boolean fresh) {
        this.fresh = fresh;
    }

    public String getId() {
        if (id == null) setId("");
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        if (link == null) setLink("");
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNiceDate() {
        if (niceDate == null) setNiceDate("");
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getNiceShareDate() {
        if (niceShareDate == null) setNiceShareDate("");
        return niceShareDate;
    }

    public void setNiceShareDate(String niceShareDate) {
        this.niceShareDate = niceShareDate;
    }

    public String getOrigin() {
        if (origin == null) setOrigin("");
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getPrefix() {
        if (prefix == null) setPrefix("");
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getProjectLink() {
        if (projectLink == null) setProjectLink("");
        return projectLink;
    }

    public void setProjectLink(String projectLink) {
        this.projectLink = projectLink;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getRealSuperChapterId() {
        return realSuperChapterId;
    }

    public void setRealSuperChapterId(int realSuperChapterId) {
        this.realSuperChapterId = realSuperChapterId;
    }

    public int getSelfVisible() {
        return selfVisible;
    }

    public void setSelfVisible(int selfVisible) {
        this.selfVisible = selfVisible;
    }

    public Object getShareDate() {
        if (shareDate == null) setShareDate(new Object());
        return shareDate;
    }

    public void setShareDate(Object shareDate) {
        this.shareDate = shareDate;
    }

    public String getShareUser() {
        if (shareUser == null) setShareUser("");
        return shareUser;
    }

    public void setShareUser(String shareUser) {
        this.shareUser = shareUser;
    }

    public int getSuperChapterId() {
        return superChapterId;
    }

    public void setSuperChapterId(int superChapterId) {
        this.superChapterId = superChapterId;
    }

    public String getSuperChapterName() {
        if (superChapterName == null) setSuperChapterName("");
        return superChapterName;
    }

    public void setSuperChapterName(String superChapterName) {
        this.superChapterName = superChapterName;
    }

    public String getTitle() {
        if (title == null) setTitle("");
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public List<?> getTags() {
        if (tags == null) setTags(new ArrayList<>());
        return tags;
    }

    public void setTags(List<?> tags) {
        this.tags = tags;
    }
}
