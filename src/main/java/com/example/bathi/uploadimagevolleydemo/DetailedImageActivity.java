package com.example.bathi.uploadimagevolleydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailedImageActivity extends AppCompatActivity {
    TextView user;
    TextView time;
    TextView description;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_image2);

        user = findViewById(R.id.user);
        time = findViewById(R.id.time);
        description = findViewById(R.id.description);
        imageView = findViewById(R.id.imageView);

        Picasso.with(this.getApplicationContext()).load(getIntent().getStringExtra("link")).into(imageView);
        Intent intent = getIntent();
        user.setText(intent.getStringExtra("user"));
        time.setText(intent.getStringExtra("time"));
        description.setText(intent.getStringExtra("description"));
    }
}
