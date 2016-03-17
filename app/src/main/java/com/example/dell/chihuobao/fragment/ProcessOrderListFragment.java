package com.example.dell.chihuobao.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.chihuobao.R;
import com.example.dell.chihuobao.activity.DeliverManActivity;
import com.example.dell.chihuobao.appwidget.MyListView;
import com.example.dell.chihuobao.bean.Item;
import com.example.dell.chihuobao.bean.Order;
import com.example.dell.chihuobao.bean.OrderJson;
import com.example.dell.chihuobao.util.BaseLog;
import com.example.dell.chihuobao.util.OrderFoodAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yalantis.phoenix.PullToRefreshView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**已处理订单界面
 * Created by ZX on 2016/3/9.
 */
public class ProcessOrderListFragment extends BaseRefreshFragment {
    private PullToRefreshView mPullToRefreshView;
    private List<Order> list;
    private ListView mListView;
    private ProcessOrderAdapter mProcessOrderAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_list_view, container, false);
        mListView = (ListView) rootView.findViewById(R.id.list_view);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getDataFromServe();
            }
        }).run();
        mPullToRefreshView = (PullToRefreshView) rootView.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getDataFromServe();
                    }
                }).run();
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, REFRESH_DELAY);
            }
        });

        return rootView;
    }
    //    从服务器获取数据
    public void getDataFromServe(){
        RequestParams params = new RequestParams("http://10.6.12.91:8080/zhbj/common.json");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                parseData(result);
                BaseLog.e("成功成功成功成功成功成功成功1");
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                BaseLog.e("失败失败失败失败失败失败1");
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
        list=new ArrayList<Order>();
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<OrderJson>() {
        }.getType();
        OrderJson orderBean = gson.fromJson(result, type);
//        for (int i = 0; i < orderBean.getRows().size(); i++) {
//            list.add(orderBean.getRows().get(i));
//        }
        mProcessOrderAdapter = new ProcessOrderAdapter(orderBean.getRows(), R.layout.order_processing_item, getActivity());
        mListView.setAdapter(mProcessOrderAdapter);
    }
    public class ProcessOrderAdapter extends BaseAdapter {
        private List<Order> mOrders=null;//ListView显示的数据
        private int resource;//显示列表项的Layout
        private LayoutInflater inflater;//界面生成器
        private Context context;
        public ProcessOrderAdapter(List<Order> orders, int resource, Context context){

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
        public View getView(int arg0, View arg1, ViewGroup arg2) {
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
                viewHolder.accept= (TextView) arg1.findViewById(R.id.accept_order);
                arg1.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) arg1.getTag();
            }
            Order order=getItem(arg0);

            if (order!=null){
                viewHolder.telephone.setText(order.getTelephone());
                viewHolder.time.setText(order.getOrdertime());
                viewHolder.address.setText(order.getAddress());
                viewHolder.orderId.setText(order.getOrderId());
                mItems=order.getOrderdelist();
                OrderFoodAdapter orderFoodAdapter=new OrderFoodAdapter(mItems,R.layout.item_mylistview,context);
                viewHolder.food.setAdapter(orderFoodAdapter);
                viewHolder.notice.setText(order.getRequest());
                viewHolder.item_id.setText(order.getId());
                viewHolder.accept.setOnClickListener(new lvButtonListener(arg0));
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
        class lvButtonListener implements View.OnClickListener {
            private int position;

            lvButtonListener(int pos) {
                position = pos;
            }

            @Override
            public void onClick(View v) {
                int vid = v.getId();
                Toast.makeText(context, position + "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DeliverManActivity.class);
                context.startActivity(intent);
            }
        }

        private  class ViewHolder
        {
            TextView telephone;
            TextView time;
            TextView address;
            TextView orderId;
            MyListView food;
            TextView notice;
            TextView item_id;
            TextView accept;

        }
    }

}

