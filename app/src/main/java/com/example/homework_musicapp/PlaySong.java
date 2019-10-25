package com.example.homework_musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PlaySong extends AppCompatActivity {

    int[] listSongs;
    String[] listName;
    int[] listImage;
    String[] listDes;

    MyView myView;
    LinearLayout animation;

    TextView timeBar;
    ImageButton playPause, next, previous;
    LinearLayout page;
    int playState = 1;
    ImageView imageHolder;
    TextView songName;
    TextView songArtist;
    MediaPlayer mediaPlayer;
    MediaPlayer.OnCompletionListener onCompletionListener;
    int currentPos;
    int currentSong;
    SeekBar seekBar;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_song);

        page = (LinearLayout) findViewById(R.id.playPage);

        playPause = (ImageButton) page.findViewById(R.id.playPause);
        next = (ImageButton) page.findViewById(R.id.next);
        previous = (ImageButton) page.findViewById(R.id.previous);

        Intent intent = this.getIntent();

        listSongs = intent.getIntArrayExtra("listSong");
        listName = intent.getStringArrayExtra("listName");
        listImage = intent.getIntArrayExtra("listImage");
        listDes = intent.getStringArrayExtra("listDes");

//        int image = intent.getIntExtra("image", 0);
//        imageHolder = (ImageView)page.findViewById(R.id.imageSong);
//        imageHolder.setImageResource(image);

        animation = this.findViewById(R.id.animation);
        myView = new MyView(this.getBaseContext());
        animation.addView(myView);
        myView.startAnimation();
        myView.setX(300);
        myView.setY(200);


        String nameOfSong = intent.getStringExtra("songName");
        songName = (TextView) page.findViewById(R.id.songName);
        songName.setText(nameOfSong);

        String artistOfSong = intent.getStringExtra("songArtist");
        songArtist = (TextView) page.findViewById(R.id.songArtist);
        songArtist.setText(artistOfSong);

        timeBar = (TextView) page.findViewById(R.id.timeBar);

        currentPos = intent.getIntExtra("currentPos", 0);
        currentSong = intent.getIntExtra("songId", 0);

        // setup mediaPlayer
        mediaPlayer = MediaPlayer.create(this.getBaseContext(), currentSong);
        onCompletionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                getNextSong(mediaPlayer);
            }
        };

        mediaPlayer.setOnCompletionListener(onCompletionListener);
        mediaPlayer.start();

        // set image for button
        playPause.setBackgroundResource(R.drawable.pause);

        // init seekBar
        seekBar = (SeekBar) page.findViewById(R.id.seekBar);
        seekBar.setMax(mediaPlayer.getDuration() / 1000);

        // update seekBar
        mHandler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null && mediaPlayer.isPlaying()) {
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                    int min = mCurrentPosition / 60;
                    int sec = mCurrentPosition - (min * 60);
                    String minute = (min<10)?"0"+min:""+min;
                    String second = (sec<10)?"0"+sec:""+sec;
                    timeBar.setText(minute + ":" + second);
                }
                mHandler.postDelayed(this, 1000);
            }
        };
        mHandler.postDelayed(runnable, 1000);

        // seekBar update
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer != null  && fromUser){
                    mediaPlayer.seekTo(progress * 1000);
                    int min = progress / 60;
                    int sec = progress - (min * 60);
                    String minute = (min<10)?"0"+min:""+min;
                    String second = (sec<10)?"0"+sec:""+sec;
                    timeBar.setText(minute + ":" + second);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    public void onPlayClicked(View view) {
        if (playState == 0){
            playPause.setBackgroundResource(R.drawable.pause);
            mediaPlayer.start();
        }
        else {
            playPause.setBackgroundResource(R.drawable.play);
            mediaPlayer.pause();
        }
        playState = ++playState % 2;
    }

    protected void getNextSong(MediaPlayer mediaPlayer) {
        PlaySong.this.currentPos = ++PlaySong.this.currentPos % PlaySong.this.listSongs.length;

//        int image = PlaySong.this.listImage[PlaySong.this.currentPos];
//        PlaySong.this.imageHolder = (ImageView)PlaySong.this.page.findViewById(R.id.imageSong);
//        PlaySong.this.imageHolder.setImageResource(image);

        String nameOfSong = PlaySong.this.listName[PlaySong.this.currentPos];
        PlaySong.this.songName = (TextView) page.findViewById(R.id.songName);
        PlaySong.this.songName.setText(nameOfSong);

        String artistOfSong = PlaySong.this.listDes[PlaySong.this.currentPos];
        PlaySong.this.songArtist = (TextView) PlaySong.this.page.findViewById(R.id.songArtist);
        PlaySong.this.songArtist.setText(artistOfSong);

        PlaySong.this.currentSong = PlaySong.this.listSongs[PlaySong.this.currentPos];

        PlaySong.this.mediaPlayer = MediaPlayer.create(PlaySong.this.getBaseContext(), PlaySong.this.currentSong);

        PlaySong.this.seekBar.setMax(PlaySong.this.mediaPlayer.getDuration() / 1000);
        PlaySong.this.seekBar.setProgress(0);

        PlaySong.this.mediaPlayer.setOnCompletionListener(PlaySong.this.onCompletionListener);

        PlaySong.this.timeBar.setText("00:00");

        PlaySong.this.mediaPlayer.start();
    }

    public void onNextClick(View view) {
        this.mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;

        this.currentPos = ++this.currentPos % this.listSongs.length;

//        int image = this.listImage[this.currentPos];
//        this.imageHolder = (ImageView)this.page.findViewById(R.id.imageSong);
//        this.imageHolder.setImageResource(image);

        String nameOfSong = this.listName[this.currentPos];
        this.songName = (TextView) page.findViewById(R.id.songName);
        this.songName.setText(nameOfSong);

        String artistOfSong = this.listDes[this.currentPos];
        this.songArtist = (TextView) this.page.findViewById(R.id.songArtist);
        this.songArtist.setText(artistOfSong);

        this.currentSong = this.listSongs[this.currentPos];

        this.mediaPlayer = MediaPlayer.create(this.getBaseContext(), this.currentSong);

        this.seekBar.setMax(this.mediaPlayer.getDuration() / 1000);
        this.seekBar.setProgress(0);

        this.mediaPlayer.setOnCompletionListener(this.onCompletionListener);
        PlaySong.this.timeBar.setText("00:00");

        this.mediaPlayer.start();
    }

    public void onPreClick(View view) {
        this.mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;

        this.currentPos = (--this.currentPos + this.listSongs.length) % this.listSongs.length;

//        int image = this.listImage[this.currentPos];
////        this.imageHolder = (ImageView)this.page.findViewById(R.id.imageSong);
////        this.imageHolder.setImageResource(image);

        String nameOfSong = this.listName[this.currentPos];
        this.songName = (TextView) page.findViewById(R.id.songName);
        this.songName.setText(nameOfSong);

        String artistOfSong = this.listDes[this.currentPos];
        this.songArtist = (TextView) this.page.findViewById(R.id.songArtist);
        this.songArtist.setText(artistOfSong);

        this.currentSong = this.listSongs[this.currentPos];

        this.mediaPlayer = MediaPlayer.create(this.getBaseContext(), this.currentSong);

        this.seekBar.setMax(this.mediaPlayer.getDuration() / 1000);
        this.seekBar.setProgress(0);

        this.mediaPlayer.setOnCompletionListener(this.onCompletionListener);

        PlaySong.this.timeBar.setText("00:00");

        this.mediaPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void onStopClicked(View view) {
        this.onBackPressed();
    }
}
