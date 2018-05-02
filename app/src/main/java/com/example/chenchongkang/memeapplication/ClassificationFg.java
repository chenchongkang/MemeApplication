package com.example.chenchongkang.memeapplication;

/**
 * Created by chenchongkang on 2018/2/3.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dm on 16-3-29.
 * 第一个页面
 */
public class ClassificationFg extends Fragment implements View.OnClickListener {

    private ViewPager viewPager = null;
    private List<Fragment> viewContainter = new ArrayList<Fragment>();   //存放容器
    private ViewPagerAdapter viewPagerAdapter = null;   //声明适配器
    private TabHost mTabHost = null;
    private TabWidget mTabWidget = null;
    private  ClassificationFgTabOne fgtab1;
    private  ClassificationFgTabTwo fgtab2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.classificationfg, container, false);
        // 绑定组件
        viewPager = (ViewPager) view.findViewById(R.id.viewpager1);
        mTabHost = (TabHost)view.findViewById(android.R.id.tabhost);
        initMyTabHost();  //初始化TabHost
        initViewPagerContainter();  //初始viewPager
        viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        //设置adapter的适配器
        viewPager.setAdapter(viewPagerAdapter);
        //设置viewPager的监听器
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            //当 滑动 切换时
            @Override
            public void onPageSelected(int position) {
                mTabWidget.setCurrentTab(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //TabHost的监听事件
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId.equals("tab_1")){
                    viewPager.setCurrentItem(0);
                }else if (tabId.equals("tab_2")){
                    viewPager.setCurrentItem(1);
                }else if (tabId.equals("tab_3")){
                    viewPager.setCurrentItem(2);
                }else{
                    viewPager.setCurrentItem(3);
                }
            }
        });

//        解决开始时不显示viewPager
        mTabHost.setCurrentTab(1);
        mTabHost.setCurrentTab(0);
        return view;
    }

    //初始化TabHost
    public void initMyTabHost(){
        //绑定id
        mTabHost.setup();
        mTabWidget = mTabHost.getTabWidget();
        /**
         * newTabSpec（）   就是给每个Tab设置一个ID
         * setIndicator()   每个Tab的标题
         * setCount()       每个Tab的标签页布局
         */
        mTabHost.addTab(mTabHost.newTabSpec("tab_1").setContent(R.id.tab_1).setIndicator("最新"));
        mTabHost.addTab(mTabHost.newTabSpec("tab_2").setContent(R.id.tab_2).setIndicator("最热"));
        mTabHost.addTab(mTabHost.newTabSpec("tab_3").setContent(R.id.tab_3).setIndicator("暴漫"));
        mTabHost.addTab(mTabHost.newTabSpec("tab_4").setContent(R.id.tab_4).setIndicator("明星"));
    }

    //初始化viewPager
    public void initViewPagerContainter(){
        //建立四个view的样式，并找到他们
//        View view_1 = LayoutInflater.from(getContext()).inflate(R.layout.tab1,null);
//        View view_2 = LayoutInflater.from(getContext()).inflate(R.layout.tab2,null);
//        View view_3 = LayoutInflater.from(getContext()).inflate(R.layout.tab3,null);
//        View view_4 = LayoutInflater.from(getContext()).inflate(R.layout.tab4,null);
//        TextView textView=(TextView) view_1.findViewById(R.id.tab1_tv1);
//        textView.setOnClickListener(this);
//        Button button=(Button) view_1.findViewById(R.id.bt_test1);
//        button.setOnClickListener(this);
        //加入ViewPage的容器
        ClassificationFgTabOne classificationFgTabOne = new ClassificationFgTabOne();
        ClassificationFgTabTwo classificationFgTabTwo = new ClassificationFgTabTwo();
        ClassificationFgTabThree classificationFgTabThree= new ClassificationFgTabThree();
        ClassificationFgTabFour classificationFgTabFour =new ClassificationFgTabFour();
        viewContainter.add(classificationFgTabOne);
        viewContainter.add(classificationFgTabTwo);
        viewContainter.add(classificationFgTabThree);
        viewContainter.add(classificationFgTabFour);
    }

    @Override
    public void onClick(View v) {
    }
    //内部类实现viewpager的适配器
    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return viewContainter.get(position);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}





