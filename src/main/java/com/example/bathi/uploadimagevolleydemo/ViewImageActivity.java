package com.example.bathi.uploadimagevolleydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ViewImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        List<String> list = new ArrayList<>();
        list.add("http://buivanhoa.tk/image/10H-33M-59S-2020-10-23.png");
        list.add("http://buivanhoa.tk/image/10H-33M-59S-2020-10-23.png");
        list.add("http://buivanhoa.tk/image/10H-33M-59S-2020-10-23.png");
        list.add("http://buivanhoa.tk/image/10H-33M-59S-2020-10-23.png");



        GridImageAdapter adapter = new GridImageAdapter(this,R.layout.adapter_view_layout,list);
        GridView listView = findViewById(R.id.gridview);
        listView.setAdapter(adapter);

    }
}

