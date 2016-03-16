package com.example.dell.chihuobao.util;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;


import java.util.ArrayList;
import com.example.dell.chihuobao.R;
import com.example.dell.chihuobao.activity.FoodMenuModifyActivity;
import com.example.dell.chihuobao.bean.Food;
import com.example.dell.chihuobao.bean.FoodCategory;

import org.xutils.x;

/**
 * Created by dell on 2016/3/1.
 */
public class FoodMenuRightListViewAdapter extends BaseAdapter {
    public final static String URL = "";
    private ArrayList foodType;
    private ArrayList allFood;
    private Context context;
    private LayoutInflater inflater;
    final static  int ITEM_TAG = 1;
    final static int ITEM_NORMAL=0;
    private ArrayList<FoodCategory> foodCategoryArrayList ;
    public FoodMenuRightListViewAdapter(Context context, ArrayList allFood, ArrayList arrayList){
        this.context = context;
        this.foodType = arrayList;
        this.allFood = allFood;
        inflater  = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return allFood.size();
    }

    @Override
    public Object getItem(int position) {
        return allFood.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (foodCategoryArrayList.contains(getItem(position))){
            return ITEM_TAG;
        }else {
            return ITEM_NORMAL;
        }

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolderTag viewHolderTag = null;
        ViewHolderNormal viewHolderNormal = null;

        int type = getItemViewType(position);

        //无convertView，需要new出各个控件
        if(convertView == null)
        {
            Log.d("convertView = ", " NULL1");

            //按当前所需的样式，确定new的布局
            switch(type)
            {
                case ITEM_TAG:
                    convertView = inflater.inflate(R.layout.adapter_food_menu_right_tag, parent, false);
                    viewHolderTag = new ViewHolderTag();
                    viewHolderTag.tvFoodTypeName = (TextView)convertView.findViewById(R.id.group_list_item_text);
                    //viewHolderTag.tvFoodTypeModify = (TextView)convertView.findViewById(R.id.tv_food_type_modify);
                    Log.d("convertView = ", "ITEM_TAG");
                    convertView.setTag(viewHolderTag);
                    break;
                case ITEM_NORMAL:
                    convertView = inflater.inflate(R.layout.adapter_food_menu_right_item, parent, false);
                    viewHolderNormal = new ViewHolderNormal();
                    viewHolderNormal.tvFoodName = (TextView)convertView.findViewById(R.id.group_list_item_text);
                    viewHolderNormal.imageView = (ImageView)convertView.findViewById(R.id.food_image);
                    viewHolderNormal.tvPrice = (TextView)convertView.findViewById(R.id.group_list_item_price);
                    viewHolderNormal.btnModify = (Button)convertView.findViewById(R.id.item_food_detail_modify);
                    viewHolderNormal.btnSoldOut = (Button)convertView.findViewById(R.id.item_food_detail_soldOut);
                    viewHolderNormal.tvAchieveMoney=(TextView)convertView.findViewById(R.id.item_food_achieve_money);
                    viewHolderNormal.tvReduceMoney = (TextView)convertView.findViewById(R.id.item_food_reduce_money);

                    Log.d("convertView = ", "NULL ITEM_NORMAL");
                    convertView.setTag(viewHolderNormal);
                    break;

                }
            }else{
            //有convertView，按样式，取得不用的布局
            switch(type)
            {
                case ITEM_TAG:
                    viewHolderTag = (ViewHolderTag) convertView.getTag();
                    Log.d("convertView !!!!!!= ", "NULL ITEM_TAG");
                    break;
                case ITEM_NORMAL:
                    viewHolderNormal = (ViewHolderNormal) convertView.getTag();
                    Log.d("convertView !!!!!!= ", "NULL ITEM_NORMAL");
                    break;
                }
            }

        //设置资源
        switch(type)
        {
            case ITEM_TAG:
                viewHolderTag.tvFoodTypeName.setText(((FoodCategory)(getItem(position))).getName());
                /*viewHolderTag.tvFoodTypeModify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TableLayout foodTypeEditView = (TableLayout) inflater.inflate(R.layout.dialog_food_menu_right_type_modify, null);
                        final EditText editText = (EditText)foodTypeEditView.findViewById(R.id.food_type_modify_edit);
                        editText.setText(getItem(position).toString());

                        new AlertDialog.Builder(context)
                                .setTitle("修改菜品种类")
                                .setView(foodTypeEditView).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String editFoodType = editText.getText().toString();
                                int index = foodType.indexOf(getItem(position));
                                foodType.set(index,editFoodType);
                                allFood.set(position,editFoodType);
                            }
                        })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .create()
                                .show();

                    }

                });*/
                break;
            case ITEM_NORMAL:
                viewHolderNormal.tvFoodName.setText(getItem(position).toString());
                viewHolderNormal.tvAchieveMoney.setText(((Food)(getItem(position))).getAchievemoney());
                viewHolderNormal.tvReduceMoney.setText(((Food)(getItem(position))).getReducemoney());
                viewHolderNormal.tvPrice.setText(((Food)(getItem(position))).getPrice());

                viewHolderNormal.btnSoldOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /**
                         * 菜品售罄
                         */
                    }
                });
                viewHolderNormal.btnModify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /**
                         * 编辑菜品详细信息
                         */
                        Intent intent = new Intent(context, FoodMenuModifyActivity.class);
                        context.startActivity(intent);

                    }
                });
                break;
            }
        x.image().bind(viewHolderNormal.imageView,URL+((Food)(getItem(position))).getPhoto());

        return convertView;


    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    class ViewHolderTag{
        TextView tvFoodTypeName;
        //TextView tvFoodTypeModify;

    }

    class ViewHolderNormal{
        ImageView imageView;
        TextView tvFoodName;
        TextView tvPrice;
        Button btnModify;
        Button btnSoldOut;
        TextView tvAchieveMoney;
        TextView tvReduceMoney;
    }
}
