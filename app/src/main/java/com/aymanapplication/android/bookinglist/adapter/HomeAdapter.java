package com.aymanapplication.android.bookinglist.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aymanapplication.android.bookinglist.MoreActivity;
import com.aymanapplication.android.bookinglist.R;
import com.aymanapplication.android.bookinglist.model.CategoryData;
import com.aymanapplication.android.bookinglist.model.RowData;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by ayman on 16-Jun-17.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CategoryData> groupedCategoryDataList;
    private SubAdapter subAdapter;

    public HomeAdapter(Context context, ArrayList<CategoryData> groupedCategoryDataList) {
        this.context = context;
        this.groupedCategoryDataList = groupedCategoryDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_category_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtCategoryTitle.setText(groupedCategoryDataList.get(position).getCategoryName());
        holder.rvListCategoryCard.setLayoutManager(new LinearLayoutManager(context, LinearLayout.HORIZONTAL, false));
        holder.rvListCategoryCard.setItemAnimator(new DefaultItemAnimator());
        subAdapter = new SubAdapter(context, groupedCategoryDataList.get(position).getListRowData());
        holder.rvListCategoryCard.setAdapter(subAdapter);
    }

    @Override
    public int getItemCount() {
        return groupedCategoryDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtCategoryTitle;
        private TextView txtMore;
        private RecyclerView rvListCategoryCard;

        public ViewHolder(View itemView) {
            super(itemView);
            txtCategoryTitle = (TextView) itemView.findViewById(R.id.txtCategoryTitle);
            txtMore = (TextView) itemView.findViewById(R.id.txtMore);
            rvListCategoryCard = (RecyclerView) itemView.findViewById(R.id.rvListCategoryCard);
            txtMore.setOnClickListener(this);
            txtCategoryTitle.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String categoryTitle = groupedCategoryDataList.get(getAdapterPosition()).getCategoryName();
            ArrayList<RowData> listRowData = new ArrayList<>();
            listRowData = groupedCategoryDataList.get(getAdapterPosition()).getListRowData();

            Intent intent = new Intent(context, MoreActivity.class);
            intent.putExtra("categoryTitle", categoryTitle);

            //To path an arrayList to another acivity..parse it to json.
            Gson gson = new Gson();
            String jsonListRowData = gson.toJson(listRowData);
            intent.putExtra("jsonListRowData", jsonListRowData);
            context.startActivity(intent);
        }
    }
}
