package com.example.dell.chihuobao.fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dell.chihuobao.R;
import com.example.dell.chihuobao.util.MyFoodListViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class FoodMenuFragment extends Fragment {


	/*String[] arr = new String[] { "套餐A", "套餐B", "套餐C", "套餐D", "套餐E", "套餐F" };*/

	private ListView listView;
	private ListView listView2 ;
	private Button mButton;

	TextView textView;
	/**
	 * 左边listview的要使用的数组
	 */

	private ArrayList<String> foodType = new ArrayList<String>();
	private ArrayList<String> allFood = new ArrayList<String>();
	/**
	 * 用来记录每一个 1 2 3 4 5 6 在右边listview的位置；
	 */
	List<Integer> nums = new ArrayList<Integer>();

	private void initData(){
		String[] arr2 = new String[] { "food11", "food12", "food13", "food14", "food15" };
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
        foodType.add("套餐F");

		String[][] arr8 = new String[][] { arr2, arr3, arr4, arr5, arr6, arr7 };
		for (int i = 0; i < foodType.size(); i++) {
			allFood.add(foodType.get(i));
			for (int j = 0; j < arr8[i].length; j++) {
				allFood.add(arr8[i][j]);
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
					num = num + arr8[j].length+1;

				}
				nums.add(num);
			}
		}
		nums.add(1000);
	}

	/**
	 * 用来存放 food数组
	 */
	/*List<String> list;*/


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		//引入我们的布局
		View foodMenuLayout =  inflater.inflate(R.layout.food_menu, container, false);
		initData();
		listView = (ListView)foodMenuLayout.findViewById(R.id.listView1);
		listView2 = (ListView)foodMenuLayout.findViewById(R.id.listView2);
		mButton= (Button) foodMenuLayout.findViewById(R.id.but);
		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final AlertDialog addFood = new AlertDialog.Builder(getActivity()).create();
				addFood.setView(new EditText(getActivity()));
				addFood.show();
				Window window = addFood.getWindow();
				window.setContentView(R.layout.add_food_alert);
				Button btnCancel = (Button)addFood.findViewById(R.id.add_food_cancel);
				Button btnConfirm = (Button)addFood.findViewById(R.id.add_food_confirm);
				btnCancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						addFood.dismiss();
					}
				});
				btnConfirm.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(getActivity(),"哈哈",Toast.LENGTH_SHORT).show();
						addFood.dismiss();
					}
				});

			}
		});
		initView();
		return foodMenuLayout;

	}
	private void initView()
	{
		//菜品种类的listView
		MyFoodListViewAdapter myFoodListViewAdapter = new MyFoodListViewAdapter(getActivity(),allFood,foodType);
		myFoodListViewAdapter.notifyDataSetChanged();

		listView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.food_type, foodType));
		listView2.setAdapter(myFoodListViewAdapter);
		/**
		 * 下面这个函数表示点了种类表中的item中，item变色，然后右边的菜品列表跳转的当前种类置顶
		 */
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
									long id) {
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
			 * 下面这个函数表示当滑到了当前类别的食物时就将左边的类别listView变色
			 */
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
								 int visibleItemCount, int totalItemCount) {
				if (nums.contains(firstVisibleItem) && listView.getChildCount() > 0) {

					for (int i = 0; i < listView.getChildCount(); i++) {
						if (i == nums.indexOf(firstVisibleItem)) {
							listView.getChildAt(i).setBackgroundColor(
									Color.rgb(100, 100, 100));
						} else {
							listView.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
						}
					}
				}
			}
		});

	}



}
