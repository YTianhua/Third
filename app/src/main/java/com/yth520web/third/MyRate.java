package com.yth520web.third;

import android.widget.TextView;

public class MyRate {
    //需要输入两个text
    String mLeftText;
    String mRightText;
    public MyRate(String mLeftText,String mRightText){
        this.mLeftText = mLeftText;
        this.mRightText = mRightText;
    }
    public String getMLeftText(){
        return mLeftText;
    }
    public String getMRightText(){
        return mRightText;
    }
    public void setMLeftText(String mLeftText){
        this.mLeftText = mLeftText;
    }
    public void setmRightText(String mRightText){
        this.mRightText = mRightText;
    }
}
