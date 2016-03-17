package com.example.dell.chihuobao.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.chihuobao.R;
import com.example.dell.chihuobao.bean.DeliverMan;
import com.example.dell.chihuobao.util.BaseLog;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(DeliverManActivity.this).setTitle("确认配送吗？")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击“确认”后的操作
                                RequestParams params = new RequestParams("http://10.6.12.136:8080/chb/shop/transferOrder.do?");
                                params.addQueryStringParameter("orderId", "1");
                                x.http().post(params, new Callback.CommonCallback<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        BaseLog.e("配送成功");
                                    }
                                    @Override
                                    public void onError(Throwable ex, boolean isOnCallback) {
                                        Toast.makeText(DeliverManActivity.this, "配送连接服务器失败", Toast.LENGTH_SHORT).show();
                                    }
                                    @Override
                                    public void onCancelled(CancelledException cex) {

                                    }

                                    @Override
                                    public void onFinished() {

                                    }
                                });

                            }
                        })
                        .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击“返回”后的操作,这里不设置没有任何操作
                            }
                        }).show();

            }
        });



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
    public class DeliverManAdapter extends BaseAdapter {
        private List<DeliverMan> mDeliverMans;
        private int resource;
        //private LayoutInflater mInflater;
        private Context mContext;
        public DeliverManAdapter(List<DeliverMan> deliverManList, int recouce, Context context){
            this.mDeliverMans=deliverManList;
            this.resource=recouce;
            this.mContext=context;
        }
        @Override
        public int getCount() {
            return mDeliverMans.size();
        }

        @Override
        public DeliverMan getItem(int position) {
            return mDeliverMans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView==null){
                viewHolder = new ViewHolder();
                LayoutInflater inflater=LayoutInflater.from(mContext);
                convertView=inflater.inflate(resource,parent, false);
                viewHolder.name= (TextView) convertView.findViewById(R.id.name);
                viewHolder.photo= (ImageView) convertView.findViewById(R.id.photo);
                viewHolder.telephone= (TextView) convertView.findViewById(R.id.telephone);
                convertView.setTag(viewHolder);
            }else{
                Log.i("!!!!!!!!!!!", "" + convertView.getTag());
                viewHolder=(ViewHolder)convertView.getTag();
            }
            DeliverMan deliverMan=getItem(position);
            if (deliverMan!=null){
                Log.i("22",""+viewHolder);
                viewHolder.name.setText(deliverMan.getName());
                viewHolder.photo.setImageResource(deliverMan.getImageId());
                viewHolder.telephone.setText(deliverMan.getTelephone());
            }
            return convertView;
        }
        private  class ViewHolder
        {
            TextView telephone;
            TextView name;
            ImageView photo;
        }
    }

}
