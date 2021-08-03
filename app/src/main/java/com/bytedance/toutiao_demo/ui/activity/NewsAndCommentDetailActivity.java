package com.bytedance.toutiao_demo.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bytedance.toutiao_demo.listener.AppBarStateChangeListener;
import com.bytedance.toutiao_demo.ui.adapter.CommentItemAdapter;
import com.bytedance.toutiao_demo.data.MyData;
import com.bytedance.toutiao_demo.R;
import com.bytedance.toutiao_demo.entity.CommentItem;
import com.bytedance.toutiao_demo.entity.Image;
import com.bytedance.toutiao_demo.entity.News;
import com.bytedance.toutiao_demo.utils.DataUtil;
import com.githang.statusbar.StatusBarCompat;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NewsAndCommentDetailActivity extends AppCompatActivity {
    private List<CommentItem> commentItemList=new ArrayList<>();
    private RecyclerView recyclerView;
    private boolean isFollow=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_and_comment_detail);
        //设置系统状态栏颜色（新闻详情页的状态栏背景颜色为白色）
        //第一步：引入依赖
        //第二部：在activity中设置颜色
        StatusBarCompat.setStatusBarColor(this,0xFFFFFFFF,true);

        //展示详情页
        initNewsDetail();
        initCommentDetail();


        //关注按钮点击事件
        Button button=findViewById(R.id.follow);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFollow==false){
                    button.setBackgroundResource(R.drawable.rectangle_white);
                    button.setTextColor(getResources().getColor(android.R.color.darker_gray));
                    button.setText("已关注");
                    isFollow=true;
                }else{
                    button.setBackgroundResource(R.drawable.rectangle_red);
                    button.setTextColor(getResources().getColor(R.color.white));
                    button.setText("关注");
                    isFollow=false;
                }
            }
        });


        //返回按钮点击事件
        ImageView imageView=findViewById(R.id.iv_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        //appbarlayout滚动监听事件（滚动过程中，将用户头像、ID等信息显示在toolbar中）
        AppBarLayout appBarLayout=findViewById(R.id.test_appbarlayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                //展开状态
                if(state==State.EXPANDED){
                    ImageView toolBarImageView=findViewById(R.id.toolbar_iv_avatar);
                    TextView toolBarTextView=findViewById(R.id.toolbar_tv_author);
                    toolBarTextView.setText("");
                    ViewGroup.LayoutParams layoutParams=toolBarImageView.getLayoutParams();
                    layoutParams.width=1000;
                    toolBarImageView.setLayoutParams(layoutParams);
                    toolBarImageView.setImageResource(R.mipmap.logo);
                }
                //折叠状态
                else if(state==State.COLLAPSED){
                   //Toast.makeText(NewsAndCommentDetailActivity.this,"折叠",Toast.LENGTH_SHORT).show();
                   ImageView toolBarImageView=findViewById(R.id.toolbar_iv_avatar);
                   TextView toolBarTextView=findViewById(R.id.toolbar_tv_author);
                   //设置数据
                    String tempJson=MyData.getJson1();
                    News tempNews=new Gson().fromJson(tempJson,News.class);
                    ViewGroup.LayoutParams layoutParams=toolBarImageView.getLayoutParams();
                    layoutParams.width=100;
                    toolBarImageView.setLayoutParams(layoutParams);
                    Glide.with(NewsAndCommentDetailActivity.this)
                            .load(tempNews.getUser().getAvatar_url())
                            .transform(new CircleCrop())  //圆角
                            .into(toolBarImageView);
                    toolBarTextView.setText(tempNews.getUser().getName());

                }
                //中间状态
                else{

                }
            }
        });







    }


    //初始化评论详情
    private void initCommentDetail() {
        initCommentItemList();
        recyclerView=findViewById(R.id.comment_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new CommentItemAdapter(commentItemList));
    }


    //初始化数据，向commentItemList中填充数据
    private void initCommentItemList(){
        String json2= MyData.getJson2();
        Type type = new TypeToken<List<CommentItem>>(){}.getType();
        List<CommentItem> list = new Gson().fromJson(json2,type);
        commentItemList=list;
    }



    //初始化新闻详情
    private void initNewsDetail(){
        //1.将新闻详情json数据转为news对象
        String json1=MyData.getJson1();
        News news=new Gson().fromJson(json1,News.class);
        //获取news对象的属性值
        String name=news.getUser().getName();
        String remarkName=news.getUser().getRemark_name();
        String avatarUrl=news.getUser().getAvatar_url();
        String content=news.getContent();
        int displayCount=news.getDisplay_count();
        List<Image> imageList=news.getUgc_cut_image_list();
        //2.获取新闻详情页View
        TextView nameTextView=findViewById(R.id.tv_name);
        TextView remarkNameTextView=findViewById(R.id.remark_name);
        ImageView avatarImageView=findViewById(R.id.iv_avatar);
        TextView contentTextView=findViewById(R.id.news_content);
        TextView displayCountTextView=findViewById(R.id.news_display_count);
        LinearLayout linearLayout=findViewById(R.id.ll_image_list);
        //3.将视图与数据绑定
        nameTextView.setText(name);
        remarkNameTextView.setText(remarkName);
        contentTextView.setText(content);
        displayCountTextView.setText(DataUtil.displayCountToFormat(displayCount));
        Glide.with(this)
                .load(avatarUrl)
                .transform(new CircleCrop())  //圆角
                .into(avatarImageView);
        //动态添加网络图片
        if(imageList.size()==0){

        }else if(imageList.size()==1){

        }else if(imageList.size()==2){
            for(int i=0;i<imageList.size();i++){
                //linearLayout.removeAllViews();
                ImageView imageView=new ImageView(this);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(500,400));
                Glide.with(this)
                        .load(imageList.get(i).getUrl())
                        .into(imageView);
                linearLayout.addView(imageView);
            }
        }else if(imageList.size()==3){

        }else{

        }











    }










}