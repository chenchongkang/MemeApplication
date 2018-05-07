package com.example.chenchongkang.memeapplication;

/**
 * Created by chenchongkang on 2018/2/3.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dm on 16-3-29.
 * 第一个页面
 */
public class PersonalFg extends Fragment implements OnClickListener{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.personalfg,container,false);
        final CircleImageView titleLeftImv=(CircleImageView) view.findViewById(R.id.avatar);
        titleLeftImv.setOnClickListener(this);
        RelativeLayout relativeLayout=(RelativeLayout) view.findViewById(R.id.rl_sz);
        relativeLayout.setOnClickListener(this);
        RelativeLayout relativeLayout_1=(RelativeLayout)view.findViewById(R.id.rl_xz);
        relativeLayout_1.setOnClickListener(this);
        RelativeLayout relativeLayout_2=(RelativeLayout)view.findViewById(R.id.rl_sc);
        relativeLayout_2.setOnClickListener(this);
        RelativeLayout relativeLayout_3=(RelativeLayout)view.findViewById(R.id.rl_pj);
        relativeLayout_3.setOnClickListener(this);
        RelativeLayout relativeLayout_4=(RelativeLayout)view.findViewById(R.id.rl_gy);
        relativeLayout_4.setOnClickListener(this);
        SharedPreferences userInfor = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        int userid = userInfor.getInt("userid", 0);

        Glide.with(this).load("http://192.168.43.87:8081/meme/getuseravatar/" + userid).placeholder(R.drawable.ic_startone)
//                .into(titleLeftImv);
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable> glideAnimation) {
                        titleLeftImv.setImageDrawable(resource);
                    }
                });

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_sz:
//                Intent intent_1 = new Intent();
//                intent_1.setClass(getActivity(), PersonalFgSz.class);
//                intent_1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //注意本行的FLAG设置
//                startActivity(intent_1);
                Intent intent_1 = new Intent(getActivity(),PersonalFgSz.class);
                startActivity(intent_1);
                break;
            case R.id.rl_xz:
                Intent intent_2 = new Intent(getActivity(),PersonalFgXz.class);
                startActivity(intent_2);
                break;
            case R.id.rl_sc:
                Intent intent_3 = new Intent(getActivity(),PersonalFgSc.class);
                startActivity(intent_3);
                break;
            case R.id.rl_pj:
                Intent intent_4 = new Intent(getActivity(),PersonalFgPj.class);
                startActivity(intent_4);
                break;
            case R.id.rl_gy:
                Intent intent_5 = new Intent(getActivity(),PersonalFgGy.class);
                startActivity(intent_5);
                break;
            case R.id.avatar:
                Intent intent_6 = new Intent(getActivity(),UserinfoActivity.class);
                startActivity(intent_6);
                break;
            default:
                break;
        }
    }
}