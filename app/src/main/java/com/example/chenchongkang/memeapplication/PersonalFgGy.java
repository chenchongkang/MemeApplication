package com.example.chenchongkang.memeapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by chenchongkang on 2018/3/8.
 */

public class PersonalFgGy extends AppCompatActivity implements View.OnClickListener {
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
    setContentView(R.layout.personalfg_gy);
    ImageButton imageButton = (ImageButton) findViewById(R.id.return_5);
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.return_5:
                    finish();
                    break;
                default:
                    break;


        }

    }
}
