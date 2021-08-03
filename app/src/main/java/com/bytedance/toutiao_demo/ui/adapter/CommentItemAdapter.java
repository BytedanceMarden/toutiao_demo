package com.bytedance.toutiao_demo.ui.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bytedance.toutiao_demo.R;
import com.bytedance.toutiao_demo.entity.CommentItem;
import com.bytedance.toutiao_demo.utils.DateUtil;

import java.util.List;

public class CommentItemAdapter extends RecyclerView.Adapter<CommentItemAdapter.CommentItemViewHolder>{
    private List<CommentItem> commentItemList;
    private boolean isDigg=false;


    public CommentItemAdapter(List<CommentItem> list) {
        this.commentItemList = list;
    }


    @NonNull
    @Override
    public CommentItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        CommentItemViewHolder commentItemViewHolder=new CommentItemViewHolder(view);

        //设置RecyclerView Item内子控件点击事件
        commentItemViewHolder.diggTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDigg==false){
                    TextView textView=view.findViewById(R.id.tv_like_count);
                    //在textView中设置drawableLeft
                    Drawable drawable=view.getResources().getDrawable(R.mipmap.icon_article_comment_digg_press);
                    drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                    textView.setCompoundDrawables(drawable,null,null,null);
                    String temp=textView.getText().toString().trim();
                    if(temp.equals("赞")){
                        textView.setText("1");
                    }else{
                        int newCount=Integer.parseInt(textView.getText().toString().trim())+1;
                        textView.setText(newCount+"");
                    }
                    isDigg=true;
                }else{
                    TextView textView=view.findViewById(R.id.tv_like_count);
                    Drawable drawable=view.getResources().getDrawable(R.mipmap.icon_article_comment_digg);
                    drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                    textView.setCompoundDrawables(drawable,null,null,null);
                    String temp=textView.getText().toString().trim();
                    if(temp.equals("1")){
                        textView.setText("赞");
                    }else{
                        int newCount=Integer.parseInt(textView.getText().toString().trim())-1;
                        textView.setText(newCount+"");
                    }
                    isDigg=false;
                }
            }
        });
        return commentItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentItemViewHolder holder, int position) {
        CommentItem commentItem = commentItemList.get(position);
        holder.bindData(commentItem);
    }

    @Override
    public int getItemCount() {
        return commentItemList.size();
    }




     class  CommentItemViewHolder extends RecyclerView.ViewHolder {
         public  TextView nameTextView;
         public TextView contentTextView;
         public TextView diggTextView;
         public TextView timeStampTextView;
         public ImageView authorImageView;

        public CommentItemViewHolder(View itemView) {
            super(itemView);
            nameTextView=itemView.findViewById(R.id.tv_name);
            contentTextView=itemView.findViewById(R.id.tv_content);
            diggTextView=itemView.findViewById(R.id.tv_like_count);
            timeStampTextView=itemView.findViewById(R.id.tv_time);
            authorImageView=itemView.findViewById(R.id.iv_avatar);
        }


        public void bindData(CommentItem commentItem) {
            String name=commentItem.getComment().getUser_name();
            String content=commentItem.getComment().getText();
            int digg=commentItem.getComment().getDigg_count();
            String timeStamp=commentItem.getComment().getCreate_time();
            String imageUrl=commentItem.getComment().getUser_profile_image_url();

            nameTextView.setText(name);
            contentTextView.setText(content);
            if(digg==0){
                diggTextView.setText(" 赞");
            }else{
                diggTextView.setText(" "+digg);
            }


            timeStampTextView.setText(DateUtil.getTimeDown(DateUtil.TimeStampToFormatDate(timeStamp)));
            Glide.with(itemView)
                    .load(imageUrl)
                    .transform(new CircleCrop())  //圆角
                    .into(authorImageView);
        }
    }











    }






