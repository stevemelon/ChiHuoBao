package com.example.dell.chihuobao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.chihuobao.R;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ViewPager viewpager;
    private View view1,view2,view3;//三个界面
    private List<View> view=new ArrayList<>();//保存各种界面的链表
    private LayoutInflater inflater;
    private ImageView[] points;//保存所有点图片的链表
    private int[] dots={R.id.point1,R.id.point2,R.id.point3};//保存各个点的id
    private TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_main);
        initial();
        login= (TextView) findViewById(R.id.tv_to_login);
        viewpager= (ViewPager) findViewById(R.id.viewpager);
        ViewAdpter viewAdpter=new ViewAdpter(view);
        viewpager.setAdapter(viewAdpter);
        viewpager.setOnPageChangeListener(this);
        initialDots();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });



//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("登录");
//        setSupportActionBar(toolbar);


    }
    private void initial(){
        viewpager= (ViewPager) findViewById(R.id.viewpager);
        inflater= LayoutInflater.from(this);
        view1=inflater.inflate(R.layout.welcome_one, null);
        view2=inflater.inflate(R.layout.welcome_two,null);
        view3=inflater.inflate(R.layout.welcome_three, null);
        view.add(view1);
        view.add(view2);
        view.add(view3);
    }
    private void initialDots(){
        points= new ImageView[3];
        for (int i=0;i<3;i++) {
            points[i] = (ImageView) findViewById(dots[i]);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        for (int i=0;i<3;i++){
            if (i==position){
                points[i].setImageResource(R.drawable.point_select);
            }else
                points[i].setImageResource(R.drawable.point_normal);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class ViewAdpter extends PagerAdapter{
        private List<View> mViews;
        public  ViewAdpter(List<View> view){
            this.mViews=view;
        }


        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViews.get(position));
            return mViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViews.get(position));

        }
    }

}
