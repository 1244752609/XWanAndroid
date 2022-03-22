package com.ara.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ara.db.converter.DateConverter;
import com.ara.db.dao.SearchRecordDao;
import com.ara.db.entity.SearchRecord;

/**
 * Created by XieXin on 2022/3/10.
 * 数据库
 */
@TypeConverters(value = {DateConverter.class})
@Database(entities = {SearchRecord.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SearchRecordDao searchRecordDao();
}
