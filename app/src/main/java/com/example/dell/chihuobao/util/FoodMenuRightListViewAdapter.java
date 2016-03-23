package com.example.dell.chihuobao.util;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
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
import com.example.dell.chihuobao.activity.MainActivity;
import com.example.dell.chihuobao.bean.Food;
import com.example.dell.chihuobao.bean.FoodCategory;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by dell on 2016/3/1.
 */
public class FoodMenuRightListViewAdapter extends BaseAdapter {
    public String URL="http://10.6.12.88:8080/";
    ImageOptions imageOptions = new ImageOptions.Builder()
            .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                    // 加载中或错误图片的ScaleType
                    //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            .setLoadingDrawableId(R.mipmap.ic_launcher)
            .setFailureDrawableId(R.mipmap.ic_launcher)
            .build();
    private ArrayList allFood;
    private FragmentActivity context;
    private LayoutInflater inflater;
    final static  int ITEM_TAG = 1;
    final static int ITEM_NORMAL=0;

    public FoodMenuRightListViewAdapter(FragmentActivity context, ArrayList allFood){
        this.context = context;
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
        if (getItem(position) instanceof FoodCategory){
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

            //按当前所需的样式，确定new的布局
            switch(type)
            {
                case ITEM_TAG:
                    convertView = inflater.inflate(R.layout.adapter_food_menu_right_tag, parent, false);
                    viewHolderTag = new ViewHolderTag();
                    viewHolderTag.tvFoodTypeName = (TextView)convertView.findViewById(R.id.group_list_item_text);
                    //viewHolderTag.tvFoodTypeModify = (TextView)convertView.findViewById(R.id.tv_food_type_modify);
                    convertView.setTag(viewHolderTag);
                    break;
                case ITEM_NORMAL:
                    convertView = inflater.inflate(R.layout.adapter_food_menu_right_item, parent, false);
                    viewHolderNormal = new ViewHolderNormal();
                    viewHolderNormal.tvFoodName = (TextView)convertView.findViewById(R.id.group_list_item_text);
                    viewHolderNormal.imageView = (ImageView)convertView.findViewById(R.id.food_image);
                    viewHolderNormal.tvPrice = (TextView)convertView.findViewById(R.id.group_list_item_price);
                    viewHolderNormal.btnModify = (Button)convertView.findViewById(R.id.item_food_detail_modify);
                    viewHolderNormal.tvAchieveMoney=(TextView)convertView.findViewById(R.id.item_food_achieve_money);
                    viewHolderNormal.tvReduceMoney = (TextView)convertView.findViewById(R.id.item_food_reduce_money);

                    convertView.setTag(viewHolderNormal);
                    break;

            }
        }else{
            //有convertView，按样式，取得不用的布局
            switch(type)
            {
                case ITEM_TAG:
                    viewHolderTag = (ViewHolderTag) convertView.getTag();
                    break;
                case ITEM_NORMAL:
                    viewHolderNormal = (ViewHolderNormal) convertView.getTag();
                    break;
            }
        }

        //设置资源
        switch(type)
        {
            case ITEM_TAG:
                viewHolderTag.tvFoodTypeName.setText(((FoodCategory)(getItem(position))).getName());
                break;
            case ITEM_NORMAL:


                viewHolderNormal.tvFoodName.setText(((Food)(getItem(position))).getName());
                viewHolderNormal.tvAchieveMoney.setText(((Food) (getItem(position))).getAchievemoney());
                viewHolderNormal.tvReduceMoney.setText(((Food) (getItem(position))).getReducemoney());
                viewHolderNormal.tvPrice.setText((int)Double.parseDouble(((Food) (getItem(position))).getPrice())+"元");
                x.image().bind(viewHolderNormal.imageView,URL+((Food)(getItem(position))).getPhoto().replaceAll("\\\\", "/"),imageOptions);
                viewHolderNormal.btnModify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /**
                         * 编辑菜品详细信息
                         */

                        Intent intent = new Intent(context, FoodMenuModifyActivity.class);
                        intent.putExtra("id", ((Food) getItem(position)).getId());

                        context.startActivity(intent);



                    }
                });
                break;
        }


        return convertView;


    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    class ViewHolderTag{
        TextView tvFoodTypeName;

    }

    class ViewHolderNormal{
        ImageView imageView;
        TextView tvFoodName;
        TextView tvPrice;
        Button btnModify;
        TextView tvAchieveMoney;
        TextView tvReduceMoney;
    }
}
