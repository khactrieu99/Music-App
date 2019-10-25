package com.example.homework_musicapp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.List;
public class MyView extends View {
    Integer[] _img_ids = {
            R.drawable.cat_00,
            R.drawable.cat_01,
            R.drawable.cat_02,
            R.drawable.cat_03,
            R.drawable.cat_04,
            R.drawable.cat_05,
            R.drawable.cat_06,
            R.drawable.cat_07,
            R.drawable.cat_08,
            R.drawable.cat_09,
            R.drawable.cat_10,
            R.drawable.cat_11,
            R.drawable.cat_12,
            R.drawable.cat_13,
            R.drawable.cat_14,
            R.drawable.cat_15,
            R.drawable.cat_16
    };
    CountDownTimer _countDownTimer;
    int _next = 0;
    int sign = 1;

    public MyView(Context context) {
        super(context);

        _countDownTimer = new CountDownTimer(
                500, 100) {
            @Override
            public void onTick(long l) {
                MyView.this.invalidate();
            }

            @Override
            public void onFinish() {
                this.start();
            }
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),
                _img_ids[_next]), 0, 0, null);
        _next = (_next+1) % _img_ids.length;
    }

    public void startAnimation()
    {
        _countDownTimer.start();
    }

    public void stopAnimation()
    {
        _countDownTimer.cancel();
    }
}
