package com.example.jingbo_multipaneshoppingapp;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailsActivity extends AppCompatActivity {

    private float x = 0f;
    private boolean isScroll = false;
    private float scollX = 0f;
    private View decorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        decorView = getWindow().getDecorView();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                float mX = event.getX();
                scollX = mX - x;

                if (scollX > 0) {
                    isScroll = true;
                    decorView.setTranslationX(scollX);
                }
                break;

            case MotionEvent.ACTION_UP:
                if (isScroll && scollX > 300) {
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                } else {
                    isScroll = false;
                    decorView.setTranslationX(0);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public  boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            float x = ev.getX();
            if (x <= 50) {
                isScroll = true;
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
