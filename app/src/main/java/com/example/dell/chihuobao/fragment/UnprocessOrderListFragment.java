package com.example.dell.chihuobao.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.yalantis.phoenix.PullToRefreshView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**未处理订单界面
 * Created by Zx.
 */
public class UnprocessOrderListFragment extends BaseRefreshFragment {
    private PullToRefreshView mPullToRefreshView;
    public List<Order> list;
    private  ListView mListView;
    private String shopId;
    private OrderAdapter simpleAdapter;
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
                //此处写业务代码
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
        String shopId=(int)Double.parseDouble(MyApplication.getInstance().getUser().getUser().get("id").toString())+"";
        RequestParams params = new RequestParams("http://10.6.12.110:8080/chb/shop/queryOrderByStatus.do?");
        params.addQueryStringParameter("shopId", shopId);
        params.addQueryStringParameter("sendStatus", "0");
        params.addQueryStringParameter("orderStatus", "2");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BaseLog.e(result);
                parseData(result);
                BaseLog.e("成功未处理");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                BaseLog.e("失败未处理");
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
        java.lang.reflect.Type type = new TypeToken<OrderJson>() {
        }.getType();
        OrderJson orderBean = gson.fromJson(result, type);
        simpleAdapter = new OrderAdapter(orderBean.getRows(), R.layout.order_unprocessing_item, getActivity());
        mListView.setAdapter(simpleAdapter);
    }
    public class OrderAdapter extends BaseAdapter {

        private List<Order> mOrders=null;//ListView显示的数据
        private int resource;//显示列表项的Layout
        private LayoutInflater  inflater;//界面生成器
        private Context context;
        public OrderAdapter(List<Order> orders, int resource, Context context){

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
                viewHolder.accept= (TextView) arg1.findViewById(R.id.accept_order);
                viewHolder.reject= (TextView) arg1.findViewById(R.id.reject_order);
                arg1.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) arg1.getTag();
            }
            final Order order=mOrders.get(arg0);
            if (order!=null){
                viewHolder.telephone.setText(order.getTelephone());
                viewHolder.time.setText(order.getOrdertime());
                viewHolder.address.setText(order.getAddress());
                final String a=order.getOrderId();
                BaseLog.e("!!!!" + a);
                viewHolder.orderId.setText(order.getOrderId());
                mItems=order.getOrderdelist();
                OrderFoodAdapter orderFoodAdapter=new OrderFoodAdapter(mItems,R.layout.item_mylistview,context);//嵌套listvie的适配器
                viewHolder.food.setAdapter(orderFoodAdapter);
                viewHolder.notice.setText("备注：" + order.getRequest());
                viewHolder.item_id.setText(""+(arg0+1));
                viewHolder.accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(context).setTitle("确认订单吗？")
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 点击“确认”后的操作
//                                        mOrders.remove(arg0);
//                                        simpleAdapter.notifyDataSetChanged();
                                        RequestParams params = new RequestParams("http://10.6.12.110:8080/chb/shop/ignoreOrder.do?");
                                        params.addQueryStringParameter("orderId", a);
                                        params.addQueryStringParameter("sendStatus", "1");
                                        x.http().post(params, new Callback.CommonCallback<String>() {
                                            @Override
                                            public void onSuccess(String result) {
                                                mOrders.remove(arg0);
                                                simpleAdapter.notifyDataSetChanged();
                                                BaseLog.e("确认订单成功");
                                            }

                                            @Override
                                            public void onError(Throwable ex, boolean isOnCallback) {
                                                Toast.makeText(context, "确认订单连接服务器失败", Toast.LENGTH_SHORT).show();
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
                viewHolder.reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(context).setTitle("确认删除订单吗？")
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 点击“确认”后的操作
//                                        mOrders.remove(arg0);
//                                        simpleAdapter.notifyDataSetChanged();

                                        RequestParams params = new RequestParams("http://10.6.12.110:8080/chb/shop/ignoreOrder.do?");
                                        String orderId=order.getId();
                                        String Orderstatus=order.getOrderstatus()+"";
                                        params.addQueryStringParameter("orderId", a);
                                        params.addQueryStringParameter("orderStatus","3");
                                        x.http().post(params, new Callback.CommonCallback<String>() {
                                            @Override
                                            public void onSuccess(String result) {
                                                mOrders.remove(arg0);
                                                simpleAdapter.notifyDataSetChanged();
                                                BaseLog.e("无效订单成功");
                                            }
                                            @Override
                                            public void onError(Throwable ex, boolean isOnCallback) {
                                                Toast.makeText(context,"无效订单连接服务器失败",Toast.LENGTH_SHORT).show();
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
            TextView item_id;
            TextView accept;
            TextView reject;

        }
    }
}