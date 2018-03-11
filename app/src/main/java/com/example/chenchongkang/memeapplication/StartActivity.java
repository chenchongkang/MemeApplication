package com.example.chenchongkang.memeapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.chenchongkang.memeapplication.Login.LoginActivity;

/**
 * Created by chenchongkang on 2018/2/5.
 */

public class StartActivity extends Activity {
    private ImageView startview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        startview = (ImageView) this.findViewById(R.id.start_view);
        AlphaAnimation anima = new AlphaAnimation(0.3f, 1.0f);
        anima.setDuration(2000);// 设置动画显示时间
        startview.startAnimation(anima);
        anima.setAnimationListener(new AnimationImpl());

    }

    private class AnimationImpl implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            startview.setBackgroundResource(R.drawable.ic_startfour);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            skip(); // 动画结束后跳转到别的页面
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

    }

    private void skip() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
