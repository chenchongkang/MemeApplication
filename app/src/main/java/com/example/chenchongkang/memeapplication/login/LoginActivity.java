package com.example.chenchongkang.memeapplication.login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.chenchongkang.memeapplication.MainActivity;
import com.example.chenchongkang.memeapplication.R;
import com.example.chenchongkang.memeapplication.api.HttpHandler;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenchongkang on 2018/3/11.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username;
    private EditText password;
    private long firstTime = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏ActionBar
        getSupportActionBar().hide();

        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //找到需要使用的控件
        TextView textView = (TextView) findViewById(R.id.xyhzc);
        Button button = (Button) findViewById(R.id.bt_login);
        username = (EditText) findViewById(R.id.login_usrename);
        password = (EditText) findViewById(R.id.login_password);

        //控件点击调用
        textView.setOnClickListener(this);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:

                //获取用户名密码
                final String name = username.getText().toString().trim();
                final String pass = password.getText().toString().trim();

                new Thread() {
                    public void run() {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("userName", name);
                            jsonObject.put("password", pass);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String s = HttpHandler.executeHttpPost("http://172.22.34.2:8081/meme/login", jsonObject.toString());
                        if ("success".equals(s)) {
                            Intent intent_1 = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent_1);
                        } else {
                            showToast(s);
                        }
                    }
                }.start();

                break;

            case R.id.xyhzc:
                Intent intent_2 = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent_2);
                break;
            default:
                break;
        }
    }

    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            Toast.makeText(LoginActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else {
            System.exit(0);
        }
    }

    private void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
