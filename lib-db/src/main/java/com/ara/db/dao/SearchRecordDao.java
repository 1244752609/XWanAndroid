package com.ara.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.ara.db.entity.SearchRecord;

import java.util.List;

/**
 * Created by XieXin on 2022/3/10.
 * 搜索记录
 */
@Dao
public interface SearchRecordDao {
    @Query("SELECT * FROM SearchRecord")
    List<SearchRecord> getAll();

    @Query("SELECT * FROM SearchRecord WHERE id IN (:ids)")
    List<SearchRecord> loadAllByIds(int[] ids);

    @Query("SELECT * FROM SearchRecord WHERE content LIKE :content LIMIT 1")
    SearchRecord findByContent(String content);

    @Insert
    void insertAll(SearchRecord... records);

    @Delete
    void delete(SearchRecord record);

    @Query("DELETE FROM SearchRecord WHERE id= :id")
    void deleteById(long id);

    @Query("DELETE FROM SearchRecord")
    void deleteAll();
}
