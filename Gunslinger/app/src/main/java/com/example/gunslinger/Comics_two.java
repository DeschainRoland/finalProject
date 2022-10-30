package com.example.gunslinger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;

public class Comics_two extends AppCompatActivity implements ViewSwitcher.ViewFactory, GestureDetector.OnGestureListener {

    private ImageSwitcher mImageSwitcher;
    ImageButton button;

    int position = 0;
    private int[] mImageIds = {R.drawable.comics_first_page, R.drawable.comics_second_page,
            R.drawable.comics_third_page, R.drawable.comics_four_page, R.drawable.comics_five_page};

    private GestureDetector mGestureDetector;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comics_two);

        mImageSwitcher = findViewById(R.id.imageSwitcher_two);
        mImageSwitcher.setFactory(this);

        button = findViewById(R.id.button_two);

        Animation inAnimation = new AlphaAnimation(0, 1);
        inAnimation.setDuration(2000);
        Animation outAnimation = new AlphaAnimation(1, 0);
        outAnimation.setDuration(2000);

        mImageSwitcher.setInAnimation(inAnimation);
        mImageSwitcher.setOutAnimation(outAnimation);

        mImageSwitcher.setImageResource(mImageIds[0]);

        mGestureDetector = new GestureDetector(this, this);

        button.setEnabled(false);
        button.setVisibility(View.INVISIBLE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Comics_two.this, LevelListActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void setPositionNext() {
        position++;
        if (position > mImageIds.length - 1) {
            position = mImageIds.length - 1;
        }
    }

    public void setPositionPrev() {
        position--;
        if (position < 0) {
            position = 0;
        }
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        try {
            if (Math.abs(motionEvent.getY() - motionEvent1.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
            // справа налево

            if (motionEvent.getX() - motionEvent1.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(v) > SWIPE_THRESHOLD_VELOCITY) {
                setPositionNext();

                if (position == mImageIds.length - 1){
                    button.setEnabled(true);
                    button.setVisibility(View.VISIBLE);
                }

                mImageSwitcher.setImageResource(mImageIds[position]);

            } else if (motionEvent1.getX() - motionEvent.getX() > SWIPE_MIN_DISTANCE
                    && Math.abs(v1) > SWIPE_THRESHOLD_VELOCITY) {
                // слева направо
                setPositionPrev();
                mImageSwitcher.setImageResource(mImageIds[position]);
            }
        } catch (Exception e) {
            // nothing
            return true;
        }
        return true;
    }

    @Override
    public View makeView() {
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new
                ImageSwitcher.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        imageView.setBackgroundColor(0xFF000000);
        return imageView;
    }
}