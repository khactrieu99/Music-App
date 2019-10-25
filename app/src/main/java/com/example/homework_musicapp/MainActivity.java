package com.example.homework_musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<SongDetails> songList;
    private ListView songView;

    static public int[] listSongs = {R.raw.aa, R.raw.cswy, R.raw.fl, R.raw.hdiyl, R.raw.lt, R.raw.ts, R.raw.wu};
    static public String[] listName = {"Alone Again", "Can't Smile Without You", "Fallen", "How Deep Is Your Love", "Lemon Tree", "The Scientist", "What's Up"};
    static public int[] listImage = {R.drawable.aa, R.drawable.cswy, R.drawable.fl, R.drawable.hdiyl, R.drawable.lt, R.drawable.ts, R.drawable.wu};
    static public String[] listDes = {"Gilbert O'Sullivan","Barry Manilow", "Gert Taberner", "Bee Gees", "Fool’s Garden", "Coldplay", "4 Non Blondes" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.songView = (ListView)findViewById(R.id.listSong);
        this.songList = initData();

        fillData();

        songView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, Integer.toString(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, PlaySong.class);

                intent.putExtra("image", songList.get(position).getAvatar());
                intent.putExtra("songName", songList.get(position).getName());
                intent.putExtra("songArtist", songList.get(position).getDescription());
                intent.putExtra("songId", songList.get(position).getSongId());
                intent.putExtra("currentPos", position);

                intent.putExtra("listSong", MainActivity.listSongs);
                intent.putExtra("listName", MainActivity.listName);
                intent.putExtra("listImage", MainActivity.listImage);
                intent.putExtra("listDes", MainActivity.listDes);

                MainActivity.this.startActivity(intent);
            }
        });
    }

    protected void fillData() {
        MyAdapter myAdapter = new MyAdapter(this, songList);
        songView.setAdapter(myAdapter);
    }

    protected ArrayList<SongDetails> initData() {
        ArrayList<SongDetails> list = new ArrayList<SongDetails>();
        for(int i = 0; i<7; i++){
            list.add(new SongDetails(listName[i], listDes[i], listImage[i], listSongs[i]));
        }

        return list;
    }
}
