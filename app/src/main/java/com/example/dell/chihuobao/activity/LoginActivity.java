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
import com.example.dell.chihuobao.util.BaseLog;
import com.example.dell.chihuobao.util.GsonUtil;
import com.example.dell.chihuobao.util.HttpUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.HashMap;


@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
    public static final int LOGIN_SUCCESS = 1;
    public static final int LOGIN_FAILURE = 2;
    public static final String ADDRESS="http://10.6.12.70:8080/chb/user/login.do?";
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
            HashMap<String,String> data;
            switch (msg.what) {

                case LOGIN_SUCCESS:
                    data= (HashMap<String, String>) msg.getData().getSerializable("data");
                    Toast.makeText(LoginActivity.this, data.get("status"),Toast.LENGTH_SHORT).show();
                    break;
                case LOGIN_FAILURE:
                    data= (HashMap<String, String>) msg.getData().getSerializable("data");
                    Toast.makeText(LoginActivity.this, data.get("status"),Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Event(R.id.login)
    private void onLoginClick(View view) {
        BaseLog.i("登录点击");

        String s= "";
        try {

            s = HttpUtil.getURLResponse("http://www.baidu.com");
        } catch (Exception e) {
            e.printStackTrace();

            if (etUserName.getText().toString().trim().equals("") || erUserPwd.getText().toString().trim().equals("")) {
                Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
                etUserName.setText(etUserName.getText().toString().trim());
                erUserPwd.setText("");
                return;

            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String username = etUserName.getText().toString().trim();
                        String password = erUserPwd.getText().toString().trim();
                        String loginData = HttpUtil.getURLResponse(ADDRESS +
                                "username=" + username + "&password=" + password);
                        HashMap<String, String> map = (HashMap<String, String>) GsonUtil.parseData(loginData);
                        if (map.get("status").equals("success")) {
                            Message msg = new Message();
                            msg.what = LOGIN_SUCCESS;
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("data", map);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        } else if (map.get("status").equals("fail")) {
                            Message msg = new Message();
                            msg.what = LOGIN_SUCCESS;
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("data", map);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        } else {
                            BaseLog.e("返回数据出错！！！");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }

    @Event(R.id.to_login_by_phone)
    private void onLoginByPhoneClick(View view) {
        BaseLog.i("登录点击");

        Intent intent = new Intent(LoginActivity.this,
                PhoneVerifyActivity.class);
        startActivity(intent);

    }
}
