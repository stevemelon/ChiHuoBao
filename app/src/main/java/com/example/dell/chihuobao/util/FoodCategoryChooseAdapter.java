package com.example.dell.chihuobao.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dell.chihuobao.R;
import com.example.dell.chihuobao.bean.FoodCategory;

import java.util.ArrayList;

/**
 * Created by dell on 2016/3/20.
 */
public class FoodCategoryChooseAdapter extends BaseAdapter {
    private ArrayList<FoodCategory> foodCategoryArrayList = new ArrayList<>();
    private Context context;

    public FoodCategoryChooseAdapter(ArrayList<FoodCategory> foodCategoryArrayList, Context context) {
        this.foodCategoryArrayList = foodCategoryArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.foodCategoryArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodCategoryArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.spinner_item_view,parent,false);
        if (convertView!=null){

            viewHolder.tvFoodCategory = (TextView)convertView.findViewById(R.id.category_name);
            viewHolder.tvFoodCategory.setText(foodCategoryArrayList.get(position).getName());
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    class ViewHolder{
        TextView tvFoodCategory;
    }
}
