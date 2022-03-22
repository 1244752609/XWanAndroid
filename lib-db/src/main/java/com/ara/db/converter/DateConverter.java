package com.ara.db.converter;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Created by XieXin on 2022/3/10.
 * 时间转换器
 */
public class DateConverter {
    @TypeConverter
    public static Date revertDate(long value) {
        return new Date(value);
    }

    @TypeConverter
    public static long converterDate(Date value) {
        if (value == null) value = new Date();
        return value.getTime();
    }
}
