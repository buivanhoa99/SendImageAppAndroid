package com.example.bathi.uploadimagevolleydemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GridImageAdapter extends ArrayAdapter {
    private Context mcontext;
    private  int mResourse;
    public GridImageAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        mcontext = context;
        mResourse = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String url = getItem(position)+"";

        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = inflater.inflate(mResourse,parent,false);

        ImageView img = convertView.findViewById(R.id.imgView);
        Picasso.with(mcontext).load(url).resize(300,300).into(img);
        return convertView;
    }
}
