package com.example.chenchongkang.memeapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.chenchongkang.memeapplication.api.HttpHandler;
import com.example.chenchongkang.memeapplication.model.MemeBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenchongkang on 2018/2/3.
 */

public class RecommendationFg extends Fragment{
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<MemeBean> memeBeanList;
    private MyAdapter adapter2;
    private int userid;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.recommendationfg,container,false);

        SharedPreferences userInfor = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        String username=userInfor.getString("username","");
        Toast.makeText(getActivity(),username+"de推荐", Toast.LENGTH_SHORT).show();
        userid=userInfor.getInt("userid",0);

        //listview的适配器,liatview显示推荐表情包
        ListView lv=(ListView)view.findViewById(R.id.listView_recommend);
        memeBeanList = new ArrayList<>();
        adapter2 = new MyAdapter();
        lv.setAdapter(adapter2);

        swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorSchemeResources(new int[]{R.color.colorAccent, R.color.colorPrimary});
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread() {
                    public void run() {
                        parseJOSNWithGSON();
                    }
                }.start();
            }
        });



        new Thread() {
            public void run() {
                parseJOSNWithGSON();
            }
        }.start();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MemeBean memeBean1 =memeBeanList.get(i);
                Intent intent = new Intent(getActivity(), Memepicture.class);
                Log.e("wewe",memeBean1.getClassis());
                int abc=memeBean1.getMemeID();
                intent.putExtra("memeid",abc);
                startActivity(intent);
            }
        });


        return view;
    }

    private void parseJOSNWithGSON(){
        String jsondata = HttpHandler.executeHttpPost("http://192.168.43.87:8081/meme/recommendlists/"+userid, null);
        Gson gson = new Gson();
        memeBeanList = gson.fromJson(jsondata,new TypeToken<List<MemeBean>>(){}.getType());
        showToast();
    }


    private void showToast() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                adapter2.notifyDataSetChanged();
            }
        });
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if(memeBeanList==null){
                return 0;
            }
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

