package com.example.chenchongkang.memeapplication;

/**
 * Created by chenchongkang on 2018/2/3.
 */


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chenchongkang.memeapplication.api.HttpHandler;
import com.example.chenchongkang.memeapplication.model.MemeBean;
import com.example.chenchongkang.memeapplication.model.UserBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class SelectionFg extends Fragment {

    private View rootView;
    private TextView title;
    private ViewPager mViewPaper;
    //图片列表
    private List<ImageView> images;
    //图片轮播小点列表
    private List<View> dots;
    //表情包列表
    private List<MemeBean> memeBeanList;
    private ViewPagerAdapter adapter;
    private MyAdepter adapter1;
    private int currentItem;
    //记录上一次点的位置
    private int oldPosition = 0;
    //存放图片的id
    private int[] imageIds = new int[]{R.drawable.timg1, R.drawable.timg2, R.drawable.timg3, R.drawable.timg4, R.drawable.timg5};
    //存放图片的标题
    private String[] titles = new String[]{"大脸系列表情包", "装逼系列表情包", "打钱系列表情包", "心累系列表情包","智商系列表情包"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getActivity()).inflate(R.layout.selectionfg, container, false);
        mViewPaper = (ViewPager) rootView.findViewById(R.id.vp);

        //listview的适配器,liatview显示精选表情包
        ListView lv=(ListView)rootView.findViewById(R.id.list_xxk);
        memeBeanList = new ArrayList<>();
        adapter1 = new MyAdepter();
        lv.setAdapter(adapter1);

        final SwipeRefreshLayout swipeRefreshLayout= (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorSchemeResources(new int[]{R.color.colorAccent, R.color.colorPrimary});
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                parseJOSNWithGSON();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        new Thread() {
            public void run() {
                parseJOSNWithGSON();
            }
        }.start();

        //显示的图片
        images = new ArrayList<>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }

        //显示的小点
        dots = new ArrayList<>();
        dots.add(rootView.findViewById(R.id.dot_0));
        dots.add(rootView.findViewById(R.id.dot_1));
        dots.add(rootView.findViewById(R.id.dot_2));
        dots.add(rootView.findViewById(R.id.dot_3));
        dots.add(rootView.findViewById(R.id.dot_4));

        title = (TextView) rootView.findViewById(R.id.title);
        title.setText(titles[0]);
        adapter = new ViewPagerAdapter();
        mViewPaper.setAdapter(adapter);
        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                title.setText(titles[position]);
                dots.get(position).setBackgroundResource(R.drawable.ic_picturefour);
                dots.get(oldPosition).setBackgroundResource(R.drawable.ic_picturethree);
                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

        return rootView;
    }
//  图片适配器
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            view.addView(images.get(position));
            return images.get(position);
        }

    }


    private void parseJOSNWithGSON(){
        String jsondata = HttpHandler.executeHttpPost("http://192.168.43.87:8081/meme/entitymemelist", null);
        Gson gson = new Gson();
        memeBeanList = gson.fromJson(jsondata,new TypeToken<List<MemeBean>>(){}.getType());
        showToast();
    }


    private class MyAdepter extends BaseAdapter{

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
            ViewHolder viewHolder;
            if (convertView==null){
                viewHolder = new ViewHolder();
                view=LayoutInflater.from(getContext()).inflate(R.layout.selectionfg_xxk,null);
                viewHolder.iv_cover  = (ImageView) view.findViewById(R.id.iv_select_cover);
                viewHolder.iv_name   = (TextView) view.findViewById(R.id.tv_select_name);
                viewHolder.iv_intro  = (TextView) view.findViewById(R.id.tv_select_intro);
                viewHolder.iv_type   = (TextView) view.findViewById(R.id.bt_select_type);
                view.setTag(viewHolder);
            }else{
                view=convertView;
                viewHolder = (ViewHolder) view.getTag();

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


    private void showToast() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter1.notifyDataSetChanged();
            }
        });
    }
}