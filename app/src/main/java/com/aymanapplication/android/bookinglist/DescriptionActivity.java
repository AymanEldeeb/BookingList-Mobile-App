package com.aymanapplication.android.bookinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DescriptionActivity extends AppCompatActivity {

    private TextView txtTitle, txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        this.setTitle("Description");

        Intent intent = getIntent();
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtDescription = (TextView) findViewById(R.id.txtDescription);

        txtTitle.setText(intent.getStringExtra("title"));
        txtDescription.setText(intent.getStringExtra("description"));
    }
}
