package com.example.bathi.uploadimagevolleydemo;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailedImageActivity extends AppCompatActivity {
    TextView user;
    TextView time;
    TextView description;
    ImageView imageView;
    List<String> list;
    ListView listView;
    String nameOfImage;


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Download");
        menu.add(0, v.getId(), 0, "Delete");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String choice = item.getTitle()+"";
        switch (choice){
            case "Download":{
                SavedImageView();
                break;
            }
            case "Delete":{
                SendDeleteRequest();
                break;
            }
            default:{
                break;
            }
        }
        return true;
    }

    private void SendDeleteRequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, Configuration.ApiDeleteImage+nameOfImage, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("99999","OK");
               startActivity(new Intent(DetailedImageActivity.this,ViewImageActivity.class));


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("nameOfImage",nameOfImage);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(DetailedImageActivity.this);
        requestQueue.add(stringRequest);
    }

    private void SavedImageView() {
        File path = Environment.getExternalStorageDirectory();
        Log.d("9999",path+"");
        File dir = new File(path+"/DoAnMang/");
        dir.mkdirs();
        //text
        if (!dir.exists()) Toast.makeText(this,"Created Folder",Toast.LENGTH_LONG).show();


        File file = new File(dir,nameOfImage);
        FileOutputStream fos ;
        try {
            fos = new FileOutputStream(file);
            imageView.buildDrawingCache();
            Bitmap bitmap = imageView.getDrawingCache();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
            Toast.makeText(this,"Saved",Toast.LENGTH_LONG).show();
            try {
                fos.flush();
                fos.close();
            }
            catch (IOException e){

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_image2);

        list = new ArrayList<>();
        imageView = findViewById(R.id.imageView);
        listView = findViewById(R.id.listDetailed);

        String link = getIntent().getStringExtra("link");
        nameOfImage = link.substring(link.lastIndexOf("/")+1,link.length());

        registerForContextMenu(imageView);
        ActivityCompat.requestPermissions(
                DetailedImageActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},999

        );


        Picasso.with(this.getApplicationContext()).load(getIntent().getStringExtra("link")).resize(800,800).into(imageView);
        Intent intent = getIntent();
        list.add("User:  "+ intent.getStringExtra("user"));
        list.add("Time:  "+ intent.getStringExtra("time"));
        list.add("Des:  "+ intent.getStringExtra("description"));
        ArrayAdapter<String> aADapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(aADapter);

    }
}
