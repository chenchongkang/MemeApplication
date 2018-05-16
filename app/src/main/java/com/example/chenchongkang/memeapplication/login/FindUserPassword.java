package com.example.chenchongkang.memeapplication.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenchongkang.memeapplication.R;
import com.example.chenchongkang.memeapplication.api.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;




/**
 * Created by chenchongkang on 2018/5/14.
 */

public class FindUserPassword extends AppCompatActivity implements View.OnClickListener {

    private EditText username;
    private EditText useraddress;
    private EditText userphone;
    private TextView find;
    private TextView view;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏ActionBar
        getSupportActionBar().hide();
        setContentView(R.layout.finduserpassword);
        ImageView imageView=(ImageView)findViewById(R.id.return_findPassword);
        username=(EditText)findViewById(R.id.et_findusername);
        useraddress=(EditText)findViewById(R.id.et_findaddress);
        userphone=(EditText)findViewById(R.id.et_findphone);
        find=(TextView) findViewById(R.id.tv_find);
        view=(TextView)findViewById(R.id.et_password_view);
        find.setOnClickListener(this);
        imageView.setOnClickListener(this);






    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.return_findPassword:
                finish();
                break;
            case R.id.tv_find:
                //获取用户名密码
                final String name = username.getText().toString().trim();
                final String address = useraddress.getText().toString().trim();
                final String phone = userphone.getText().toString().trim();


                new Thread() {
                    public void run() {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("userName", name);
                            jsonObject.put("address", address);
                            jsonObject.put("phonenumber",phone);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String s = HttpHandler.executeHttpPost("http://192.168.43.87:8081/meme/finduserpass", jsonObject.toString());
                        showToast(s);
                        }
                }.start();
                break;
                default:
                    break;

        }

    }

    private void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(FindUserPassword.this,text,Toast.LENGTH_SHORT).show();
                view.setText(text);
            }

        });
    }
}
