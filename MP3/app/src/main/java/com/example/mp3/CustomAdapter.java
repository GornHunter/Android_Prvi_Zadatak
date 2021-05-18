package com.example.mp3;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    ArrayList<SingleRow> list;
    Context context;

    CustomAdapter(Context context){
        this.context = context;
        this.list = new ArrayList<SingleRow>();
        Resources resource = context.getResources();
        String[] singers = resource.getStringArray(R.array.singers);
        String[] songs = resource.getStringArray(R.array.songs);
        int[] images = {R.drawable.justin_timberlake, R.drawable.eminem, R.drawable.ed_sheeran, R.drawable.john_denver, R.drawable.bruno_mars};
        int[] mp3s = {R.raw.cant_stop_the_feeling, R.raw.not_afraid, R.raw.shape_of_you, R.raw.take_me_home_country_roads, R.raw.the_lazy_song};

        for(int i = 0;i < singers.length;i++)
            list.add(new SingleRow(singers[i], songs[i], images[i], mp3s[i]));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = (View) inflater.inflate(R.layout.single_row, parent, false);
        }

        ImageView imgView = (ImageView) view.findViewById(R.id.imageView);
        TextView textViewSinger = (TextView) view.findViewById(R.id.textViewSinger);
        TextView textViewSong = (TextView) view.findViewById(R.id.textViewSong);

        SingleRow sr = list.get(position);

        imgView.setImageResource(sr.getImage());
        textViewSinger.setText(sr.getSinger());
        textViewSong.setText(sr.getSong());

        return view;
    }
}