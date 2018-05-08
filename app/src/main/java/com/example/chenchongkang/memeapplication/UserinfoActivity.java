package com.example.chenchongkang.memeapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by chenchongkang on 2018/4/16.
 */

public class UserinfoActivity extends AppCompatActivity implements View.OnClickListener{

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //隐藏ActionBar
            getSupportActionBar().hide();
            setContentView(R.layout.personalfg_userinformation);
            SharedPreferences userInfor = UserinfoActivity.this.getSharedPreferences("config", Context.MODE_PRIVATE);
            int userid = userInfor.getInt("userid", 0);
            String username= userInfor.getString("username","");
            String userqq = userInfor.getString("qq","");
            String useraddress=userInfor.getString("address","");
            String userIntro=userInfor.getString("intro","");

            ImageView imageView= (ImageView) findViewById(R.id.info_return);
            ImageView avatar =(ImageView)findViewById(R.id.iv_avatar);
            TextView tvusername =(TextView)findViewById(R.id.tv_username);
            TextView tvqq=(TextView)findViewById(R.id.tv_qq);
            TextView tvadress=(TextView)findViewById(R.id.tv_adress);
            TextView tvIntro=(TextView)findViewById(R.id.tv_Intro);


            imageView.setOnClickListener(this);
            Glide.with(this).load("http://192.168.43.87:8081/meme/getuseravatar/" + userid)
                  .placeholder(R.drawable.ic_startone)
                  .into(avatar);
            tvusername.setText(username);
            tvqq.setText(userqq);
            tvadress.setText(useraddress);
            tvIntro.setText(userIntro);
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_return:
                finish();
                break;
            default:
                break;
        }

    }
}
