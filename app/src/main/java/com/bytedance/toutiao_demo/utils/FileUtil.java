package com.bytedance.toutiao_demo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtil {
    public static String JsonToString(InputStream is){
        StringBuilder result = new StringBuilder();
        BufferedReader br=null;
        try{
            br = new BufferedReader(new InputStreamReader(is));
            String s = null;
            while((s = br.readLine())!=null){
                //result = result + "\n" +s;
                result.append("\n");
                result.append(s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(br!=null) {
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return result.toString();
    }
}
