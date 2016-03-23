package com.example.dell.chihuobao.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dell.chihuobao.R;
import com.example.dell.chihuobao.fragment.BarChartFragment;
import com.example.dell.chihuobao.fragment.FoodMenuFragment;
import com.example.dell.chihuobao.fragment.ProcessOrderListFragment;
import com.example.dell.chihuobao.fragment.SettingFragment;
import com.example.dell.chihuobao.fragment.UnprocessOrderListFragment;

import java.util.ArrayList;

/**
 * 嵌套Fragment使用
 *
 *
 * //@see http://www.cnblogs.com/over140/archive/2013/01/02/2842227.html
 *
 */
public class MainActivity extends BaseActivity implements OnClickListener {

    private LinearLayout mTabWeixin;
    private LinearLayout mTabFrd;
    private LinearLayout mTabAddress;
    private LinearLayout mTabSetting;
    //底部4个导航控件中的图片按钮
    private ImageButton mImgOrder;
    private ImageButton mImgFood;
    private ImageButton mImgCount;
    private ImageButton mImgSetting;
    //底部4个导航控件中的文字
    private TextView mTextOrder;
    private TextView mTextFood;
    private TextView mTextCount;
    private TextView mTextSetting;
    //四个界面
    private Fragment tab01;
    private Fragment tab02;
    private Fragment tab03;
    private Fragment tab04;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_main);
        initView();

        initEvents();
        findViewById(R.id.id_tab_weixin).performClick();
    }

    private void initEvents() {
        mTabWeixin.setOnClickListener(this);
        mTabFrd.setOnClickListener(this);
        mTabAddress.setOnClickListener(this);
        mTabSetting.setOnClickListener(this);

    }

    //    初始化控件
    private void initView() {
        mTabWeixin = (LinearLayout)findViewById(R.id.id_tab_weixin);
        mTabFrd = (LinearLayout)findViewById(R.id.id_tab_frd);
        mTabAddress = (LinearLayout)findViewById(R.id.id_tab_address);
        mTabSetting = (LinearLayout)findViewById(R.id.id_tab_setting);
        mImgOrder = (ImageButton)findViewById(R.id.id_tab_order_img);
        mImgFood = (ImageButton)findViewById(R.id.id_tab_food_img);
        mImgCount = (ImageButton)findViewById(R.id.id_tab_count_img);
        mImgSetting = (ImageButton)findViewById(R.id.id_tab_setting_img);
        mTextSetting= (TextView) findViewById(R.id.id_tab_setting_text);
        mTextCount= (TextView) findViewById(R.id.id_tab_count_text);
        mTextOrder= (TextView) findViewById(R.id.id_tab_orde_text);
        mTextFood= (TextView) findViewById(R.id.id_tab_food_text);

    }


    //点击相应按钮后触发的事件
    @Override
    public void onClick(View v) {
        resetImg();
        switch (v.getId()) {
            case R.id.id_tab_weixin:
                setSelect(0);
                break;
            case R.id.id_tab_frd:
                setSelect(1);
                break;
            case R.id.id_tab_address:
                setSelect(2);
                break;
            case R.id.id_tab_setting:
                setSelect(3);
            default:
                break;
        }
    }
    //将Fragment隐藏的方法
    private void hideFragment(FragmentTransaction transaction) {
        if (tab01 != null) {
            transaction.hide(tab01);
        }
        if (tab02 != null) {
            transaction.hide(tab02);
        }
        if (tab03 != null) {
            transaction.hide(tab03);
        }
        if (tab04 != null) {
            transaction.hide(tab04);
        }

    }

    //实现导航栏图标变色的方法
    private void resetImg() {
        mImgOrder.setImageResource(R.drawable.shopping_home_tab_order);
        mImgFood.setImageResource(R.drawable.shopping_home_tab_take_out);
        mImgCount.setImageResource(R.drawable.shopping_home_tab_found);
        mImgSetting.setImageResource(R.drawable.shopping_home_tab_personal);
        mTextOrder.setTextColor(Color.parseColor("#999999"));
        mTextCount.setTextColor(Color.parseColor("#999999"));
        mTextFood.setTextColor(Color.parseColor("#999999"));
        mTextSetting.setTextColor(Color.parseColor("#999999"));
    }

    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();//创建一个事务
        hideFragment(transaction);//我们先把所有的Fragment隐藏了，然后下面再开始处理具体要显示的Fragment
        switch (i) {
            case 0:
                if (tab01 == null) {
                    tab01 = FragmentParent.newInstance(0);
				/*
				 * 将Fragment添加到活动中，public abstract FragmentTransaction add (int containerViewId, Fragment fragment)
				*containerViewId即为Optional identifier of the container this fragment is to be placed in. If 0, it will not be placed in a container.
				 * */
                    transaction.add(R.id.fragment_container, tab01);//将微信聊天界面的Fragment添加到Activity中
                }else {
                    transaction.show(tab01);
                }
                mImgOrder.setImageResource(R.drawable.shopping_home_tab_order_selected);
                mTextOrder.setTextColor(Color.parseColor("#6699ff"));
                break;
            case 1:
                if (tab02 == null) {
                    tab02 = new FoodMenuFragment();
                    transaction.add(R.id.fragment_container, tab02);
                }else {
                    transaction.show(tab02);
                }
                mImgFood.setImageResource(R.drawable.shopping_home_tab_take_out_selected);
                mTextFood.setTextColor(Color.parseColor("#6699ff"));
                break;
            case 2:
                if (tab03 == null) {
                    tab03 = new BarChartFragment() ;
                    transaction.add(R.id.fragment_container, tab03);
                }else {
                    transaction.show(tab03);
                }
                mImgCount.setImageResource(R.drawable.shopping_home_tab_found_selected);
                mTextCount.setTextColor(Color.parseColor("#6699ff"));
                break;
            case 3:
                if (tab04 == null) {
                    tab04 = new SettingFragment();
                    transaction.add(R.id.fragment_container, tab04);
                }else {
                    transaction.show(tab04);
                }
                mImgSetting.setImageResource(R.drawable.shopping_home_tab_personal_selected);
                mTextSetting.setTextColor(Color.parseColor("#6699ff"));
                break;

            default:
                break;
        }
        transaction.commit();//提交事务
    }

    /** 嵌套Fragment */
    public final static class FragmentParent extends Fragment {
        private TextView textView1;
        private TextView textView2;
        private int currIndex;
        private int screenW;
        private ArrayList<Fragment> fragmentListView;
        private EditText etSearch;
        /*   通过此方法创建Fragment并且向其传达参数*/
        public  final static FragmentParent newInstance(int position) {
            FragmentParent f = new FragmentParent();
            Bundle args = new Bundle(2);
            args.putInt("position", position);
            f.setArguments(args);
            return f;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            //将layout的xml布局文件实例化为View类对象
            View convertView = inflater.inflate(R.layout.order_fragments, container, false);
            ViewPager pager = (ViewPager) convertView.findViewById(R.id.pager);
            textView1 = (TextView)convertView.findViewById(R.id.tv_have_handle);
            textView2 = (TextView)convertView.findViewById(R.id.tv_not_handle);
            etSearch = (EditText) convertView.findViewById(R.id.etSearch);
            etSearch.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), OrderSelectActivity.class));
                }
            });
            DisplayMetrics metric = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
            screenW = metric.widthPixels;
            UnprocessOrderListFragment unprocessOrderListFragment = new UnprocessOrderListFragment();
            ProcessOrderListFragment processOrderListFragment = new ProcessOrderListFragment();
            //RecyclerViewFragment recyclerViewFragment = new RecyclerViewFragment();
            fragmentListView = new ArrayList<Fragment>();
            fragmentListView.add(unprocessOrderListFragment);
            fragmentListView.add(processOrderListFragment);


            final int parent_position = getArguments().getInt("position");
            //注意这里的代码
//            getChildFragmentManager()
            SectionPagerAdapter adapter=new SectionPagerAdapter(getChildFragmentManager(),fragmentListView);
            pager.setAdapter(adapter);
            pager.setCurrentItem(0);
            pager.addOnPageChangeListener(new MyOnPageChangeListener());
            return convertView;
        }

        class SectionPagerAdapter extends FragmentPagerAdapter {
            private ArrayList<Fragment> fragmentList;

            public SectionPagerAdapter(FragmentManager fm,ArrayList<Fragment> list) {
                super(fm);
                this.fragmentList = list;
            }

            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:

                        return "ListView";
                    case 1:
                    default:
                        return "ListView";
                }
            }




        }

        /**
         *
         * 改变已处理未处理textview颜色
         */
        public  void changeCursor(int index){
            if (index == 0){
                textView1.setBackgroundColor(Color.parseColor("#0088ff"));
                textView1.setTextColor(Color.parseColor("#ffffff"));
                textView2.setBackgroundColor(Color.parseColor("#ffffff"));
                textView2.setTextColor(Color.parseColor("#0088ff"));
            }else {
                textView2.setBackgroundColor(Color.parseColor("#0088ff"));
                textView2.setTextColor(Color.parseColor("#ffffff"));
                textView1.setBackgroundColor(Color.parseColor("#ffffff"));
                textView1.setTextColor(Color.parseColor("#0088ff"));

            }
        }
        class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

            private int one = screenW/2;//两个相邻页面的偏移量

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                Animation animation = new TranslateAnimation(currIndex*one,arg0*one,0,0);//平移动画
                currIndex = arg0;
                changeCursor(currIndex);
                animation.setFillAfter(true);//动画终止时停留在最后一帧，不然会回到没有执行前的状态
                animation.setDuration(200);//动画持续时间0.2秒
            /*image.startAnimation(animation);//是用ImageView来显示动画的*/
            }
        }


    }




   /* public static*/





}
