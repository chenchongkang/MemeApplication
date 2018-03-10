package com.example.chenchongkang.memeapplication;

import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Created by chenchongkang on 2018/3/7.
 */

public class PersonalFgPj extends AppCompatActivity implements View.OnClickListener {
    TextView show = null;
    RatingBar star = null;

    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalfg_pj);
        ImageButton imageButton = (ImageButton) findViewById(R.id.return_4);
        imageButton.setOnClickListener(this);

        show = (TextView) findViewById(R.id.show);
        star = (RatingBar) findViewById(R.id.star);

        //为星级评分条添加动作监听事件
        star.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
                //arg1表示当前评分条中选中星星的个数
                float values = arg1;
                if (values < 1) {
                    show.setText(values + "分，掀桌子不干了！");
                } else if (values < 4) {
                    show.setText("NO！才" + values + "分，我会加油的~");
                } else if (values < 5) {
                    show.setText(values + "分耶~ 谢谢你的肯定");
                } else {
                    show.setText("沃~ 满分，小尾巴上天！");
                }

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_4:
                finish();
                break;
            default:
                break;
        }

    }
}
