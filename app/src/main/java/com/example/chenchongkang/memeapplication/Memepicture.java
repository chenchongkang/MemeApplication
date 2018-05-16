package com.example.chenchongkang.memeapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chenchongkang.memeapplication.api.HttpHandler;
import com.example.chenchongkang.memeapplication.model.EvaluationBean;
import com.example.chenchongkang.memeapplication.model.MemeBean;
import com.example.chenchongkang.memeapplication.model.PictureBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class Memepicture extends AppCompatActivity implements View.OnClickListener {
    private MemeBean memeBean;
    private List<PictureBean> pictureBeanslist;
    private EvaluationBean evaluation;
    private int a,userid;
    private Button memeEvaluation;
    private MyAdapter adapter;
    private ImageView memeCover;
    static final private int GET_CODE = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏ActionBar
        getSupportActionBar().hide();
        setContentView(R.layout.zx_meme);

        ImageView imageView = (ImageView) findViewById(R.id.return_3);
        imageView.setOnClickListener(this);
        Intent intent = getIntent();
        a = intent.getIntExtra("memeid", 0);
        //图片保存
        memeCover = (ImageView) findViewById(R.id.meme_cover);
        Glide.with(this).load("http://192.168.43.87:8081/meme/getmemecover/" + a)
                .placeholder(R.drawable.ic_startone)
                .into(memeCover);

        memeEvaluation = (Button)findViewById(R.id.meme_evaluation);
        memeEvaluation.setOnClickListener(this);
        pictureBeanslist = new ArrayList<>();

        new Thread() {
            public void run() {
                parseJOSNWithGSON();
            }
        }.start();

        new Thread() {
            public void run() {
                parseJOSNWithGSONpicture();

            }
        }.start();

        new Thread(){
            public void run(){
                parseJOSNWithGSONEvaluation();
            }
        }.start();

        GridView gv = (GridView) findViewById(R.id.gridView_a);
        adapter = new MyAdapter();
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PictureBean pickBean1 =pictureBeanslist.get(i);
                Intent intent = new Intent(Memepicture.this, Picturedownload.class);
                Log.e("wewe",pickBean1.getImgID()+"");
                int abc=pickBean1.getImgID();
                intent.putExtra("imgid",abc);
                startActivity(intent);

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_CODE) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(Memepicture.this,"点击了返回",Toast.LENGTH_SHORT).show();
            } else {
                if (data != null) {
                    new Thread(){
                        public void run(){
                            parseJOSNWithGSONEvaluation();
                        }
                    }.start();
                    Toast.makeText(Memepicture.this,"得到第二个activity返回的结果:\n" + data.getAction(),Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void parseJOSNWithGSON() {
        String jsondata = HttpHandler.executeHttpPost("http://192.168.43.87:8081/meme/entitymemefindid/" + a, null);
        Gson gson = new Gson();
        memeBean = gson.fromJson(jsondata, MemeBean.class);
        showToast(memeBean);
    }

    private void parseJOSNWithGSONpicture() {
        String jsondata = HttpHandler.executeHttpPost("http://192.168.43.87:8081/meme/picturememeid/" + a, null);
        Gson gson = new Gson();
        List<PictureBean> mpictureBeanslist = gson.fromJson(jsondata, new TypeToken<List<PictureBean>>() {
        }.getType());
        pictureBeanslist = mpictureBeanslist;
        showToastpicture();
    }

    private void parseJOSNWithGSONEvaluation(){
        SharedPreferences userInfor = this.getSharedPreferences("config", Context.MODE_PRIVATE);
        userid=userInfor.getInt("userid",0);
            String jsondata = HttpHandler.executeHttpPost("http://192.168.43.87:8081/meme/evaluations/" + userid+"/"+a, null);
            Gson gson = new Gson();
            evaluation = gson.fromJson(jsondata,EvaluationBean.class);
        if (evaluation!=null){
            showToastbutton();
        }
    }

    private void showToastbutton() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                memeEvaluation.setText("已评价");
            }

        });
    }

    private void showToast(final MemeBean ab) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String a = ab.getClassis();
                TextView memename = (TextView) findViewById(R.id.meme_name);
                memename.setText(ab.getMemeName());
                TextView memeclasses = (TextView) findViewById(R.id.meme_classes);
                if ("type2".equals(ab.getClassis())) {
                    String type = "暴漫";
                    memeclasses.setText(type);
                } else if ("type3".equals(ab.getClassis())) {
                    String type = "明星";
                    memeclasses.setText(type);
                }
                TextView memeintro = (TextView) findViewById(R.id.meme_intro);
                memeintro.setText(ab.getMemeIntro());
                TextView memeauthor = (TextView) findViewById(R.id.meme_author);
                memeauthor.setText(ab.getAuthor());
            }

        });
    }

    private void showToastpicture() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }

        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_3:
                finish();
                break;
            case R.id.meme_evaluation:
                if (evaluation!=null){
                    AlertDialog.Builder builder= new AlertDialog.Builder(Memepicture.this);
                    builder.setTitle("表情包推荐应用");
                    builder.setMessage("这个表情包你已经评价过了！");
                    builder.setIcon(R.mipmap.ic_laucher_memecover);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    builder.show();
                }
                if (evaluation==null){
                Intent intent = new Intent(Memepicture.this, MemeEvaluation.class);
                intent.putExtra("memeid",memeBean.getMemeID());
                startActivityForResult(intent,GET_CODE);
                }
                break;
            default:
                break;
        }

    }


    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return pictureBeanslist.size();
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
            PictureBean pictureBean = pictureBeanslist.get(position);
            View view;
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(Memepicture.this).inflate(R.layout.gridview_item, null);
                viewHolder.iv_cover = (ImageView) view.findViewById(R.id.grid_img);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            //显示数据
            int imgid = pictureBean.getImgID();
            Glide.with(Memepicture.this).load("http://192.168.43.87:8081/meme/getpictureid/" + imgid)
                    .placeholder(R.drawable.ic_startone)
                    .centerCrop().into(viewHolder.iv_cover);
            return view;
        }

        class ViewHolder {
            ImageView iv_cover;
        }
    }

}
