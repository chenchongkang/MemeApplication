package com.example.chenchongkang.memeapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by chenchongkang on 2018/5/11.
 */

public class UserInfoUpdate extends AppCompatActivity implements View.OnClickListener {
    private String userinfo;
    private String userinfo1;
    private String userinfo2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏ActionBar
        getSupportActionBar().hide();
        setContentView(R.layout.update_userinfor);
        Intent intent=getIntent();
        userinfo=intent.getStringExtra("userInfo");
        userinfo2=intent.getStringExtra("rlname");

        Toast.makeText(UserInfoUpdate.this,userinfo,Toast.LENGTH_SHORT).show();
        EditText editText=(EditText)findViewById(R.id.et_userUpdate);
        editText.setText(userinfo);
        TextView textView=(TextView)findViewById(R.id.submit_userInfo);
        textView.setOnClickListener(this);
        ImageView imageView=(ImageView)findViewById(R.id.return_userInfo);
        imageView.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_userInfo:

                EditText editText=(EditText)findViewById(R.id.et_userUpdate);
                userinfo1=editText.getText().toString().trim();
                Intent intent = new Intent();
                intent.putExtra("a", userinfo1);
                intent.putExtra("b",userinfo2);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.return_userInfo:
                finish();
                default:
                    break;
        }


    }
}
