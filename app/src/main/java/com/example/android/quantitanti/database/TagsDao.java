package com.example.android.quantitanti.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.android.quantitanti.models.TagsPojo;

import java.util.List;

@Dao
public interface TagsDao {

    @Insert
    void insertTag(TagEntry tagEntry);

    @Update
    void updateTag(TagEntry tagEntry);

    @Delete
    void deleteTag(TagEntry tagEntry);

    @Query("DELETE FROM tags WHERE tag_id =:tag_id")
    void deleteTagWithId(int tag_id);

    @Query("SELECT * FROM tags ORDER BY tag_name")
    LiveData<List<TagEntry>> loadAllTags();

    @Query("SELECT tag_name FROM tags ORDER BY tag_name")
    LiveData<List<String>> loadNameTags();

    @Query("SELECT tag_id FROM tags WHERE tag_name = :tag_name")
    int loadTagId(String tag_name);

    @Query("SELECT EXISTS (SELECT 1 FROM tags WHERE tag_name = :tag_name)")
    int tagExists(String tag_name);

    @Query("SELECT tag_name FROM tags WHERE tag_id = :tag_id")
    String loadTagName(int tag_id);
}
