package com.example.chenchongkang.memeapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by chenchongkang on 2018/4/16.
 */

public class PersonalFgSzNumbersafe extends AppCompatActivity implements View.OnClickListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏ActionBar
        getSupportActionBar().hide();
        setContentView(R.layout.sz_numbersafe);
        ImageView imageView= (ImageView) findViewById(R.id.sz_numberSafe_return);
        imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sz_numberSafe_return:
                finish();
                break;
            default:
                break;
        }

    }
}