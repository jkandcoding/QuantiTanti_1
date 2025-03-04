package com.example.android.quantitanti.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.android.quantitanti.database.CostDatabase;
import com.example.android.quantitanti.database.CostEntry;
import com.example.android.quantitanti.database.TagEntry;
import com.example.android.quantitanti.models.DailyExpenseTagsWithPicsPojo;

import java.util.List;

public class AddCostViewModel extends ViewModel {

    private LiveData<DailyExpenseTagsWithPicsPojo> cost;

    public AddCostViewModel(CostDatabase database, int costId) {
        cost = database.dailyExpensesDao().loadCostWithTagsAndPicsById(costId);
    }

    public LiveData<DailyExpenseTagsWithPicsPojo> getCost() {
        return cost;
    }

}
