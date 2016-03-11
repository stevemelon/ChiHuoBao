package com.example.dell.chihuobao.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dell.chihuobao.R;
import com.example.dell.chihuobao.bean.Item;

import java.util.List;

/**
 * Created by Zx on 2016/3/9.
 */
public class OrderFoodAdapter extends BaseAdapter {
    private List<Item> mOrders=null;//ListView显示的数据
    private int resource;//显示列表项的Layout
    private LayoutInflater inflater;//界面生成器
    private Context context;
    public OrderFoodAdapter(List<Item> orders, int resource, Context context){

        this.mOrders = orders;
        this.resource = resource;
        this.context = context;
        //inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public void setMarkerData(List<Item> orderItems)
    {
        mOrders = orderItems;
    }

    @Override
    public int getCount() {
        return mOrders.size();
    }

    @Override
    public Item getItem(int arg0) {
        return mOrders.get(arg0);
    }
    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        ViewHolder viewHolder=null;



        if(arg1 == null){
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(context);
            arg1 = mInflater.inflate(resource, null);
            viewHolder.price=(TextView) arg1.findViewById(R.id.order_item_price);
            viewHolder.count=(TextView) arg1.findViewById(R.id.order_item_count);
            viewHolder.name=(TextView) arg1.findViewById(R.id.order_item_name);
            arg1.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) arg1.getTag();
        }
        Item item=getItem(arg0);

        if (item!=null){
            viewHolder.price.setText(item.getItem_price());
            viewHolder.count.setText(item.getItem_cout());
            viewHolder.name.setText(item.getItem_name());
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
        TextView price;
        TextView count;
        TextView name;

    }
}
