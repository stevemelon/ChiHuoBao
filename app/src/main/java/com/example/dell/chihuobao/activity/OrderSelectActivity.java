package com.example.dell.chihuobao.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dell.chihuobao.R;
import com.example.dell.chihuobao.appwidget.MyListView;
import com.example.dell.chihuobao.bean.Item;
import com.example.dell.chihuobao.bean.Order;
import com.example.dell.chihuobao.bean.OrderJson;
import com.example.dell.chihuobao.util.BaseLog;
import com.example.dell.chihuobao.util.MyApplication;
import com.example.dell.chihuobao.util.OrderFoodAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderSelectActivity extends AppCompatActivity {
    private ListView mListView;
    private OrderSelectAdapter mOrderSelectAdapter;

    private EditText orderId;
    private Button query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_select);
        mListView= (ListView) findViewById(R.id.orderQuery);
        orderId= (EditText) findViewById(R.id.etSearch);
        query= (Button) findViewById(R.id.login);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String order=orderId.getText().toString();
                getDataFromServe(order);

            }
        });

    }
    public void getDataFromServe(String orderId){
        RequestParams params = new RequestParams(MyApplication.getLocalhost()+"/chb/shop/queryOrderByStatus.do?");
        params.addQueryStringParameter("orderId",orderId);
        //RequestParams params = new RequestParams("http://10.6.11.19:8080/chb/shop/getSendPersonByStatus.do");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                parseData(result);
                BaseLog.e("成功查询");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                BaseLog.e("失败查询");
            }

            @Override
            public void onCancelled(CancelledException cex) {
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
        java.lang.reflect.Type type = new TypeToken<OrderJson>() {
        }.getType();
        BaseLog.e(result);
        OrderJson orderJson=gson.fromJson(result,type);
        BaseLog.e(""+orderJson.getFlag());
        if (orderJson.getFlag()==0){
            new AlertDialog.Builder(OrderSelectActivity.this).setTitle("警告")
                    .setMessage("暂无此订单信息，请检查订单是否正确")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        }else {
//        DeliverManJson deliverManJson = gson.fromJson(result, type);
            mOrderSelectAdapter = new OrderSelectAdapter(orderJson.getRows(), R.layout.order_select_item, this);
            mListView.setAdapter(mOrderSelectAdapter);
        }
    }
    public class OrderSelectAdapter extends BaseAdapter {
        private List<Order> mOrders=null;//ListView显示的数据
        private int resource;//显示列表项的Layout
        private LayoutInflater  inflater;//界面生成器
        private Context context;
        public OrderSelectAdapter(List<Order> orders, int resource, Context context){

            this.mOrders = orders;
            this.resource = resource;
            this.context = context;
            //inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        public void setMarkerData(List<Order> orderItems)
        {
            mOrders = orderItems;
        }

        @Override
        public int getCount() {
            return mOrders.size();
        }

        @Override
        public Order getItem(int arg0) {
            return mOrders.get(arg0);
        }
        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(final int arg0, View arg1, ViewGroup arg2) {
            ViewHolder viewHolder=null;
            List<Item> mItems=new ArrayList<Item>();
            if(arg1 == null){
                viewHolder = new ViewHolder();
                LayoutInflater mInflater = LayoutInflater.from(context);
                arg1 = mInflater.inflate(resource, null);
                viewHolder.telephone = (TextView) arg1.findViewById(R.id.order_search_result_item_telphone);
                viewHolder.time = (TextView) arg1.findViewById(R.id.order_search_result_item_time);
                viewHolder.address = (TextView)arg1.findViewById(R.id.order_search_result_item_address);
                viewHolder.orderId=(TextView) arg1.findViewById(R.id.order_search_result_item_orderId);
                viewHolder.food= (MyListView) arg1.findViewById(R.id.MyListView);//嵌套的listview
                viewHolder.notice=(TextView) arg1.findViewById(R.id.order_notice);
                viewHolder.item_id= (TextView) arg1.findViewById(R.id.order_search_result_item_id);
                viewHolder.receipt= (TextView) arg1.findViewById(R.id.order_receipt);
                viewHolder.totalprice= (TextView) arg1.findViewById(R.id.totalprice);
                arg1.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) arg1.getTag();
            }
            final Order order=mOrders.get(arg0);
            if (order!=null){
                viewHolder.telephone.setText(order.getTelephone());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                Date date=new Date(Long.parseLong(order.getOrdertime()));
                viewHolder.time.setText(formatter.format(date));
                viewHolder.address.setText(order.getAddress());
                viewHolder.orderId.setText(order.getOrderId());
                viewHolder.totalprice.setText("总计:￥"+order.getTotalprice());
                mItems=order.getOrderdelist();
                OrderFoodAdapter orderFoodAdapter=new OrderFoodAdapter(mItems,R.layout.item_mylistview,context);//嵌套listvie的适配器
                viewHolder.food.setAdapter(orderFoodAdapter);
                if (order.getRequest()!=null) {
                    viewHolder.notice.setText("备注：" + order.getRequest());
                }else {
                    viewHolder.notice.setText("备注:无" );
                }
                viewHolder.item_id.setText(""+(arg0+1));
                if (order.getOrderstatus()==3){
                    viewHolder.receipt.setText("订单状态：商家已取消订单");
                }else if (order.getSendstatus()==2){
                    viewHolder.receipt.setText("订单状态：商品派送中");
                }else if (order.getSendstatus()==3){
                    viewHolder.receipt.setText("订单状态：商品已送达");
                }else if (order.getSendstatus()==0){
                    viewHolder.receipt.setText("订单状态：未处理");
                }else if (order.getSendstatus()==1){
                    viewHolder.receipt.setText("订单状态：商家已接单");
                }
            }


//        final Order order = mOrders.get(arg0);
//        TextView telphone= (TextView) arg1.findViewById(R.id.order_search_result_item_telphone);
//        TextView time= (TextView) arg1.findViewById(R.id.order_search_result_item_time);
//        TextView address= (TextView) arg1.findViewById(R.id.order_search_result_item_address);
//        TextView orderId= (TextView) arg1.findViewById(R.id.order_search_result_item_orderId);
//        TextView price= (TextView) arg1.findViewById(R.id.order_item_price);
//        TextView count= (TextView) arg1.findViewById(R.id.order_item_count);
//        TextView name= (TextView) arg1.findViewById(R.id.order_item_name);
//        TextView notice= (TextView) arg1.findViewById(R.id.order_notice);
//        TextView receipt= (TextView) arg1.findViewById(R.id.order_receipt);
//        TextView item_id= (TextView) arg1.findViewById(R.id.order_search_result_item_id);
//        TextView accept= (TextView) arg1.findViewById(R.id.accept_order);

            return arg1;

        }

        private  class ViewHolder
        {
            TextView telephone;
            TextView time;
            TextView address;
            TextView orderId;
            MyListView food;//嵌套的lsitview
            TextView notice;
            TextView receipt;
            TextView item_id;
            TextView totalprice;
        }
    }
}
