package com.example.bathi.uploadimagevolleydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.*;

public class ViewImageActivity extends AppCompatActivity {
    GridView gridView;
    GridImageAdapter gridAdapter;
    ArrayAdapter arrayAdapter;
    String url;
    List<String> Images;
    Spinner spinner;
    List<String> Users;
    RequestQueue queue;
    JSONArray ja;
    JSONObject jo;
    Intent intent;
    List<MyImage> MyImages;
    String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        Intent i = getIntent();
        this.user = i.getStringExtra("user");
        //add data
        Images = new ArrayList<>();
        MyImages = new ArrayList<>();
        url = Configuration.urlImage;
        spinner = findViewById(R.id.spinner);
        gridView = findViewById(R.id.gridview);
        queue = Volley.newRequestQueue(this);

        String ApiUsers = Configuration.ApiUser;
        StringRequest sr1 = new StringRequest(Request.Method.GET, ApiUsers, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Users = new ArrayList<>();
                Users.add(0,"All");
                try {
                    ja = new JSONArray(response);
                    for (int i=0;i<ja.length();i++){
                        Users.add(ja.getJSONObject(i).getString("user"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arrayAdapter= new ArrayAdapter<>(ViewImageActivity.this, android.R.layout.simple_spinner_item,Users);
                for (String x: Users)
                    Log.d("user111",x);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(arrayAdapter);




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        gridAdapter = new GridImageAdapter(ViewImageActivity.this,R.layout.adapter_view_layout,Images);
        gridView.setAdapter(gridAdapter);



        //Set Listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String user = Users.get(i);
                if (i==0) user="all";
                String ApiImageUser = Configuration.ApiImageUser+user;
                StringRequest sr2 = new StringRequest(Request.Method.GET, ApiImageUser,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("abcd999",response);
                                try {
                                    ja = new JSONArray(response);
                                    Images.clear();
                                    MyImages.clear();
                                    Log.d("aaaaa",ja.length()+"");
                                    for (int i=0;i<ja.length();i++){
                                        try {
                                            jo = ja.getJSONObject(i);
                                            Images.add(jo.getString("name"));
                                            MyImages.add(new MyImage(Configuration.urlImage+jo.getString("name"),
                                                    jo.getString("user"),jo.getString("time"),jo.getString("des")));

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    Log.d("size99999999",Images.size()+"&&"+MyImages.size());
                                    for (int i=0;i<Images.size();i++){
                                        Images.set(i,Configuration.urlImage+Images.get(i));
                                    }

                                    gridAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                            }

                            }

                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Connection Failed",Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(sr2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent = new Intent(ViewImageActivity.this,DetailedImageActivity.class);
                intent.putExtra("link",MyImages.get(i).getLink());
                intent.putExtra("user",MyImages.get(i).getUser());
                intent.putExtra("time",MyImages.get(i).getTime());
                intent.putExtra("description",MyImages.get(i).getDescription());
                Log.d("99999",MyImages.get(i).getLink());
                startActivity(intent);
            }
        });

        queue.add(sr1);


    }
}

