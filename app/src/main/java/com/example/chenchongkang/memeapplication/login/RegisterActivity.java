package com.example.chenchongkang.memeapplication.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chenchongkang.memeapplication.MainActivity;
import com.example.chenchongkang.memeapplication.R;
import com.example.chenchongkang.memeapplication.api.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chenchongkang on 2018/3/11.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText username;
    private EditText password1;
    private EditText password2;
    private EditText phonenumber;
    private EditText address;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        //找到要用到的控件
        username=(EditText)findViewById(R.id.et_username);
        password1=(EditText)findViewById(R.id.et_password_one);
        password2=(EditText)findViewById(R.id.et_password_two);
        phonenumber=(EditText)findViewById(R.id.et_phone_number);
        address=(EditText)findViewById(R.id.et_address);
        Button button =(Button)findViewById(R.id.bt_register);

        button.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        final String name = username.getText().toString().trim();
        final String pass = password1.getText().toString().trim();
        final String phone = phonenumber.getText().toString().trim();
        final String addr = address.getText().toString().trim();
        switch(v.getId()){
            case R.id.bt_register:

                if (checkEdit()) {
                    new Thread() {
                        public void run() {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("userName", name);
                                jsonObject.put("password", pass);
                                jsonObject.put("phonenumber", phone);
                                jsonObject.put("address", addr);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String s = HttpHandler.executeHttpPost("http://172.24.7.1:8081/meme/register", jsonObject.toString());
                            if ("success".equals(s)) {
                                Intent intent_1 = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent_1);
                            } else {
                                showToast(s);
                            }
                        }
                    }.start();
                }

                break;

            default:
                    break;
        }

    }
    public boolean checkEdit(){
        if(username.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password1.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password2.getText().toString().equals(password1.getText().toString())){
            Toast.makeText(RegisterActivity.this, "两次输入的密码不同", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(address.getText().toString().equals("")){
            Toast.makeText(RegisterActivity.this, "邮箱不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void showToast(final String text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this,text,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
