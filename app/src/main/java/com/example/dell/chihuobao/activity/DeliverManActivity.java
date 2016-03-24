package com.example.dell.chihuobao.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.dell.chihuobao.bean.DeliverManJson;
import com.example.dell.chihuobao.util.BaseLog;
import com.example.dell.chihuobao.util.MyApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class DeliverManActivity extends AppCompatActivity {
    private ListView mListView;
    private String orderId;
    private DeliverManAdapter mDeliverManAdapter;
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
        mListView= (ListView) findViewById(R.id.list_view);
        Intent intent=getIntent();
        orderId=intent.getStringExtra("orderId");
        getDataFromServe();
    }
    public void getDataFromServe(){
        RequestParams params = new RequestParams("http://10.6.12.88:8080/chb/shop/getSendPersonByStatus.do");
        //RequestParams params = new RequestParams("http://10.6.11.19:8080/chb/shop/getSendPersonByStatus.do");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                parseData(result);
                BaseLog.e("成功配送");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                BaseLog.e("失败配送");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                BaseLog.e("取消取消取消取消取消取消取消取消取消取消取消1");
            }

            @Override
            public void onFinished() {

            }
        });
    }
    /*
    将从服务器端获取的数据解析，传给listview的适配器
     */
    public void parseData(String result){
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<DeliverManJson>() {
        }.getType();
        BaseLog.e(result);
        DeliverManJson deliverManJson = gson.fromJson(result, type);
        mDeliverManAdapter = new DeliverManAdapter(deliverManJson.getDeliverMans(), R.layout.deliver_item_layout,this);
        mListView.setAdapter(mDeliverManAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                new AlertDialog.Builder(DeliverManActivity.this).setTitle("确认配送吗？")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 点击“确认”后的操作
                                BaseLog.e("！！！！！！！！！"+orderId);
                                RequestParams params = new RequestParams(MyApplication.getLocalhost()+"/chb/shop/ignoreOrder.do?");
                                params.addQueryStringParameter("orderId", orderId);
                                params.addQueryStringParameter("transferOrder", "1");
                                x.http().post(params, new Callback.CommonCallback<String>() {
                                    @Override
                                    public void onSuccess(String result) {
                                        Toast.makeText(DeliverManActivity.this, "配送成功", Toast.LENGTH_SHORT).show();
                                        //getDataFromServe();
                                        BaseLog.e("配送成功");
                                        finish();
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
                int i = deliverMan.getId()% imageId.length;
                Log.i("22",""+viewHolder);
                viewHolder.name.setText(deliverMan.getName());
                viewHolder.photo.setImageResource(imageId[i]);
                viewHolder.telephone.setText(deliverMan.getPhone());
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
