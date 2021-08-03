package com.bytedance.toutiao_demo.utils;

public class DataUtil {


    public static String displayCountToFormat(int displayCount){
        if(displayCount>=0){
            if(displayCount<10000){
                return displayCount+"阅读";
            }else{
                return displayCount/10000+"万阅读";
            }
        }
        return null;
    }
}
