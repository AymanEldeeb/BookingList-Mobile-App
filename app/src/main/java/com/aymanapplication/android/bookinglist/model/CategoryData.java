package com.aymanapplication.android.bookinglist.model;

import java.util.ArrayList;

/**
 * Created by ayman on 16-Jun-17.
 */

public class CategoryData {

    private String categoryName;
    private ArrayList<RowData> listRowData;

    public CategoryData() {}

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<RowData> getListRowData() {
        return listRowData;
    }

    public void setListRowData(ArrayList<RowData> rowData) {
        this.listRowData = rowData;
    }
}
