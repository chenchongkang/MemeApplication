package com.example.chenchongkang.memeapplication.Adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.chenchongkang.memeapplication.ClassificationFg;

/**
 * Created by chenchongkang on 2018/3/9.
 */

public class ViewPagerAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}


//    @Override
//    public int getCount() {
//        return NUMBER_IMAGES;
//    }
//
//
//    @Override
//    public Object instantiateItem(ViewGroup imageArray, int galaxy) {
//        Fragment fragment = new ClassificationFg();
////        FragmentManager fragmentM = getSupportFragmentManager();//管理对象
////        FragmentTransaction trans = fragmentM.beginTransaction();//切换碎片
//        ImageView spaceView = new ImageView(fragment.getActivity());
//        spaceView.setImageResource(space[galaxy]);
//        ((ViewPager) imageArray).addView(spaceView);
//        return spaceView;
//    }
//
////    private FragmentManager getSupportFragmentManager() {
////        return null;
////    }
//
//    @Override
//    public void destroyItem(ViewGroup imageArray, int galaxy, Object spaceView) {
//        ((ViewPager) imageArray).removeView((ImageView) spaceView);
//    }
//
//    @Override
//    public boolean isViewFromObject(View spaceView, Object galaxy) {
//        return spaceView == ((ImageView) galaxy);
//    }
//
//    @Override
//    public Parcelable saveState() {
//        return null;
//    }
//
//    @Override
//    public void restoreState(Parcelable arg0, ClassLoader arg1) {
//    }
//
//    @Override
//    public void startUpdate(ViewGroup arg0) {
//    }
//
//    @Override
//    public void finishUpdate(ViewGroup arg0){}
//}
