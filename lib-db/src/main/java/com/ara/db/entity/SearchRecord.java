package com.ara.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.ara.db.DBUtils;

import java.util.Date;


/**
 * Created by XieXin on 2022/3/10.
 * 搜索记录
 */
@Entity(indices = {@Index(value = "content", unique = true)})
public class SearchRecord {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String content;

    @ColumnInfo(name = "search_time")
    public Date searchTime;

    public SearchRecord() {
    }

    @Ignore
    public SearchRecord(String content) {
        this.content = content;
    }
}
