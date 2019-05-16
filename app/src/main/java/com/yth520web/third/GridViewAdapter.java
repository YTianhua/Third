package com.yth520web.third;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GridViewAdapter extends ArrayAdapter {
    //需要传入rate和detail
    int resourceId;
    List mList;
    //int mPosition;
   /* public MyAdapter(Context context, int resource, ArrayList<HashMap<String,String>> list) {//为什么?
        super(context, resource, list);
    }*/
    public GridViewAdapter(Context context, int resource,List<MyRate> list) {//为什么?
        super(context, resource, list);
        resourceId = resource;
        mList = list;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        //获得实例
        MyRate myRate = (MyRate) getItem(position);//为什么出现不兼容？
        View itemView =  LayoutInflater.from(getContext()).inflate(resourceId//要注意不能直接传入布局，要传入子项布局的id
                ,parent,false);
        TextView rate = (TextView)itemView.findViewById(R.id.m_grid_LeftText);
        TextView detail =(TextView)itemView.findViewById(R.id.m_grid_RightText);//分别在左和右显示汇率和详情
        rate.setText(myRate.getMLeftText());
        detail.setText(myRate.getMRightText());
        //别在这设置listview的点击事件
        return itemView;
    }
}
