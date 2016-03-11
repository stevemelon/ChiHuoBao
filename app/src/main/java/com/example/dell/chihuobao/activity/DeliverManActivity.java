package com.example.dell.chihuobao.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.dell.chihuobao.R;
import com.example.dell.chihuobao.bean.DeliverMan;
import com.example.dell.chihuobao.util.DeliverManAdapter;

import java.util.ArrayList;
import java.util.List;

public class DeliverManActivity extends AppCompatActivity {
    private List<DeliverMan> mDeliverMans;
    private ListView mListView;
    private String[] name=new String[]{"张三","李四","王五"};
    private String[] telephone=new String[]{"123456","545646546546","9999999"};
    private int[] imageId=new int[]{R.drawable.anastasia,
            R.drawable.andriy,
            R.drawable.dmitriy,
            R.drawable.dmitry_96,
            R.drawable.ed,
            R.drawable.illya,
            R.drawable.kirill,
            R.drawable.konstantin,
            R.drawable.oleksii,
            R.drawable.pavel,
            R.drawable.vadim};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_man);
        mDeliverMans=new ArrayList<DeliverMan>();
        mListView= (ListView) findViewById(R.id.list_view);
        initData();
        DeliverManAdapter manAdapter=new DeliverManAdapter(mDeliverMans,R.layout.deliver_item_layout,this);
        mListView.setAdapter(manAdapter);



    }
    public void initData(){
        for (int i=0;i<3;i++){
            DeliverMan deliverMan=new DeliverMan();
            deliverMan.setImageId(imageId[i]);
            deliverMan.setName(name[i]);
            deliverMan.setTelephone(telephone[i]);
            mDeliverMans.add(deliverMan);
        }

    }

}
