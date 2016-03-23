package com.example.dell.chihuobao.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dell.chihuobao.R;
import com.example.dell.chihuobao.activity.FoodMenuAddNewFoodActivity;
import com.example.dell.chihuobao.bean.FoodCategory;
import com.example.dell.chihuobao.util.BaseLog;
import com.example.dell.chihuobao.util.FoodMenuRightListViewAdapter;
import com.example.dell.chihuobao.util.MyApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class FoodMenuFragment extends Fragment {



    public String URL="http://10.6.12.110:8080/";
    public final static String QUERY_PRODUCT = "chb/shopCategory/getGoodsListSeparatedByGoodscategory.do";
    private ListView listView;
    private ListView listView2 ;
    private Button btnAddFood;
    private String shopId = (int)Double.parseDouble(MyApplication.getInstance().getUser().getUser().get("id").toString())+"";
    /**
     ** 左边listview的要使用的数组
     **/
    private ArrayList arrayAllFood = new ArrayList();
    private ArrayList<String> foodType = new ArrayList<String>();
    private ArrayList<FoodCategory> newFoodCategoryList = new ArrayList<>();

    /**
     * * 用来记录每一个 1 2 3 4 5 6 在右边listview的位置；
     * */
    List<Integer> nums = new ArrayList<Integer>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //引入我们的布局
        //arrayAllFood = new ArrayList();
        View foodMenuLayout =  inflater.inflate(R.layout.fragment_food_menu, container, false);
        listView = (ListView)foodMenuLayout.findViewById(R.id.listView1);
        listView2 = (ListView)foodMenuLayout.findViewById(R.id.listView2);
        btnAddFood = (Button)foodMenuLayout.findViewById(R.id.btn_add_food);
        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), FoodMenuAddNewFoodActivity.class);
                intent.putExtra("flag", 0);
                getContext().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), btnAddFood, "btnShare").toBundle());

            }
        });

        return foodMenuLayout;

    }

    @Override
    public void onStart() {
        clearAll();
        listView = (ListView)getActivity().findViewById(R.id.listView1);
        listView2 = (ListView)getActivity().findViewById(R.id.listView2);
        btnAddFood = (Button)getActivity().findViewById(R.id.btn_add_food);
        getDataFromServer(URL + QUERY_PRODUCT);
        super.onStart();
    }

    public void clearAll(){
        arrayAllFood.clear();
        foodType.clear();
        newFoodCategoryList.clear();
        nums.clear();
        MyApplication.getFoodCategoryArrayList().clear();

    }

    public void getDataFromServer(String url){
        RequestParams params  = new RequestParams(url);
        //shopId
        params.addBodyParameter("shopid", shopId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                parseData(result);
                dataPrepare(newFoodCategoryList);
                initFoodType();
                initData();
                initView();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                BaseLog.e(ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    public void initFoodType(){
        for (int i = 0; i <newFoodCategoryList.size() ; i++) {
            foodType.add(newFoodCategoryList.get(i).getName());

        }
    }

    /**
     * 准备所有数据，左边列表食物种类，右边列表食物种类加食物名字
     */
    private void initData(){

        for (int i = 0; i <newFoodCategoryList.size() ; i++) {
            arrayAllFood.add(newFoodCategoryList.get(i));
            for (int j = 0; j <newFoodCategoryList.get(i).getGoodsList().size(); j++) {
                arrayAllFood.add(newFoodCategoryList.get(i).getGoodsList().get(j));
            }
        }
        for (int i = 0; i < newFoodCategoryList.size(); i++)
        {
            if (i == 0)
            {
                nums.add(0);
            } else if (i > 0 && i < newFoodCategoryList.size())
            {
                int num = 0;
                for (int j = 0; j < i; j++)
                {
                    num = num + newFoodCategoryList.get(j).getGoodsList().size()+1;

                }
                nums.add(num);
            }
        }
    }

    private void initView() {
        //菜品种类的listView
        FoodMenuRightListViewAdapter myFoodListViewAdapter = new FoodMenuRightListViewAdapter(getActivity(),arrayAllFood);
        myFoodListViewAdapter.notifyDataSetChanged();
        myFoodListViewAdapter.notifyDataSetChanged();
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.adapter_left_food_type, foodType));
        listView2.setAdapter(myFoodListViewAdapter);
        /**
         * * 下面这个函数表示点了种类表中的item中，item变色，然后右边的菜品列表跳转的当前种类置顶
         * */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < listView.getChildCount(); i++) {
                    if (i == position) {
                        view.setBackgroundColor(Color.rgb(0, 136, 255));
                        TextView textView =(TextView) view.findViewById(R.id.food_type_text);
                        textView.setTextColor(Color.rgb(255, 255, 255));
                    } else {
                        view.setBackgroundColor(Color.TRANSPARENT);
                    }
                }
                listView2.setSelection(nums.get(position));
                Log.d("position", "" + nums.get(position));

            }
        });

        listView2.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
            /**
             * * 下面这个函数表示当滑到了当前类别的食物时就将左边的类别listView变色
             * */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (nums.contains(firstVisibleItem) && listView.getChildCount() > 0) {

                    for (int i = 0; i < listView.getChildCount(); i++) {
                        if (i == nums.indexOf(firstVisibleItem)) {
                            listView.getChildAt(i).setBackgroundColor(Color.rgb(0, 136, 255));
                            TextView textView =(TextView) listView.getChildAt(i).findViewById(R.id.food_type_text);
                            textView.setTextColor(Color.rgb(255, 255, 255));
                        } else {
                            listView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                            TextView textView =(TextView) listView.getChildAt(i).findViewById(R.id.food_type_text);
                            textView.setTextColor(Color.rgb(0,0,0));
                        }
                    }
                }
            }
        });
    }


    public void parseData(String result){
        Gson gson  = new Gson();
        newFoodCategoryList = gson.fromJson(result,new TypeToken<ArrayList<FoodCategory>>() {}.getType());

    }

    public void dataPrepare(ArrayList<FoodCategory> arrayList){
        for (int i = 0; i < arrayList.size(); i++) {
            MyApplication.getFoodCategoryArrayList().add(newFoodCategoryList.get(i));
        }
    }




}
