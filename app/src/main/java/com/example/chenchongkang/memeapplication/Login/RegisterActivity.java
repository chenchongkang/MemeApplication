package com.example.chenchongkang.memeapplication.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.chenchongkang.memeapplication.MainActivity;
import com.example.chenchongkang.memeapplication.R;

/**
 * Created by chenchongkang on 2018/3/11.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        Button button =(Button)findViewById(R.id.bt_register);
        button.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_register:
                Intent intent_1 = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent_1);
                break;
            default:
                    break;
        }

    }
}
