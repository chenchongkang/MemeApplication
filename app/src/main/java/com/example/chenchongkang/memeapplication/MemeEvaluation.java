package com.example.chenchongkang.memeapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenchongkang.memeapplication.api.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.PriorityQueue;

/**
 * Created by chenchongkang on 2018/5/6.
 */

public class MemeEvaluation extends AppCompatActivity implements View.OnClickListener {
    private double evaluation;
    private int userid,memeid;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏ActionBar
        getSupportActionBar().hide();
        setContentView(R.layout.personalfg_pj);
        Intent intent=getIntent();
        memeid=intent.getIntExtra("memeid",0);
        String b=String.valueOf(memeid);
        Toast.makeText(MemeEvaluation.this,b,Toast.LENGTH_SHORT).show();
        ImageView returnEvaluation=(ImageView)findViewById(R.id.return_Evaluation);
        returnEvaluation.setOnClickListener(this);
        TextView submitEvaluation =(TextView)findViewById(R.id.submit_Evaluation);
        submitEvaluation.setOnClickListener(this);
        final RatingBar ratingBar_Small = (RatingBar)findViewById(R.id.star);

        ratingBar_Small.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                ratingBar_Small.setRating(rating);
                evaluation =rating;
            }});
        SharedPreferences userInfor = this.getSharedPreferences("config", Context.MODE_PRIVATE);
        userid=userInfor.getInt("userid",0);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.return_Evaluation:
                finish();
                break;
            case R.id.submit_Evaluation:
                new Thread() {
                    public void run() {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("memeID", memeid);
                            jsonObject.put("userID", userid);
                            jsonObject.put("evaluations", evaluation);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String s = HttpHandler.executeHttpPost("http://192.168.43.87:8081/meme/addevaluation", jsonObject.toString());
                        if ("success".equals(s)) {


                            setResult(RESULT_OK,(new Intent()).setAction(String.valueOf(memeid)));
                            finish();
//                            startActivity(intent);
                        } else {
                            showToast(s);
                        }
                    }
                }.start();

                break;

                default:
                    break;
        }

    }
    private void showToast(final String text){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MemeEvaluation.this,text,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
