package com.aymanapplication.android.bookinglist.picasso;

import android.content.Context;
import android.widget.ImageView;

import com.aymanapplication.android.bookinglist.R;
import com.squareup.picasso.Picasso;

/**
 * Created by ayman on 18-Jan-17.
 */

public class PicassoMain {

    public static void loadImage(Context context, String imageUrl, ImageView imageView){
        if (imageUrl != null && imageUrl.length() > 0){
            Picasso.with(context).load(imageUrl).placeholder(R.drawable.content).into(imageView);
        } else {
            Picasso.with(context).load(R.drawable.content).into(imageView);
        }
    }
}
