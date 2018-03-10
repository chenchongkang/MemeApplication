package com.example.chenchongkang.memeapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by chenchongkang on 2018/3/7.
 */

public class PersonalFgXz extends AppCompatActivity implements View.OnClickListener{
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalfg_xz);
        ImageButton imageButton=(ImageButton)findViewById(R.id.return_2);
        imageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_2:
                finish();
                break;
            default:
                break;
        }

    }
}
