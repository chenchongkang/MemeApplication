package com.example.chenchongkang.memeapplication.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenchongkang.memeapplication.MainActivity;
import com.example.chenchongkang.memeapplication.PersonalFgGy;
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
        TextView textView=(TextView) findViewById(R.id.xyhzc);
        Button button=(Button)findViewById(R.id.bt_login);
        textView.setOnClickListener(this);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:
                Intent intent_1 = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent_1);
                break;
            case R.id.xyhzc:
                Intent intent_2 = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent_2);
                break;
                default:
                    break;
        }
        }
    private long firstTime = 0;

    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            Toast.makeText(LoginActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else {
            System.exit(0);
        }
    }

}
