package com.ara.project_common.data.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XieXin on 2019/1/23.
 * 账户 实体类
 */
public class AccountBean {
    /*** token */
    private String token;

    private String email;
    private String icon;
    private String id;
    private String password;
    private String type;
    private String username;
    private List<?> chapterTops;
    private List<Integer> collectIds; //收藏的文章id

    private String coinCount;
    private String rank;

    public String getToken() {
        if (token == null) setToken("");
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        if (email == null) setEmail("");
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        if (icon == null) setIcon("");
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        if (id == null) setId("");
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        if (password == null) setPassword("");
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        if (type == null) setType("");
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        if (username == null) setUsername("");
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<?> getChapterTops() {
        if (chapterTops == null) setChapterTops(new ArrayList<>());
        return chapterTops;
    }

    public void setChapterTops(List<?> chapterTops) {
        this.chapterTops = chapterTops;
    }

    public List<Integer> getCollectIds() {
        if (collectIds == null) setCollectIds(new ArrayList<>());
        return collectIds;
    }

    public void setCollectIds(List<Integer> collectIds) {
        this.collectIds = collectIds;
    }

    public String getCoinCount() {
        if (coinCount == null) setCoinCount("");
        return coinCount;
    }

    public void setCoinCount(String coinCount) {
        this.coinCount = coinCount;
    }

    public String getRank() {
        if (rank == null) setRank("");
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
