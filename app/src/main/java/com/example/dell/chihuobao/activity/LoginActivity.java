package com.example.dell.chihuobao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.chihuobao.R;
import com.example.dell.chihuobao.bean.FoodCategory;
import com.example.dell.chihuobao.bean.User;
import com.example.dell.chihuobao.util.BaseLog;
import com.example.dell.chihuobao.util.MyApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;


@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_FAILURE = 2;

    public static final String ADDRESS = "http://10.6.12.70:8080/chb/fragment_user/login.do?";
    public final static String QUERY_CATEGORY = "chb/shop/queryCategory.do";
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.login_user_name)
    private EditText etUserName;

    @ViewInject(R.id.login_user_pwd)
    private EditText erUserPwd;

    @ViewInject(R.id.login)
    private Button bLogin;

    @ViewInject(R.id.to_login_by_phone)
    private TextView TVLoginByPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("登录");
        setSupportActionBar(toolbar);


    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            HashMap<String, String> data;
            switch (msg.what) {

                case LOGIN_SUCCESS:
                    RequestParams params = new RequestParams(MyApplication.localhost+QUERY_CATEGORY);
                    params.addBodyParameter("shopid",MyApplication.getShopId());
                    x.http().get(params, new org.xutils.common.Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Gson gson = new Gson();
                            ArrayList<FoodCategory> foodCategoryArrayList;
                            foodCategoryArrayList = gson.fromJson(result, new TypeToken<FoodCategory>() {
                            }.getType());
                            MyApplication.setFoodCategoryArrayList(foodCategoryArrayList);
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
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

                    break;
                case LOGIN_FAILURE:

                    Toast.makeText(LoginActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Event(R.id.login)
    private void onLoginClick(View view) {
        BaseLog.i("登录点击");
        BaseLog.i("登录点击");
        String username = etUserName.getText().toString().trim();
        String password = erUserPwd.getText().toString().trim();
        RequestParams params = new RequestParams("http://192.168.155.2:8080/login.txt");
        params.addQueryStringParameter("username", username);
        params.addQueryStringParameter("password", password);
        /*if (etUserName.getText().toString().trim().equals("") || erUserPwd.getText().toString().trim().equals("")) {
            Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            etUserName.setText(etUserName.getText().toString().trim());
            erUserPwd.setText("");
            return;
        }*/
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                BaseLog.i(result);
                Gson gson = new Gson();

                User user = null;
                user = gson.fromJson(result, new TypeToken<User>() {
                }.getType());
                if (user.getStatus().equals("success")) {
                    Message msg = new Message();
                    msg.what = LOGIN_SUCCESS;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", user.getInfo());
                    msg.setData(bundle);
                    MyApplication.getInstance().setUser(user);

                    handler.sendMessage(msg);
                } else if (user.getStatus().equals("fail")) {

                } else {
                    BaseLog.e("返回数据出错！！！");
                }

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


    }

    @Event(R.id.to_login_by_phone)
    private void onLoginByPhoneClick(View view) {
        Intent intent = new Intent(LoginActivity.this,
                PhoneVerifyActivity.class);
        startActivity(intent);

    }
}
