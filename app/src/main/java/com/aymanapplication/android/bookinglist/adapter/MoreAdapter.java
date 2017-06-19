package com.aymanapplication.android.bookinglist.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aymanapplication.android.bookinglist.DetailsActivity;
import com.aymanapplication.android.bookinglist.R;
import com.aymanapplication.android.bookinglist.model.RowData;
import com.aymanapplication.android.bookinglist.picasso.PicassoMain;


import java.util.ArrayList;

/**
 * Created by ayman on 18-Jun-17.
 */

public class MoreAdapter extends RecyclerView.Adapter<MoreAdapter.ViewHolder> {

    private Context context;
    private ArrayList<RowData> listRowData;

    public MoreAdapter(Context context, ArrayList<RowData> listRowData) {
        this.context = context;
        this.listRowData = listRowData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false);
        MoreAdapter.ViewHolder viewHolder = new MoreAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (listRowData.get(position).getTitle().length() > 25){
            holder.txtTitle.setText(listRowData.get(position).getTitle().substring(0, 25) + "..");
        }else{
            holder.txtTitle.setText(listRowData.get(position).getTitle());
        }
        if (listRowData.get(position).getAuthors().length() > 15){
            holder.txtAuthor.setText(listRowData.get(position).getAuthors().substring(0, 14) + "..");
        }else{
            holder.txtAuthor.setText(listRowData.get(position).getAuthors());
        }
        if(listRowData.get(position).getRate().equals("")){
            holder.txtRate.setVisibility(View.INVISIBLE);
        }else{
            holder.txtRate.setText(listRowData.get(position).getRate() + " ");
        }
        holder.txtPrice.setText(listRowData.get(position).getPrice());
        String imgUrl = listRowData.get(position).getImgUrl();
        PicassoMain.loadImage(context, imgUrl, holder.imageView);

        holder.txtPublisher.setText(listRowData.get(position).getPublisher());
        holder.txtpublishDate.setText(listRowData.get(position).getPublishDate());
    }

    @Override
    public int getItemCount() {
        return listRowData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtTitle;
        private TextView txtAuthor;
        private TextView txtRate;
        private TextView txtPrice;
        private TextView txtPublisher;
        private TextView txtpublishDate;
        private ImageView imageView;

        public ViewHolder(View itemView){
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txtAuthor = (TextView) itemView.findViewById(R.id.txtAuthor);
            txtRate = (TextView) itemView.findViewById(R.id.txtRate);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            txtPublisher = (TextView) itemView.findViewById(R.id.txtPublisher);
            txtpublishDate = (TextView) itemView.findViewById(R.id.txtPublishDate);
            imageView = (ImageView) itemView.findViewById(R.id.imgViewDetails);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("title", listRowData.get(getAdapterPosition()).getTitle());
            intent.putExtra("author", listRowData.get(getAdapterPosition()).getAuthors());
            intent.putExtra("description", listRowData.get(getAdapterPosition()).getDescription());
            intent.putExtra("rate", listRowData.get(getAdapterPosition()).getRate());
            intent.putExtra("price", listRowData.get(getAdapterPosition()).getPrice());
            intent.putExtra("imgUrl", listRowData.get(getAdapterPosition()).getImgUrl());
            intent.putExtra("publisher", listRowData.get(getAdapterPosition()).getPublisher());
            intent.putExtra("publishDate", listRowData.get(getAdapterPosition()).getPublishDate());
            intent.putExtra("ratingsCount", listRowData.get(getAdapterPosition()).getRatingsCount());
            intent.putExtra("categoryTitle", listRowData.get(getAdapterPosition()).getCategoryName());

            context.startActivity(intent);
        }
    }
}
