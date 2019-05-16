package com.yth520web.third;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

//该类用于显示点击后出现的页面
public class NewActivity extends AppCompatActivity {
    TextView show_rateName;
    EditText show_rateDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_rate);
        Intent intent = getIntent();
       final String rate_name = intent.getStringExtra("rate");
       final String detail = intent.getStringExtra("detail");
        //对用户输入的文字实行实时监控
        show_rateName = (TextView)findViewById(R.id.show_rateName);
        show_rateDetail = (EditText)findViewById(R.id.show_rateDetail);
        show_rateName.setText(rate_name);

        show_rateDetail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String getInput = show_rateDetail.getText().toString();
                Float output = Float.parseFloat(getInput)*Float.parseFloat(detail)/100;
                show_rateName.setText(rate_name+":"+output+"");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Toast.makeText(NewActivity.this,"显示："+rate_name+detail,Toast.LENGTH_SHORT).show();
    }
}
