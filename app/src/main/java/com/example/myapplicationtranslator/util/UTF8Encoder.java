package com.example.myapplicationtranslator.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by liuht on 2017/3/13.
 */

public class UTF8Encoder {

    public static String encode(String url){
        if(url==null){
            return null;
        }
        try{
            url = URLEncoder.encode(url,"UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
           return url;
        }
}
