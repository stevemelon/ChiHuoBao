package com.example.dell.chihuobao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dell.chihuobao.R;
import com.example.dell.chihuobao.bean.User;
import com.example.dell.chihuobao.util.MyApplication;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;

public class ShopMessageModifyctivity extends AppCompatActivity {
    private User user;
    private HashMap hashMap ;
    private EditText messsage;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_message_modifyctivity);
        user = MyApplication.getInstance().getUser();
        hashMap=user.getUser();
        messsage = (EditText) findViewById(R.id.messsage);
        submit = (Button) findViewById(R.id.submit);
        if (hashMap.get("shopmessage") != null) {
            messsage.setText(hashMap.get("shopmessage").toString().trim());
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messsage.getText().toString().trim() != null) {
                    hashMap.put("shopmessage", messsage.getText().toString().trim());
                    updateUser(hashMap);
                }
            }
        });
    }

    public void updateUser(final HashMap hashMap){
        RequestParams params = new RequestParams(UserModifyActivity.URL+UserModifyActivity.UPDATE_USER);
        params.addBodyParameter("id",(int)(Double.parseDouble(hashMap.get("id").toString())),null);
        params.addBodyParameter("username",hashMap.get("username").toString(),null);
        params.addBodyParameter("password", hashMap.get("password").toString(), null);
        params.addBodyParameter("phone", hashMap.get("phone"), null);
        params.addBodyParameter("address", hashMap.get("address"), null);
       /* params.addBodyParameter("shopphoto", hashMap.get("shopphoto"), null);
        final String name=getPhotoFileName();
        params.addBodyParameter("shopphotodetail",name, null);*/
        params.addBodyParameter("registertime", hashMap.get("registertime"), null);
        params.addBodyParameter("updatetime", hashMap.get("updatetime").toString(), null);
        params.addBodyParameter("shoptype", (int)(Double.parseDouble(hashMap.get("shoptype").toString())), null);
        params.addBodyParameter("minconsume", Double.parseDouble(hashMap.get("minconsume").toString()), null);
        params.addBodyParameter("sendexpense", Double.parseDouble(hashMap.get("sendexpense").toString()), null);
        params.addBodyParameter("email", hashMap.get("email").toString(), null);
        params.addBodyParameter("qrcode", hashMap.get("qrcode"), null);
        params.addBodyParameter("shopmessage", hashMap.get("shopmessage"), null);
        params.addBodyParameter("telephone", hashMap.get("telephone"), null);
        params.addBodyParameter("identify", hashMap.get("identify"), null);
        params.addBodyParameter("license", hashMap.get("license"), null);
        params.addBodyParameter("name", hashMap.get("name"), null);
        params.addBodyParameter("businessstarttime", hashMap.get("businessstarttime").toString(), null);
        params.addBodyParameter("businessendtime", hashMap.get("businessendtime").toString(), null);
        params.addBodyParameter("isServing", (int)(Double.parseDouble(hashMap.get("isServing").toString())), null);
        params.addBodyParameter("axisX", hashMap.get("axisX"), null);
        params.addBodyParameter("axisY", hashMap.get("axisY"), null);
        if(hashMap.get("rank")!=null) {
            params.addBodyParameter("rank", (int) (Double.parseDouble(hashMap.get("rank").toString())), null);
        }else{
            params.addBodyParameter("rank", "0", null);
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("result", result);

                user.setUser(hashMap);
                MyApplication.getInstance().setUser(user);
                Toast.makeText(x.app(), "更新成功，马上去服务器看看吧！" + result, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ShopMessageModifyctivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), "更新失败，检查一下服务器地址是否正确", Toast.LENGTH_SHORT).show();
                ex.printStackTrace();

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

}
