package com.aymanapplication.android.bookinglist;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aymanapplication.android.bookinglist.picasso.PicassoMain;

public class DetailsActivity extends AppCompatActivity {

    private TextView txtTitle;
    private TextView txtdescription;
    private TextView txtAuthor;
    private TextView txtpublisher;
    private TextView txtPublishDate;
    private TextView txtPrice;
    private TextView txtRatingsCount;
    private TextView txtCategoryTitle;
    private RatingBar mRatingBar;
    private ImageView imgViewDetails;

    private String title, description;

    private TextView txtReadMore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtdescription = (TextView) findViewById(R.id.txtDescription);
        txtdescription.setMovementMethod(new ScrollingMovementMethod());
        txtAuthor = (TextView) findViewById(R.id.txtAuthor);
        txtpublisher = (TextView) findViewById(R.id.txtPublisher);
        txtPublishDate = (TextView) findViewById(R.id.txtPublishDate);
        txtPrice = (TextView) findViewById(R.id.txtRate);
        txtRatingsCount = (TextView) findViewById(R.id.txtRatingsCount);
        txtCategoryTitle = (TextView) findViewById(R.id.txtCategoryTitle);
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        imgViewDetails = (ImageView) findViewById(R.id.imgViewDetails);

        Intent intent = getIntent();
        setTitle(intent.getStringExtra("title"));

        try {
            txtTitle.setText(intent.getStringExtra("title"));
            txtdescription.setText(intent.getStringExtra("description"));
            txtAuthor.setText(intent.getStringExtra("author"));
            txtpublisher.setText(intent.getStringExtra("publisher"));
            txtPublishDate.setText(intent.getStringExtra("publishDate"));
            txtPrice.setText(intent.getStringExtra("price"));
            txtRatingsCount.setText(intent.getStringExtra("ratingsCount") + " ");
            txtCategoryTitle.setText(intent.getStringExtra("categoryTitle"));
            String rate = intent.getStringExtra("rate");
            rate = rate.equals("") ? "0" : rate;
            mRatingBar.setRating(Float.parseFloat(rate));
            String imgUrl = intent.getStringExtra("imgUrl");
            PicassoMain.loadImage(this, imgUrl, imgViewDetails);

            title = intent.getStringExtra("title");
            description = intent.getStringExtra("description");
        }catch (Exception ex){
            ex.getMessage();
        }

        txtReadMore = (TextView) findViewById(R.id.txtReadMore);
    }

    protected void readMore_Clicked(View view){
        try{

            Intent intent = new Intent(this, DescriptionActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("description", description);
            startActivity(intent);

        }catch (Exception ex){
            ex.getMessage();
        }
    }
}
