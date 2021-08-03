package com.bytedance.toutiao_demo.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

import com.bytedance.toutiao_demo.ui.fragment.NewsFragment;
import com.bytedance.toutiao_demo.R;
import com.githang.statusbar.StatusBarCompat;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //展示主页feed流
    private String [] tab={"关注","推荐","财经","游戏","地方","奥运","疫情","体育","微头条","视频","动漫"};
    private List<NewsFragment> newsFragmentList=new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //展示feed流页面
        TabLayout tabLayout=findViewById(R.id.main_tablayout);
        ViewPager viewPager=findViewById(R.id.main_viewpager);
        for(int i=0;i<tab.length;i++){
            tabLayout.addTab(tabLayout.newTab().setText(tab[i]));
            newsFragmentList.add(new NewsFragment());
        }

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @Override
            public Fragment getItem(int position) {
                return newsFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return newsFragmentList.size();
            }


            @Override
            public CharSequence getPageTitle(int position) {
                return tab[position];
            }
        });

        tabLayout.setupWithViewPager(viewPager,false);



        //设置tablayout的监听效果（选中、未选中、默认）
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView textView = new TextView(viewPager.getContext());
                float selectedSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 16, getResources().getDisplayMetrics());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,selectedSize);
                textView.setTextColor(getResources().getColor(R.color.color_follow));
                textView.setText(tab.getText());
                tab.setCustomView(textView);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setCustomView(null);
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        //设置默认选中状态
        TextView textView = new TextView(viewPager.getContext());
        float selectedSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 16, getResources().getDisplayMetrics());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,selectedSize);
        textView.setTextColor(getResources().getColor(R.color.color_follow));
        textView.setText(tabLayout.getTabAt(0).getText());
        tabLayout.getTabAt(0).setCustomView(textView);

    }






}