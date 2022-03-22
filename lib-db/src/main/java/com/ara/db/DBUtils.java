package com.ara.db;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.blankj.utilcode.util.Utils;

/**
 * Created by XieXin on 2022/3/10.
 * 数据库管理类
 */
public class DBUtils {
    private final static String DB_NAME = "x_wan_android";
    private static AppDatabase db;

    private DBUtils() {
    }

    public static AppDatabase getDb() {
        if (db == null) {
            db = Room.databaseBuilder(Utils.getApp(), AppDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .enableMultiInstanceInvalidation()
//                .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return db;
    }

    /**
     * 数据库版本 1->2 user表格新增了age列
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `book` (`bookId` INTEGER PRIMARY KEY autoincrement, `bookName` TEXT , `user_id` INTEGER, 'time' INTEGER)");

        }
    };

    /**
     * 数据库版本 2->3 新增book表格
     */
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `book` (`bookId` INTEGER PRIMARY KEY autoincrement, `bookName` TEXT , `user_id` INTEGER, 'time' INTEGER)");
        }
    };

}
