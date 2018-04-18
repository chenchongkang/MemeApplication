package com.example.chenchongkang.memeapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by chenchongkang on 2018/3/7.
 */

public class PersonalFgSz extends AppCompatActivity implements View.OnClickListener{
protected void onCreate(Bundle savedInstanceState){
    getSupportActionBar().hide();
    super.onCreate(savedInstanceState);
    setContentView(R.layout.personalfg_sz);
    ImageButton imageButton=(ImageButton)findViewById(R.id.return_1);
    imageButton.setOnClickListener(this);
    TextView textView= (TextView) findViewById(R.id.tv_numberSafe);
    textView.setOnClickListener(this);


}
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_1:
                finish();
                break;
            case R.id.tv_numberSafe:
                Intent intent_1 = new Intent(this,PersonalFgSzNumbersafe.class);
                startActivity(intent_1);
            default:
                break;
        }
    }
}
