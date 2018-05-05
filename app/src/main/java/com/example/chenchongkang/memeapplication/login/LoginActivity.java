package com.example.chenchongkang.memeapplication.login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.example.chenchongkang.memeapplication.MainActivity;
import com.example.chenchongkang.memeapplication.R;
import com.example.chenchongkang.memeapplication.api.HttpHandler;
import com.example.chenchongkang.memeapplication.model.MemeBean;
import com.example.chenchongkang.memeapplication.model.UserBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by chenchongkang on 2018/3/11.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences userInfor;
    private SharedPreferences.Editor userEditor;
    private String a;
    private UserBean userBean;

    private EditText username;
    private EditText password;
    private long firstTime = 0;
    final private int UPDATE_A=1;

    @SuppressLint("HandlerLeak")
    private Handler handler =new Handler(){

         public void handleMessage(Message msg){
             switch (msg.what){
                 case UPDATE_A:
                     Gson gson = new Gson();
                     userBean = gson.fromJson(msg.obj.toString(), UserBean.class);
                     userEditor = userInfor.edit();
                     // 设置参数
                     userEditor.putString("username", mPref.getString("user_name", ""));
                     userEditor.putInt("userid", userBean.getUserID());
                     userEditor.putString("password", userBean.getPassword());
                     userEditor.putString("address", userBean.getAddress());
                     userEditor.putString("phone", userBean.getPhonenumber());
                     userEditor.putString("qq", userBean.getQq());
                     userEditor.putString("avatar", userBean.getAvatar());
                     userEditor.putString("intro", userBean.getIntroduction());
                     // 提交
                     userEditor.commit();

                     Intent intent_1 = new Intent(LoginActivity.this, MainActivity.class);
                     intent_1.putExtra("userName",userBean.getUserName());
                     startActivity(intent_1);
                     break;
                     default:
                         break;

             }
         }

    };



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


        mPref = getSharedPreferences("user_data", MODE_PRIVATE);
        userInfor = LoginActivity.this.getSharedPreferences("config", Context.MODE_PRIVATE);
//        SharedPreferences sharedPre = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        mEditor = mPref.edit();
        username.setText(mPref.getString("user_name", ""));
        password.setText(mPref.getString("user_password", ""));

        new Thread() {
            public void run() {
                findUserInform();
            }
        }.start();
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
                        String s = HttpHandler.executeHttpPost("http://10.64.70.53:8081/meme/login", jsonObject.toString());
                        if ("success".equals(s)) {
                            mEditor.putString("user_name", name);
                            mEditor.putString("user_password", pass);
                            mEditor.commit();
                            try {
                                 a= URLEncoder.encode(name, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            String jsondata = HttpHandler.executeHttpPost("http://10.64.70.53:8081/meme/username/" +a, null);
                            Message message =new Message();
                            message.what =UPDATE_A;
                            message.obj = jsondata;
                            handler.sendMessage(message);


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

    private void findUserInform() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
            }
        });
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
