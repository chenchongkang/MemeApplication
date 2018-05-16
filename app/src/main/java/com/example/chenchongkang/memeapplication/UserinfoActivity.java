package com.example.chenchongkang.memeapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.chenchongkang.memeapplication.api.HttpHandler;
import com.example.chenchongkang.memeapplication.login.LoginActivity;
import com.example.chenchongkang.memeapplication.login.RegisterActivity;
import com.example.chenchongkang.memeapplication.model.MemeBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by chenchongkang on 2018/4/16.
 */

public class UserinfoActivity extends AppCompatActivity implements View.OnClickListener{
    static final private int GET_CODE = 0;
    private String username;
    private String userqq;
    private String useraddress;
    private String userIntro;
    private String pass;
    private String phone;
    private String avatar;
    private int userid;
    private String rlname;
    private TextView tvusername;
    private TextView tvqq;
    private TextView tvadress;
    private TextView tvIntro;

    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //隐藏ActionBar
            getSupportActionBar().hide();
            setContentView(R.layout.personalfg_userinformation);
            SharedPreferences userInfor = UserinfoActivity.this.getSharedPreferences("config", Context.MODE_PRIVATE);
            userid = userInfor.getInt("userid", 0);
            username= userInfor.getString("username","");
            userqq = userInfor.getString("qq","");
            useraddress=userInfor.getString("address","");
            userIntro=userInfor.getString("intro","");
            pass=userInfor.getString("password","");
            phone=userInfor.getString("phone","");
            avatar=userInfor.getString("avatar","");

            ImageView imageView= (ImageView) findViewById(R.id.info_return);
            ImageView avatar =(ImageView)findViewById(R.id.iv_avatar);
            tvusername =(TextView)findViewById(R.id.tv_username);
            tvqq=(TextView)findViewById(R.id.tv_qq);
            tvadress=(TextView)findViewById(R.id.tv_adress);
            tvIntro=(TextView)findViewById(R.id.tv_Intro);
            RelativeLayout rlusername=(RelativeLayout)findViewById(R.id.rl_username);
            RelativeLayout rluserqq=(RelativeLayout)findViewById(R.id.rl_userqq);
            RelativeLayout rluseraddress=(RelativeLayout)findViewById(R.id.rl_useraddress);
            LinearLayout lluserintro=(LinearLayout) findViewById(R.id.ll_userintro);


            tvusername.setText(username);
            tvqq.setText(userqq);
            tvadress.setText(useraddress);
            tvIntro.setText(userIntro);

            rlusername.setOnClickListener(this);
            rluserqq.setOnClickListener(this);
            rluseraddress.setOnClickListener(this);
            lluserintro.setOnClickListener(this);


            imageView.setOnClickListener(this);
            Glide.with(this).load("http://192.168.43.87:8081/meme/getuseravatar/" + userid)
                  .placeholder(R.drawable.ic_startone)
                  .into(avatar);

        }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_CODE) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(UserinfoActivity.this,"点击了返回",Toast.LENGTH_SHORT).show();
            } else {
                if (data != null) {
                   if (data.getStringExtra("b").equals("username")){
                       tvusername.setText(data.getStringExtra("a"));
                   }
                    if (data.getStringExtra("b").equals("userqq")){
                        tvqq.setText(data.getStringExtra("a"));
                    }
                    if (data.getStringExtra("b").equals("useraddress")){
                        tvadress.setText(data.getStringExtra("a"));
                    }
                    if (data.getStringExtra("b").equals("userintro")){
                        tvIntro.setText(data.getStringExtra("a"));
                    }
                    new Thread(){
                        public void run(){
                            parseJOSNWithGSONEUserUpdate();
                        }
                    }.start();
                }
            }
        }
    }

    private void parseJOSNWithGSONEUserUpdate() {
        String name=tvusername.getText().toString().trim();
        String qq=tvqq.getText().toString().trim();
        String addr=tvadress.getText().toString().trim();
        String intro=tvIntro.getText().toString().trim();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userName",name);
            jsonObject.put("password",pass);
            jsonObject.put("address",addr);
            jsonObject.put("phonenumber",phone);
            jsonObject.put("avatar",avatar);
            jsonObject.put("qq",qq);
            jsonObject.put("introduction",intro);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String jsondata = HttpHandler.executeHttpPost("http://192.168.43.87:8081/meme/entityuserupdates/"+userid, jsonObject.toString());
        if ("success".equals(jsondata)) {
            showToast(jsondata);
        } else {
            showToast(jsondata);
        }
    }
    private void showToast(final String text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (text.equals("0")){
                    tvusername.setText(username);
                    Toast.makeText(UserinfoActivity.this,"该用户名已经存在",Toast.LENGTH_SHORT).show();
                }
                    Toast.makeText(UserinfoActivity.this,text,Toast.LENGTH_SHORT).show();
                SharedPreferences userInfor = UserinfoActivity.this.getSharedPreferences("config", Context.MODE_PRIVATE);
                SharedPreferences.Editor userEditor = userInfor.edit();
                // 设置参数
                userEditor.putString("username",tvusername.getText().toString().trim());
                userEditor.putString("address",tvadress.getText().toString().trim());
                userEditor.putString("qq",tvqq.getText().toString().trim());
                userEditor.putString("intro",tvIntro.getText().toString().trim());
                // 提交
                userEditor.commit();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_return:
                finish();
                break;
            case R.id.rl_username:
                rlname="username";
                Intent intent = new Intent(UserinfoActivity.this, UserInfoUpdate.class);
                intent.putExtra("userInfo",tvusername.getText().toString().trim());
                intent.putExtra("rlname",rlname);
                startActivityForResult(intent,GET_CODE);
                break;
            case R.id.rl_userqq:
                rlname="userqq";
                Intent intent_1 = new Intent(UserinfoActivity.this, UserInfoUpdate.class);
                intent_1.putExtra("userInfo",tvqq.getText().toString().trim());
                intent_1.putExtra("rlname",rlname);
                startActivityForResult(intent_1,GET_CODE);
                break;
            case R.id.rl_useraddress:
                rlname="useraddress";
                Intent intent_2 = new Intent(UserinfoActivity.this, UserInfoUpdate.class);
                intent_2.putExtra("userInfo",tvadress.getText().toString().trim());
                intent_2.putExtra("rlname",rlname);
                startActivityForResult(intent_2,GET_CODE);
                break;
            case R.id.ll_userintro:
                rlname="userintro";
                Intent intent_3 = new Intent(UserinfoActivity.this, UserInfoUpdate.class);
                intent_3.putExtra("userInfo",tvIntro.getText().toString().trim());
                intent_3.putExtra("rlname",rlname);
                startActivityForResult(intent_3,GET_CODE);
                break;

            default:
                break;
        }

    }
}
