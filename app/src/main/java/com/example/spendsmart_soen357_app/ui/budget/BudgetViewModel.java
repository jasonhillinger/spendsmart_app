package com.example.spendsmart_soen357_app.ui.budget;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
//display budget, addBudget, remove, getItems,

public class BudgetViewModel<BudgetItem> extends ViewModel {

    private List<BudgetItem> budgetItems = new ArrayList<>();

    //Add budget
    public void addBudgetItem(){}

    //remove budget
    public void removeBudgetItem(){}

    //get budget item
    public List<BudgetItem> getBudgetItems() {
        return budgetItems;
    }
}
