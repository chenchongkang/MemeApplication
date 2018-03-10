package com.example.chenchongkang.memeapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by chenchongkang on 2018/3/10.
 */

public class ClassificationFgTabTwo extends Fragment implements View.OnClickListener {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2, container, false);
//        TextView textView = (TextView) view.findViewById(R.id.tab2_tv1);
//        TextView textView1 = (TextView) view.findViewById(R.id.tab2_tv2);
//        Button button = (Button) view.findViewById(R.id.bt_test1);
//        textView.setOnClickListener(this);
//        textView1.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.tab2_tv1:
//                Intent intent_1 = new Intent(getActivity(), PersonalFgSz.class);
//                startActivity(intent_1);
//                break;
//            case R.id.tab2_tv2:
//                Intent intent_3 = new Intent(getActivity(), PersonalFgPj.class);
//                startActivity(intent_3);
//                break;
//            default:
//                break;
//        }
    }
}
