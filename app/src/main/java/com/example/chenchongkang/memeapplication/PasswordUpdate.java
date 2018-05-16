package com.example.chenchongkang.memeapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenchongkang.memeapplication.login.RegisterActivity;

/**
 * Created by chenchongkang on 2018/5/13.
 */

public class PasswordUpdate extends AppCompatActivity implements View.OnClickListener {

    private EditText password1;
    private EditText password2;
    private String userinfo1;
    private String userinfo2;
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passwordupdate);
        password1=(EditText)findViewById(R.id.et_passwordone);
        password2=(EditText)findViewById(R.id.et_passwordtwo);
        TextView submit=(TextView)findViewById(R.id.submit_userInfo1);
        submit.setOnClickListener(this);
        Intent intent=getIntent();
        userinfo2=intent.getStringExtra("rlname");
        ImageView returnpassupdata=(ImageView)findViewById(R.id.return_passupdata);
        returnpassupdata.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.return_passupdata:
                finish();
                break;
            case R.id.submit_userInfo1:
                if (password1.getText().toString().trim()==null||"".equals(password1.getText().toString().trim()))
                {
                    Toast.makeText(PasswordUpdate.this, "密码不能为空", Toast.LENGTH_SHORT).show();

                }
                else if(!password2.getText().toString().equals(password1.getText().toString())){
                    Toast.makeText(PasswordUpdate.this, "两次输入的密码不同", Toast.LENGTH_SHORT).show();
                    }
                  else {
                    EditText editText=(EditText)findViewById(R.id.et_passwordone);
                    userinfo1=editText.getText().toString().trim();
                    Intent intent = new Intent();
                    intent.putExtra("a", userinfo1);
                    intent.putExtra("b",userinfo2);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
        }




    }
}
