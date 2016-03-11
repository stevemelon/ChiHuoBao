package com.example.dell.chihuobao.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.example.dell.chihuobao.R;
import com.example.dell.chihuobao.bean.Order;
import com.example.dell.chihuobao.util.OrderAdapter;
import com.yalantis.phoenix.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleksii Shliama.
 */
public class ListViewFragment extends BaseRefreshFragment {

    private PullToRefreshView mPullToRefreshView;

    private List<Order> list;
    private  ListView mListView;
    private String[] order_search_result_item_id=new String[]{"14","15","16"};
    private String[] telephone=new String[]{"123456","545646546546","9999999"};
    private String[] time=new String[]{"10:00","11:30","12:00"};
    private String[] address=new String[]{"东南大学","文汇人才公寓","三江院"};
    private String[] orderId=new String[]{"11111111111","222222222","33333333"};
    private String[] item_price=new String[]{"10","15","20"};
    private String[] item_count=new String[]{"2","5","1"};
    private String[] item_name=new String[]{"鱼香肉丝","京酱肉丝","炒菜"};
    private String[] notice=new String[]{"多放辣","多加饭","qweqwe"};
    private String[] receipt=new String[]{"旭日科技有限公司","小旭私房菜","小旭生煎"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_list_view, container, false);
         mListView = (ListView) rootView.findViewById(R.id.list_view);
        //listView.setAdapter(simpleAdapter);
        //mListView.setAdapter(new SampleAdapter(getActivity(), R.layout.list_item, mSampleList));
        list=new ArrayList<Order>();
        initData();
//        List<Map<String,Object>> listItems=new ArrayList<Map<String,Object>>();
//
//        SimpleAdapter simpleAdapter=new SimpleAdapter
//                (getActivity(), listItems,R.layout.order_processing_fragment,
//                        new String[]{"telphone","time","address","orderId","item_price","item_count","item_name","notice","receipt"},
//
//                        new int[]{R.id.order_search_result_item_telphone,R.id.order_search_result_item_time,R.id.order_search_result_item_address,R.id.order_search_result_item_orderId,
//                                R.id.order_item_price,R.id.order_item_count,R.id.order_item_name,R.id.order_notice,R.id.order_receipt}
//                );
        Log.i("!!!!!!!!",list.get(0).getAdddress());
        Log.i("!!!!!!!!",list.get(1).getAdddress());
        OrderAdapter simpleAdapter=new OrderAdapter(list,R.layout.order_processing_fragment,getActivity());
        int j=simpleAdapter.getCount();
        mListView.setAdapter(simpleAdapter);
        mPullToRefreshView = (PullToRefreshView) rootView.findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
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
    public void initData(){
        for (int i=0;i<3;i++){
            Order mOrder=new Order();
            mOrder.setAdddress(address[i]);
            mOrder.setOrder_search_result_item_id(order_search_result_item_id[i]);
            mOrder.setItem_count(item_count[i]);
            mOrder.setItem_name(item_name[i]);
            mOrder.setItem_price(item_price[i]);
            mOrder.setNotice(notice[i]);
            mOrder.setReceipt(receipt[i]);
            mOrder.setTelphone(telephone[i]);
            mOrder.setOrderId(orderId[i]);
            mOrder.setTime(time[i]);
            list.add(mOrder);
        }


    }
}
