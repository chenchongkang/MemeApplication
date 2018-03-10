package com.example.chenchongkang.memeapplication;

/**
 * Created by chenchongkang on 2018/2/3.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenchongkang.memeapplication.Adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.tabhost;

/**
 * Created by dm on 16-3-29.
 * 第一个页面
 */
public class ClassificationFg extends Fragment implements View.OnClickListener {

    private ViewPager viewPager = null;
    private List<View> viewContainter = new ArrayList<View>();   //存放容器
    private ViewPagerAdapter viewPagerAdapter = null;   //声明适配器
    private TabHost mTabHost = null;
    private TabWidget mTabWidget = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.classificationfg, container, false);
        // 绑定组件
        viewPager = (ViewPager) view.findViewById(R.id.viewpager1);
        mTabHost = (TabHost)view.findViewById(android.R.id.tabhost);
        initMyTabHost();  //初始化TabHost
        initViewPagerContainter();  //初始viewPager
        viewPagerAdapter = new ViewPagerAdapter();
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
        mTabHost.addTab(mTabHost.newTabSpec("tab_1").setContent(R.id.tab_1).setIndicator("第一页"));
        mTabHost.addTab(mTabHost.newTabSpec("tab_2").setContent(R.id.tab_2).setIndicator("第二页"));
        mTabHost.addTab(mTabHost.newTabSpec("tab_3").setContent(R.id.tab_3).setIndicator("第三页"));
        mTabHost.addTab(mTabHost.newTabSpec("tab_4").setContent(R.id.tab_4).setIndicator("第四页"));
    }

    //初始化viewPager
    public void initViewPagerContainter(){
        //建立四个view的样式，并找到他们
        View view_1 = LayoutInflater.from(getContext()).inflate(R.layout.tab1,null);
        View view_2 = LayoutInflater.from(getContext()).inflate(R.layout.tab2,null);
        View view_3 = LayoutInflater.from(getContext()).inflate(R.layout.tab3,null);
        View view_4 = LayoutInflater.from(getContext()).inflate(R.layout.tab4,null);
        TextView textView=(TextView) view_1.findViewById(R.id.tab1_tv1);
        textView.setOnClickListener(this);
        Button button=(Button) view_1.findViewById(R.id.bt_test1);
        button.setOnClickListener(this);
        //加入ViewPage的容器
        viewContainter.add(view_1);
        viewContainter.add(view_2);
        viewContainter.add(view_3);
        viewContainter.add(view_4);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab1_tv1:
                Intent intent_1 = new Intent(getActivity(),PersonalFgSz.class);
                startActivity(intent_1);
                break;
            case R.id.bt_test1:
                Intent intent_2 = new Intent(getActivity(),PersonalFgPj.class);
                startActivity(intent_2);
                break;
            default:
                break;
        }

    }
    //内部类实现viewpager的适配器
    private class ViewPagerAdapter extends PagerAdapter{

        //该方法 决定 并 返回 viewpager中组件的数量
        @Override
        public int getCount() {
            return viewContainter.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }
        //滑动切换的时候，消除当前组件
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewContainter.get(position));
        }
        //每次滑动的时候生成的组件
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewContainter.get(position));
            return viewContainter.get(position);
        }
    }
}

//    private static int NUMBER_IMAGES=4;
//    private ViewPagerAdapter viewPagerAdapter=null;
//    private ViewPager viewPager;
//    private int[] space={R.drawable.ic_startone,R.drawable.ic_starttwo,
//                         R.drawable.ic_startthree,R.drawable.ic_startfour};
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.classificationfg, container, false);
////        initViewPagerContainter();  //初始viewPager
////        initMyTabHost();  //初始化TabHost
//        viewPagerAdapter =new ViewPagerAdapter();
//        viewPager=(ViewPager)view.findViewById(R.id.viewpager1);
//        viewPager.setAdapter(viewPagerAdapter);
//
//        // 方法2、3使用：
//        // 获取该Activity里面的TabHost组件
//        // 方法1使用：
//        // TabHost tabHost = getTabHost();
//        // 方法1使用：
//        // LayoutInflater.from(this).inflate(R.layout.main_tab, tabHost.getTabContentView(), true);
//        // 方法2、3使用
//        TabHost tabHost = (TabHost)view.findViewById(R.id.tabhost);
//        tabHost.setup();
//        // 方法3使用，动态载入xml，不需要Activity
//        LayoutInflater.from(getContext()).inflate(R.layout.tab1, tabHost.getTabContentView());
//        LayoutInflater.from(getContext()).inflate(R.layout.tab2, tabHost.getTabContentView());
//        LayoutInflater.from(getContext()).inflate(R.layout.tab3, tabHost.getTabContentView());
//        // 创建第一个Tab页
//        /*TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1")
//                .setIndicator("标签页一") // 设置标题
//                .setContent(R.id.tab01); //设置内容
//        // 添加第一个标签页
//        tabHost.addTab(tab1);
//        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2")
//                .setIndicator("标签页二")
//                .setContent(R.id.tab02);
//        // 添加第二个标签页
//        tabHost.addTab(tab2);
//        TabHost.TabSpec tab3 = tabHost.newTabSpec("tab3")
//                .setIndicator("标签页三")
//                .setContent(R.id.tab03);
//        // 添加第三个标签页
//        tabHost.addTab(tab3);*/
//
//        /* 以上创建和添加标签页也可以用如下代码实现 */
//        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("标签页一").setContent(R.id.tab01));
//        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("标签页二").setContent(R.id.tab02));
//        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("标签页三").setContent(R.id.tab03));
//
//        //标签切换事件处理，setOnTabChangedListener
//        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
//            @Override
//            // tabId是newTabSpec第一个参数设置的tab页名，并不是layout里面的标识符id
//            public void onTabChanged(String tabId) {
//                if (tabId.equals("tab1")) {   //第一个标签
//                    Toast.makeText(getActivity(), "点击标签页一", Toast.LENGTH_SHORT).show();
//                }
//                if (tabId.equals("tab2")) {   //第二个标签
//                    Toast.makeText(getActivity(), "点击标签页二", Toast.LENGTH_SHORT).show();
//                }
//                if (tabId.equals("tab3")) {   //第三个标签
//                    Toast.makeText(getActivity(), "点击标签页三", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        return view;
//
//    }
//    private class ViewPagerAdapter extends PagerAdapter {
//
//        @Override
//        public int getCount() {
//            return NUMBER_IMAGES;
//        }
//
//
//        @Override
//        public Object instantiateItem(ViewGroup imageArray, int galaxy) {
////        Fragment fragment = new ClassificationFg();
////        FragmentManager fragmentM = getSupportFragmentManager();//管理对象
////        FragmentTransaction trans = fragmentM.beginTransaction();//切换碎片
////        ImageView spaceView = new ImageView(fragment.getActivity());
//            ImageView spaceView = new ImageView(getActivity());
//            spaceView.setImageResource(space[galaxy]);
//            ((ViewPager) imageArray).addView(spaceView);
//            return spaceView;
//        }
//
////    private FragmentManager getSupportFragmentManager() {
////        return null;
////    }
//
//        @Override
//        public void destroyItem(ViewGroup imageArray, int galaxy, Object spaceView) {
//            ((ViewPager) imageArray).removeView((ImageView) spaceView);
//        }
//
//        @Override
//        public boolean isViewFromObject(View spaceView, Object galaxy) {
//            return spaceView == ((ImageView) galaxy);
//        }
//
//        @Override
//        public Parcelable saveState() {
//            return null;
//        }
//
//        @Override
//        public void restoreState(Parcelable arg0, ClassLoader arg1) {
//        }
//
//        @Override
//        public void startUpdate(ViewGroup arg0) {
//        }
//
//        @Override
//        public void finishUpdate(ViewGroup arg0){}
//    }
//
//}





