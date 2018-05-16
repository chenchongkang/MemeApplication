package com.example.chenchongkang.memeapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenchongkang.memeapplication.api.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenchongkang on 2018/4/16.
 */

public class PersonalFgSzNumbersafe extends AppCompatActivity implements View.OnClickListener {
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
    private TextView tvname;
    private TextView tvphone;
    private String userpass;
    private String a;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏ActionBar
        getSupportActionBar().hide();
        setContentView(R.layout.sz_numbersafe);

        SharedPreferences userInfor = PersonalFgSzNumbersafe.this.getSharedPreferences("config", Context.MODE_PRIVATE);
        userid = userInfor.getInt("userid", 0);
        username= userInfor.getString("username","");
        userqq = userInfor.getString("qq","");
        useraddress=userInfor.getString("address","");
        userIntro=userInfor.getString("intro","");
        pass=userInfor.getString("password","");
        phone=userInfor.getString("phone","");
        avatar=userInfor.getString("avatar","");

        ImageView imageView= (ImageView) findViewById(R.id.sz_numberSafe_return);
        imageView.setOnClickListener(this);
        tvname=(TextView)findViewById(R.id.tv_name);
        tvphone=(TextView)findViewById(R.id.tv_phone);
        tvname.setText(username);
        tvphone.setText(phone);

        RelativeLayout rluserPhone=(RelativeLayout)findViewById(R.id.rl_userPhone);
        RelativeLayout rluserPass=(RelativeLayout)findViewById(R.id.rl_userPass);
        rluserPhone.setOnClickListener(this);
        rluserPass.setOnClickListener(this);

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_CODE) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(PersonalFgSzNumbersafe.this,"点击了返回",Toast.LENGTH_SHORT).show();
            } else {
                if (data != null) {
                    if (data.getStringExtra("b").equals("userPhone")){
                        tvphone.setText(data.getStringExtra("a"));
                    }
                    if(data.getStringExtra("b").equals("userPass")){
                        userpass=data.getStringExtra("a");
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
        if (userpass==null||userpass.equals("")) {
            a=pass;
        }else {
            a=userpass;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userName",username);
            jsonObject.put("password",a);
            jsonObject.put("address",useraddress);
            jsonObject.put("phonenumber",tvphone.getText().toString().trim());
            jsonObject.put("avatar",avatar);
            jsonObject.put("qq",userqq);
            jsonObject.put("introduction",userIntro);

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
                Toast.makeText(PersonalFgSzNumbersafe.this,text,Toast.LENGTH_SHORT).show();
                SharedPreferences userInfor = PersonalFgSzNumbersafe.this.getSharedPreferences("config", Context.MODE_PRIVATE);
                SharedPreferences.Editor userEditor = userInfor.edit();
                // 设置参数
                userEditor.putString("phone",tvphone.getText().toString().trim());
                userEditor.putString("password",a);
                // 提交
                userEditor.commit();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sz_numberSafe_return:
                finish();
                break;
            case R.id.rl_userPhone:
                rlname="userPhone";
                Intent intent_1 = new Intent(PersonalFgSzNumbersafe.this, UserInfoUpdate.class);
                intent_1.putExtra("userInfo",tvphone.getText().toString().trim());
                intent_1.putExtra("rlname",rlname);
                startActivityForResult(intent_1,GET_CODE);
                break;
            case R.id.rl_userPass:
                rlname="userPass";
                Intent intent_2 = new Intent(PersonalFgSzNumbersafe.this, PasswordUpdate.class);
                intent_2.putExtra("userInfo",pass);
                intent_2.putExtra("rlname",rlname);
                startActivityForResult(intent_2,GET_CODE);
                break;
            default:
                break;
        }

    }
}
