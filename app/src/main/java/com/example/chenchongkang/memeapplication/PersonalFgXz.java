package com.example.chenchongkang.memeapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chenchongkang.memeapplication.api.HttpHandler;
import com.example.chenchongkang.memeapplication.model.EvaluationBean;
import com.example.chenchongkang.memeapplication.model.MemeBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenchongkang on 2018/3/7.
 */

public class PersonalFgXz extends AppCompatActivity implements View.OnClickListener{
    private SwipeRefreshLayout swipeRefreshLayout;
    private int userid;
    private List<EvaluationBean> evaluationBeanList;
    private List<MemeBean> memeBeans;
    private MyAdapter adapter2;
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalfg_xz);
        ImageButton imageButton=(ImageButton)findViewById(R.id.return_2);
        imageButton.setOnClickListener(this);
        SharedPreferences userInfor = this.getSharedPreferences("config", Context.MODE_PRIVATE);
        userid=userInfor.getInt("userid",0);
        ListView lv = (ListView) findViewById(R.id.list_xzxxk);
        evaluationBeanList = new ArrayList<>();
        adapter2 = new MyAdapter();
        memeBeans=new ArrayList<MemeBean>();
        lv.setAdapter(adapter2);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorSchemeResources(new int[]{R.color.colorAccent, R.color.colorPrimary});
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
        new Thread() {
                    public void run() {
                        parseJOSNWithGSONmemeid();
                    }
                }.start();
            }
        });

        new Thread() {
            public void run() {

                parseJOSNWithGSON();
                parseJOSNWithGSONmemeid();
            }
        }.start();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MemeBean memeBean1 =memeBeans.get(i);
                Intent intent = new Intent(getApplicationContext(), Memepicture.class);
                Log.e("wewe",memeBean1.getClassis());
                int abc=memeBean1.getMemeID();
                intent.putExtra("memeid",abc);
                startActivity(intent);
            }
        });





    }

    private void parseJOSNWithGSON() {

        String jsondata = HttpHandler.executeHttpPost("http://192.168.43.87:8081/meme/evaluauserlist/" + userid, null);
        Gson gson = new Gson();
        evaluationBeanList = gson.fromJson(jsondata, new TypeToken<List<EvaluationBean>>() {
        }.getType());
    }

    private void parseJOSNWithGSONmemeid() {
            memeBeans.clear();
        for (EvaluationBean evaluationBeanList1:evaluationBeanList) {
            String jsondata = HttpHandler.executeHttpPost("http://192.168.43.87:8081/meme/entitymemefindid/" + evaluationBeanList1.getMemeID(), null);
            Gson gson = new Gson();
            memeBeans.add(gson.fromJson(jsondata,MemeBean.class));
    }
        showToast();
    }

    private void showToast() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                adapter2.notifyDataSetChanged();
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_2:
                finish();
                break;
            default:
                break;
        }

    }
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return memeBeans.size();
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
            MemeBean memeBean1 = memeBeans.get(position);
            View view;
            MyAdapter.ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new MyAdapter.ViewHolder();
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.selectionfg_xxk, null);
                viewHolder.iv_cover = (ImageView) view.findViewById(R.id.iv_select_cover);
                viewHolder.iv_name = (TextView) view.findViewById(R.id.tv_select_name);
                viewHolder.iv_intro = (TextView) view.findViewById(R.id.tv_select_intro);
                viewHolder.iv_type = (TextView) view.findViewById(R.id.bt_select_type);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (MyAdapter.ViewHolder) view.getTag();

            }
            //显示数据
            int memeid = memeBean1.getMemeID();

            Glide.with(getApplicationContext()).load("http://192.168.43.87:8081/meme/getmemecover/" + memeid)
                    .placeholder(R.drawable.ic_startone)
                    .centerCrop()
                    .into(viewHolder.iv_cover);
            viewHolder.iv_name.setText(memeBean1.getMemeName());
            viewHolder.iv_intro.setText(memeBean1.getMemeIntro());
            String type = memeBean1.getClassis();
            if ("type2".equals(type)) {
                viewHolder.iv_type.setText("暴漫");
            } else if ("type3".equals(type)) {
                viewHolder.iv_type.setText("明星");
            } else {
                viewHolder.iv_type.setText(type);
            }
            return view;
        }

        class ViewHolder {
            ImageView iv_cover;
            TextView iv_name;
            TextView iv_intro;
            TextView iv_type;
        }
    }
}
