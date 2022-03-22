package com.ara.mine.data.bean;

/**
 * Created by XieXin on 2022/3/16.
 */
public class IntegralBean {
    private String coinCount;
    private long date;
    private String desc;
    private String id;
    private String reason;
    private String userId;
    private String nickname;
    private String username;
    private int type;
    private String rank;
    private int level;

    public String getCoinCount() {
        if (coinCount == null) setCoinCount("");
        return coinCount;
    }

    public void setCoinCount(String coinCount) {
        this.coinCount = coinCount;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDesc() {
        if (desc == null) setDesc("");
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        if (id == null) setId("");
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReason() {
        if (reason == null) setReason("");
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUserId() {
        if (userId == null) setUserId("");
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        if (nickname == null) setNickname("");
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        if (username == null) setUsername("");
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRank() {
        if (rank == null) setRank("");
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
