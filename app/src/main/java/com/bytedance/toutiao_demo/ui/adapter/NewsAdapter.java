package com.bytedance.toutiao_demo.ui.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.target.ViewTarget;
import com.bytedance.toutiao_demo.R;
import com.bytedance.toutiao_demo.entity.Image;
import com.bytedance.toutiao_demo.entity.News;
import com.bytedance.toutiao_demo.ui.activity.NewsAndCommentDetailActivity;
import com.bytedance.toutiao_demo.utils.BitMapUtil;
import com.bytedance.toutiao_demo.utils.DateUtil;

import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{
    private List<News> newsList;
    private View view;

    public NewsAdapter(List<News> list) {
        this.newsList = list;
    }




    @NonNull
    @NotNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NewsViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.bindData(news);
        //为每个item添加点击事件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过显式意图，跳转到新闻与评论详情页
                Intent intent=new Intent(view.getContext(), NewsAndCommentDetailActivity.class);
                view.getContext().startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }










    //ViewHolder内部类
     class NewsViewHolder extends RecyclerView.ViewHolder {
        public ImageView newsItemAvatarImageView;
        public TextView newsItemNameTextView;
        public TextView newsItemRemarkNameTextView;
        public TextView newsItemContentTextView;
        public GridLayout newsItemLinearLayout;

        public TextView newsItemCommentCount;
        public TextView newsItemDiggCount;






        public NewsViewHolder(View itemView) {
            super(itemView);
            newsItemNameTextView=itemView.findViewById(R.id.news_item_name);
            newsItemRemarkNameTextView=itemView.findViewById(R.id.news_item_remark_name);
            newsItemContentTextView=itemView.findViewById(R.id.news_item_content);
            newsItemAvatarImageView=itemView.findViewById(R.id.news_item_avatar);
            newsItemLinearLayout=itemView.findViewById(R.id.news_item_image_list);
            //item中评论数
            newsItemCommentCount=itemView.findViewById(R.id.feed_comment_count);
            //item中点赞数
            newsItemDiggCount=itemView.findViewById(R.id.feed_digg_count);




        }





        public void bindData(News news) {
            newsItemNameTextView.setText(news.getUser().getName());
            String time= DateUtil.getTimeDown(DateUtil.TimeStampToFormatDate(news.getPublish_time()));
            if(news.getUser().getRemark_name()==null){
                newsItemRemarkNameTextView.setText(time);
            }else{
                newsItemRemarkNameTextView.setText(time+"·"+news.getUser().getRemark_name());
            }


            newsItemContentTextView.setText(news.getContent());
            Glide.with(itemView)
                    .load(news.getUser().getAvatar_url())
                    .transform(new CircleCrop())  //圆角
                    .into(newsItemAvatarImageView);



            //动态添加网络图片
            GridLayout gridLayout=itemView.findViewById(R.id.news_item_image_list);
            //屏幕宽度和高度
            Activity activity= (Activity) itemView.getContext();
            Display display=activity.getWindow().getWindowManager().getDefaultDisplay();
            DisplayMetrics dm=new DisplayMetrics();
            display.getMetrics(dm);
            int mWidth=dm.widthPixels;
            int mHeight=dm.heightPixels;

            //ugc裁剪图
            List<Image> imageList=news.getUgc_cut_image_list();
            //原图
            List<Image> originImageList=news.getOrigin_image_list();

            if(imageList.size()<=9){
                if(imageList.size()==0){



                }else if(imageList.size()==1){
                    //1行1列
                    gridLayout.removeAllViews();
                    gridLayout.setRowCount(1);
                    gridLayout.setColumnCount(1);
                    int width=mWidth-50;
                    int height=(mWidth-50)/3;
                    ImageView imageView=new ImageView(itemView.getContext());
                    LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(width,height);
                    imageView.setLayoutParams(layoutParams);

                    Glide.with(itemView)
                            .load(imageList.get(0).getUrl())
                            .centerCrop()
                            .into(imageView);
                    newsItemLinearLayout.addView(imageView);
                }else{
                    //n行3列
                    gridLayout.removeAllViews();
                    gridLayout.setRowCount((imageList.size()-1)/3+1);
                    gridLayout.setColumnCount(3);
                    int width=(mWidth-50)/3;
                    int height=(mWidth-50)/3;
                    for(int i=0;i<imageList.size();i++){
                        ImageView imageView=new ImageView(itemView.getContext());
                        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(width,height);
                        layoutParams.rightMargin=3;
                        layoutParams.topMargin=3;
                        imageView.setLayoutParams(layoutParams);
                        Glide.with(itemView)
                                .load(imageList.get(i).getUrl())
                                .centerCrop()
                                .into(imageView);
                        newsItemLinearLayout.addView(imageView);
                        //点击放大图片
                        final String url=originImageList.get(i).getUrl();
                        imageView.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                Bitmap bitmap= BitMapUtil.returnBitMap(url);
                                bigImageLoader(bitmap);
                            }
                        });



                    }
                }
            }

            if(news.getComment_count()==0){
                newsItemCommentCount.setText("评论");
            }else{
                newsItemCommentCount.setText(""+news.getComment_count());
            }

            if(news.getDigg_count()==0){
                newsItemDiggCount.setText("赞");
            }else{
                newsItemDiggCount.setText(""+news.getDigg_count());
            }



        }



        //图片查看器，传入bitmap
        public void bigImageLoader(Bitmap bitmap){
            final Dialog dialog = new Dialog(itemView.getContext());
            ImageView image = new ImageView(itemView.getContext());
            image.setImageBitmap(bitmap);
            dialog.setContentView(image);

            //将dialog充满屏幕
//            WindowManager wm = (WindowManager) itemView.getContext().getSystemService(itemView.getContext().WINDOW_SERVICE);
//            DisplayMetrics dm = new DisplayMetrics();
//            wm.getDefaultDisplay().getMetrics(dm);
//            int width = dm.widthPixels;// 屏幕宽度（像素）
//            int height= dm.heightPixels; // 屏幕高度（像素）
//            float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
//            int densityDpi = dm.densityDpi;//屏幕密度dpi（120 / 160 / 240）
//            WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
//            //这个地方可以用ViewGroup.LayoutParams.MATCH_PARENT属性，各位试试看看有没有效果
//            layoutParams.width = width;
//            layoutParams.height = height;
//            dialog.getWindow().setAttributes(layoutParams);


            //将dialog周围的白块设置为透明
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            //显示
            dialog.show();
            //点击图片取消
            image.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    dialog.cancel();
                }
            });
        }






    }


}
