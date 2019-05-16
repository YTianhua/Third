package com.yth520web.third;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
 //使用自定义adapter实现添加listview，练习使用Map
   // ArrayList<HashMap<String,String>> mListView;
    List<MyRate> mListView;
    MyAdapter adapter;
    SimpleAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initListView();//添加数据的数组
        //initAdapter();//调试适配器的方法
        adapter = new MyAdapter(this,R.layout.item_layout,mListView);
        final ListView listView = (ListView)findViewById(R.id.mListView);
        listView.setAdapter(adapter);
        //GlidView
        listView.setEmptyView(findViewById(R.id.empty_tv));
        //adapter.remove(listView.getItemAtPosiint)
        //listItem.remove(position)
        //
         //listview的监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MyRate myRate = (MyRate)mListView.get(position);
            //Toast.makeText(MainActivity.this,"点击："+myRate.getMLeftText()+myRate.getMRightText(),Toast.LENGTH_SHORT).show();
                 Intent intent = new Intent();
                 intent.setClass(MainActivity.this,NewActivity.class);
                 //将汇率名称和具体汇率通过intent传输
                 intent.putExtra("rate",myRate.getMLeftText());
                 intent.putExtra("detail",myRate.getMRightText());
                 startActivity(intent);//别忘记注册活动
             }
         });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //Toast.makeText(MainActivity.this,"长按Item",Toast.LENGTH_SHORT).show();
                //实现长按弹出对话框，判断是否移除ListView

                //通过AlertDialog.Builder这个类来实例化我们一个AlertDialog对象
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //设置Title图标
                builder.setIcon(R.drawable.orange);
                //设置Title内容
                builder.setTitle("弹出警告框");
                //设置Message
                builder.setMessage("确定要删除吗？");
                //设置一个PositiveButton
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"你点击了确定",Toast.LENGTH_SHORT).show();
                        //实现点击确定移除的功能
                        mListView.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("取消", null).show();
                //builder.setNegativeButton("取消",null).show();
                return true;
            }
        });
    }
/*    public void initListView(){
        mListView = new ArrayList<HashMap<String,String>>();
        HashMap<String,String> map = new HashMap<String, String>();
        for(int i = 0;i<20;i++){
            map.put("rate","Rate:"+i);//key &String
            map.put("detail","Detail:"+i);
            mListView.add(map);
        }
        listAdapter = new SimpleAdapter(this,mListView,R.layout.item_layout,
                new String[] {"rate","detail"},
                new int[] {R.id.mLeftText,R.id.mRightText});
    }*/
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

      public void goToGlidView(View view){
          Intent intent = new Intent(MainActivity.this, GridViewClass.class);
          startActivity(intent);
          //别忘记注册
      }
}
