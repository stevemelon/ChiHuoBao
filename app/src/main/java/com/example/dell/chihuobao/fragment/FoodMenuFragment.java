package com.example.dell.chihuobao.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.chihuobao.R;
import com.example.dell.chihuobao.activity.FoodMenuAddNewFoodActivity;
import com.example.dell.chihuobao.bean.Food;
import com.example.dell.chihuobao.bean.FoodCategory;
import com.example.dell.chihuobao.util.BaseLog;
import com.example.dell.chihuobao.util.FoodMenuRightListViewAdapter;
import com.example.dell.chihuobao.util.ServerUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class FoodMenuFragment extends Fragment {


    /*String[] arr = new String[] { "套餐A", "套餐B", "套餐C", "套餐D", "套餐E", "套餐F" };*/

    private ListView listView;
    private ListView listView2 ;
    private String stringFoodCategory = new String();
    private Button mButton;
    private ServerUtil serverUtil = new ServerUtil();
    /**
     ** 左边listview的要使用的数组
     **/
    private ArrayList<FoodCategory> foodCategoryArrayList = new ArrayList<FoodCategory>();
    private ArrayList<ArrayList<Food>> temp;
    private ArrayList<Food> foodArrayList;
    private ArrayList arrayAllfood;
    private ArrayList<String> foodType = new ArrayList<String>();
    private ArrayList<String> allFood = new ArrayList<String>();

    /**
     * * 用来记录每一个 1 2 3 4 5 6 在右边listview的位置；
     * */
    List<Integer> nums = new ArrayList<Integer>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            initFoodType();
            initData();
            initView();
        }
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    //引入我们的布局
        temp = new ArrayList<>();
        arrayAllfood = new ArrayList();
        View foodMenuLayout =  inflater.inflate(R.layout.fragment_food_menu, container, false);
        mButton= (Button) foodMenuLayout.findViewById(R.id.but);
        listView = (ListView)foodMenuLayout.findViewById(R.id.listView1);
        listView2 = (ListView)foodMenuLayout.findViewById(R.id.listView2);
        mButton= (Button) foodMenuLayout.findViewById(R.id.but);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), FoodMenuAddNewFoodActivity.class);
                startActivity(intent);

            }
        });
        getDataFromServer("http://10.6.12.67:8080/testCategory.json");

        return foodMenuLayout;

    }
    public void getDataFromServer(String url){
        RequestParams params  = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(x.app(),result,Toast.LENGTH_SHORT).show();
                parseData(result);
                getFoodData(foodCategoryArrayList);


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
        for (int i = 0; i <foodCategoryArrayList.size() ; i++) {
            foodType.add(foodCategoryArrayList.get(i).getName());

        }
    }

    /**
     * 准备所有数据，左边列表食物种类，右边列表食物种类加食物名字
     */
    private void initData(){
        /*String[] arr2 = new String[] { "food11", "food12", "food13", "food14", "food15" };
        String[] arr3 = new String[] {  "food21", "food22", "food23", "food24", "food25", "food26" };
        String[] arr4 = new String[] {  "food31", "food32", "food33", "food34" };
        String[] arr5 = new String[] {  "food41", "food42", "food43", "food44", "food45", "food46", "food" };
        String[] arr6 = new String[] {  "food51", "food52", "food53" };
        String[] arr7 = new String[] { "food61", "food62", "food63", "food64", "food65", "food66", "food67", "food68" };

        foodType.add("套餐A");
        foodType.add("套餐B");
        foodType.add("套餐C");
        foodType.add("套餐D");
        foodType.add("套餐E");
        foodType.add("套餐F");*/

        //stringFoodCategory = serverUtil.queryCategoryTest("http://10.6.12.69:8080/testCategory.json");
        /**
         * 获得食物种类的ArrayList
         */



        /**
         * allFood包括食物种类和食物名字
         */
        for (int i = 0; i <foodType.size() ; i++) {
            //allFood.add(foodType.get(i));
            arrayAllfood.add(foodCategoryArrayList.get(i));
            for (int j = 0; j <temp.get(i).size(); j++) {
                //allFood.add(temp.get(i).get(j).getName());
                arrayAllfood.add(temp.get(i).get(j));
            }
        }
        for (int i = 0; i < foodType.size(); i++)
        {
            if (i == 0)
            {
                nums.add(0);
            } else if (i > 0 && i < foodType.size())
            {
                int num = 0;
                for (int j = 0; j < i; j++)
                {
                    num = num + temp.get(j).size()+1;

                }
                nums.add(num);
            }
        }
    }

    private void initView()
    {
        //菜品种类的listView
        FoodMenuRightListViewAdapter myFoodListViewAdapter = new FoodMenuRightListViewAdapter(getActivity(),arrayAllfood,foodType);
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
                        view.setBackgroundColor(Color.rgb(100, 100, 100));
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
                            listView.getChildAt(i).setBackgroundColor(Color.rgb(100, 100, 100));
                        } else {
                            listView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                        }
                    }
                }
            }
        });
    }


    public void parseData(String result){
        Gson gson  = new Gson();
        foodCategoryArrayList = gson.fromJson(result,new TypeToken<ArrayList<FoodCategory>>() {}.getType());

    }

    public ArrayList<Food> parseData(String result,String type){
        Gson gson  = new Gson();
        foodArrayList = gson.fromJson(result,new TypeToken<ArrayList<Food>>() {}.getType());
        return foodArrayList;

    }
    public void  getFoodData(ArrayList arraylist) {
        BaseLog.e(arraylist.size() +"");
        for (int i = 1; i <= arraylist.size(); i++) {
            RequestParams params = new RequestParams("http://10.6.12.67:8080/testProduct"+ i+".json");
            try {
                x.http().requestSync(HttpMethod.GET, params, new Callback.TypedCallback<String>() {
                    @Override
                    public Type getLoadType() {
                        return null;
                    }

                    @Override
                    public void onSuccess(String result) {
                        temp.add(parseData(result, result));
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        handler.sendEmptyMessage(1);


    }



}
