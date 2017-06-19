package com.aymanapplication.android.bookinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.aymanapplication.android.bookinglist.adapter.MoreAdapter;
import com.aymanapplication.android.bookinglist.adapter.SubAdapter;
import com.aymanapplication.android.bookinglist.model.RowData;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MoreActivity extends AppCompatActivity {

    private ArrayList<RowData> listRowData;
    private RecyclerView rvMore;
    private MoreAdapter moreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        try {
            Intent intent = getIntent();
            String categoryTitle = intent.getStringExtra("categoryTitle");
            this.setTitle(categoryTitle);

            //To receive json file from the activity.
            Gson gson = new Gson();
            String jsonListRowData = intent.getStringExtra("jsonListRowData");
            Type type = new TypeToken<ArrayList<RowData>>(){}.getType();
            listRowData = gson.fromJson(jsonListRowData, type);

    //            FlexboxLayoutManager layoutManager = new FlexboxLayoutManager();
    //            layoutManager.setFlexWrap(FlexWrap.WRAP);
    //            layoutManager.setFlexDirection(FlexDirection.ROW);
    //            layoutManager.setAlignItems(AlignItems.STRETCH);
    //            layoutManager.setJustifyContent(2);


                rvMore = (RecyclerView) findViewById(R.id.rvMore);
                rvMore.setLayoutManager(new LinearLayoutManager(this));
                rvMore.setItemAnimator(new DefaultItemAnimator());
                moreAdapter = new MoreAdapter(this, listRowData);
                rvMore.setAdapter(moreAdapter);
        }catch(Exception ex){
            ex.getMessage();
        }
    }
}
