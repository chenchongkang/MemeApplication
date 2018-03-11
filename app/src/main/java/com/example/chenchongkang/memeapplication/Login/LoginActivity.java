package com.example.chenchongkang.memeapplication.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.chenchongkang.memeapplication.R;

/**
 * Created by chenchongkang on 2018/3/11.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
    //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

    }

    @Override
    public void onClick(View v) {

    }
}
