package com.example.dell.chihuobao.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dell.chihuobao.R;
import com.example.dell.chihuobao.activity.FeedBackActivity;
import com.example.dell.chihuobao.activity.HelpActivity;
import com.example.dell.chihuobao.activity.LocationActivity;
import com.example.dell.chihuobao.activity.LoginActivity;
import com.example.dell.chihuobao.activity.PhoneVerifyActivity;
import com.example.dell.chihuobao.activity.ShopMessageModifyctivity;
import com.example.dell.chihuobao.activity.UserModifyActivity;
import com.example.dell.chihuobao.activity.UserUpdateActivity;
import com.example.dell.chihuobao.util.MyApplication;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class SettingFragment extends Fragment {
    Bitmap bitmap;
    ImageView restaurant_icon;
    TextView tv_restaurant_name;
    TextView tv_owner_phone;
    TextView tv_owner_cardnumber;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting, container, false);
        restaurant_icon = (ImageView) view.findViewById(R.id.restaurant_icon);
        tv_restaurant_name = (TextView) view.findViewById(R.id.tv_restaurant_name);
        tv_owner_phone = (TextView) view.findViewById(R.id.tv_owner_phone);
        tv_owner_cardnumber=(TextView) view.findViewById(R.id.tv_owner_cardnumber);
        if (MyApplication.getUser().getUser().get("name") != null) {
            tv_restaurant_name.setText(MyApplication.getUser().getUser().get("name").toString());
        }
        if (MyApplication.getUser().getUser().get("phone") != null) {
            tv_owner_phone.setText(MyApplication.getUser().getUser().get("phone").toString());
        }
        if (MyApplication.getUser().getUser().get("identify") != null) {
            tv_owner_cardnumber.setText(getDisplayStr(MyApplication.getUser().getUser().get("identify").toString()));
        }
        RelativeLayout about_us_layout = (RelativeLayout) view.findViewById(R.id.about_us_layout);
        about_us_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), FeedBackActivity.class);
                startActivity(intent);
            }
        });
        RelativeLayout locationUpdate = (RelativeLayout) view.findViewById(R.id.location);
        locationUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), LocationActivity.class);
                startActivity(intent);
            }
        });
        RelativeLayout app_recommend_layout= (RelativeLayout) view.findViewById(R.id.app_recommend_layout);
        app_recommend_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), HelpActivity.class);
                startActivity(intent);
            }
        });
        RelativeLayout app_password_layout = (RelativeLayout) view.findViewById(R.id.app_password_layout);
        app_password_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), PhoneVerifyActivity.class);
                startActivity(intent);
            }
        });
        TextView settings_logout = (TextView) view.findViewById(R.id.settings_logout);
        settings_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("确认要退出登录吗？")
                        .setMessage("")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clearUser();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("否", null)
                        .show();
            }
        });
        LinearLayout restaurant_info_container = (LinearLayout) view.findViewById(R.id.restaurant_info_container);
        restaurant_info_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startActivity(new Intent(getActivity(), UserModifyActivity.class));*/
                startActivity(new Intent(getActivity(), UserUpdateActivity.class));
            }
        });

        RelativeLayout feedback_layout = (RelativeLayout) view.findViewById(R.id.feedback_layout);
        feedback_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ShopMessageModifyctivity.class));
            }
        });
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                downloadBitmap(UserModifyActivity.URL + MyApplication.getUser().getUser().get("shopphoto").toString().replaceAll("\\\\", "/"));

            }
        }

        );
        thread.start();

           /*  SettingsItemView settings_connect_manager= (SettingsItemView) view.findViewById(R.id.settings_connect_manager);
        settings_connect_manager.setOnSettingClickListener(new SettingsItemView.settingClickListener() {
            @Override
            public void rightClick() {
                Toast.makeText(getActivity(), "a", Toast.LENGTH_SHORT).show();
            }
        });*/
        return view;

    }

    @Override
    public void onStart() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                downloadBitmap(UserModifyActivity.URL + MyApplication.getUser().getUser().get("shopphoto").toString().replaceAll("\\\\", "/"));

            }
        }

        );
        thread.start();
        if (MyApplication.getUser().getUser().get("name") != null) {
            tv_restaurant_name.setText(MyApplication.getUser().getUser().get("name").toString());
        }
        if (MyApplication.getUser().getUser().get("phone") != null) {
            tv_owner_phone.setText(MyApplication.getUser().getUser().get("phone").toString());
        }
        super.onStart();
    }

    private void clearUser() {
        MyApplication.getInstance().setUser(null);
        SharedPreferences settings = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("token", "");
        editor.putString("username", "");
        editor.putString("password", "");
        editor.commit();
    }

    private void downloadBitmap(String url) {

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();

            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStream = conn.getInputStream();

                //图片压缩处理
                //BitmapFactory.Options option = new BitmapFactory.Options();
                //option.inSampleSize = 2;//宽高都压缩为原来的二分之一, 此参数需要根据图片要展示的大小来确定
                //option.inPreferredConfig = Bitmap.Config.RGB_565;//设置图片格式
                bitmap = BitmapFactory.decodeStream(inputStream, null, null);
                handler.sendEmptyMessage(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();

        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            restaurant_icon.setImageBitmap(bitmap);

        }
    };

    /**
     * 获取用于显示的带星花的字符，不提交后台
     * @param realStr
     * @return
     */
    private String getDisplayStr(String realStr) {
        String result = new String(realStr);
        char[] cs = result.toCharArray();
        for(int i = 0;i < cs.length;i++){
            if(i >= 3&& i <= 10){//把3和10区间的字符隐藏
                cs[i] = '*';
            }
        }
        return new String(cs);
    }
}