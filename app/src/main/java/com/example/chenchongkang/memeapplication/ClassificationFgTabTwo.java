package com.example.chenchongkang.memeapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chenchongkang.memeapplication.api.HttpHandler;
import com.example.chenchongkang.memeapplication.model.MemeBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenchongkang on 2018/3/10.
 */

public class ClassificationFgTabTwo extends Fragment implements View.OnClickListener {
    private List<MemeBean> memeBeanList;
    private MyAdapter adapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2, container, false);
        //listview的适配器,liatview显示推荐表情包
        ListView lv=(ListView)view.findViewById(R.id.lv_hottest);
        memeBeanList = new ArrayList<>();
        adapter = new MyAdapter();
        lv.setAdapter(adapter);
        new Thread() {
            public void run() {
                parseJOSNWithGSON();
            }
        }.start();
        return view;
    }
    private void parseJOSNWithGSON(){
        String jsondata = HttpHandler.executeHttpPost("http://192.168.43.87:8081/meme/entityhottestmemelist", null);
        Gson gson = new Gson();
        memeBeanList = gson.fromJson(jsondata,new TypeToken<List<MemeBean>>(){}.getType());
        showToast();
    }
    private void showToast() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
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
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return memeBeanList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MemeBean memeBean = memeBeanList.get(position);
            View view;
            MyAdapter.ViewHolder viewHolder;
            if (convertView==null){
                viewHolder = new MyAdapter.ViewHolder();
                view=LayoutInflater.from(getContext()).inflate(R.layout.selectionfg_xxk,null);
                viewHolder.iv_cover  = (ImageView) view.findViewById(R.id.iv_select_cover);
                viewHolder.iv_name   = (TextView) view.findViewById(R.id.tv_select_name);
                viewHolder.iv_intro  = (TextView) view.findViewById(R.id.tv_select_intro);
                viewHolder.iv_type   = (TextView) view.findViewById(R.id.bt_select_type);
                view.setTag(viewHolder);
            }else{
                view=convertView;
                viewHolder = (MyAdapter.ViewHolder) view.getTag();

            }
            //显示数据
            int memeid=memeBean.getMemeID();

            Glide.with(getContext())
                    .load("http://192.168.43.87:8081/meme/getmemecover/" +memeid)
                    .placeholder(R.drawable.ic_startone)
                    .centerCrop()
                    .into(viewHolder.iv_cover);
            viewHolder.iv_name.setText(memeBean.getMemeName());
            viewHolder.iv_intro.setText(memeBean.getMemeIntro());
            String type = memeBean.getClassis();
            if("type2".equals(type)){
                viewHolder.iv_type.setText("暴漫");
            }
            else if("type3".equals(type))
            {
                viewHolder.iv_type.setText("明星");
            }
            else {
                viewHolder.iv_type.setText(type);
            }
            return view;
        }

        class ViewHolder{
            ImageView iv_cover;
            TextView iv_name;
            TextView iv_intro;
            TextView iv_type;
        }
    }

}
