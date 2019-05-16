package com.yth520web.third;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GridViewClass extends AppCompatActivity {
    //GridView只是个高级的ListView
    List<MyRate> mListView;
    MyAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_glidview);
        //GridView只是个高级的ListView
        //用来展示GlidView
        initListView();//添加数据的数组
        //initAdapter();//调试适配器的方法
        adapter = new MyAdapter(this,R.layout.item_layout,mListView);
        final ListView listView = (ListView)findViewById(R.id.mListView);
        listView.setAdapter(adapter);
    }

    public void initListView(){
        mListView = new ArrayList<>();

          /*for (int i=0;i<2;i++){
              MyRate myRate = new MyRate("rate"+i,"detail"+i);
              mListView.add(myRate);
          }*/
        //开启子线程，使用jsoup从网络获取数据
        new Thread(new Runnable() {
            Document doc;
            String url = "http://www.usd-cny.com/abc.htm";//别忘添加联网的permission
            @Override
            public void run() {
                try{
                    doc = Jsoup.connect(url).get();
                    String doc_title = doc.title();
                    Log.i("获取尝试","标题："+doc_title);
                    //遍历tr，找到需要的数据
                    Elements trs = doc.select("div.pp tr td");
                    int x=5;
                    int y=6;//x,y分别获得名称和汇率
                    for(;x<=80;){
                        //最低是81
                        String  rate_name =trs.get(x).text();
                        String rate_detail =trs.get(y).text();
                        // Log.i("获得名称","名称"+x+":"+rate_name);
                        //Log.i("获得具体","具体"+y+":"+rate_detail);
                        //Log.i("获得汇率","名称:"+rate_detail);
                        MyRate myRate = new MyRate(rate_name,rate_detail);
                        mListView.add(myRate);
                        x=x+5;
                        y=y+5;
                    }
                    runOnUiThread(new Runnable() {
                        //当子线程运行完成时，需要提醒adapter数据已经更新了
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            Log.i("更新notify","更新adapter");
                        }
                    });

                }
                catch (IOException e){
                    Log.i("IOException","原因："+e);
                }
            }
        }).start();
    }
}
