package com.example.android.quantitanti.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.android.quantitanti.models.DailyExpenseTagsWithPicsPojo;

import java.util.List;

@Dao
public interface DailyExpensesDao {

    @Transaction
    @Query("SELECT * FROM expenses WHERE date =:date ORDER BY id DESC")
    LiveData<List<DailyExpenseTagsWithPicsPojo>> loadCostsWithTagsAndPics(String date);

    @Transaction
    @Query("SELECT * FROM expenses WHERE id =:id")
    LiveData<DailyExpenseTagsWithPicsPojo> loadCostWithTagsAndPicsById(int id);

    //for AllCostsFragment
    @Transaction
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    LiveData<List<DailyExpenseTagsWithPicsPojo>> loadAllCostsWithTagsAndPicsForFragment();
}
