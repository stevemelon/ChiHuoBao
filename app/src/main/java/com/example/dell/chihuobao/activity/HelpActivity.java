package com.example.dell.chihuobao.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.dell.chihuobao.R;

public class HelpActivity extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        textView = (TextView) findViewById(R.id.text);
        textView.setText("如有问题请访问https://github.com/stevemelon/ChiHuoBao");
    }
}
