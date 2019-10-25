package com.example.homework_musicapp;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private ArrayList<SongDetails> listSongs;
    private LayoutInflater songInf;

    public MyAdapter(Context context, ArrayList<SongDetails> listSongs) {
        this.listSongs = listSongs;
        songInf = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listSongs.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout songLayout = (LinearLayout) songInf.inflate(R.layout.listview_item, parent, false);

        TextView nameText = (TextView) songLayout.findViewById(R.id.textName);
        TextView desText = (TextView) songLayout.findViewById(R.id.textDes);
        ImageView imgView = (ImageView) songLayout.findViewById(R.id.imageView);

        SongDetails currentSong = listSongs.get(position);
        nameText.setText(currentSong.getName());
        desText.setText(currentSong.getDescription());
        imgView.setImageResource(currentSong.getAvatar());

        songLayout.setTag((position));

        return songLayout;
    }
}
