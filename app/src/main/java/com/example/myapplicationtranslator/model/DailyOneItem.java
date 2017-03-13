package com.example.myapplicationtranslator.model;

/**
 * Created by liuht on 2017/3/13.
 */

public class DailyOneItem {

    //英文内容
    private String content = null;
    //中文内容
    private String note = null;
    //大图地址
    private String imgUrl = null;
    //每一句的id
    private int id;

    public String getContent(){
        return content;
    }

    public String getNote(){
        return note;
    }

    public String getImgUrl(){
        return imgUrl;
    }

    public int getId(){
        return id;
    }
}
