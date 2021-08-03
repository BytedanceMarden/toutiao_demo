package com.bytedance.toutiao_demo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bytedance.toutiao_demo.ui.adapter.NewsAdapter;
import com.bytedance.toutiao_demo.R;
import com.bytedance.toutiao_demo.entity.News;
import com.bytedance.toutiao_demo.utils.FileUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    //成员变量
    private RecyclerView recyclerView;
    private List<News> newsList=new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.news_fragment_layout,container,false);
        initNews();
        recyclerView=view.findViewById(R.id.news_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new NewsAdapter(newsList));
        return view;

    }



    private void initNews() {
        Gson gson1=new Gson();
//        News news=gson1.fromJson(json1,News.class);
//        for(int i=0;i<10;i++){
//            newsList.add(news);
//        }


        String json3=FileUtil.JsonToString(this.getResources().openRawResource(R.raw.feed));
        JsonElement jsonElement=gson1.fromJson(json3, JsonElement.class);
        JsonArray jsonArray =jsonElement.getAsJsonObject().getAsJsonArray("data");
        List<News> tempList=new ArrayList<>();
        for(int i=1;i<jsonArray.size();i++){
            //去除转义
            News news=gson1.fromJson(jsonArray.get(i).getAsJsonObject().getAsJsonPrimitive("content").getAsString(),News.class);
            tempList.add(news);
        }
        newsList=tempList;











    }




}
