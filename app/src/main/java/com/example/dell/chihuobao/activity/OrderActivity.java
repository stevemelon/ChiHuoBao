package com.example.dell.chihuobao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dell.chihuobao.R;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class OrderActivity extends Activity {
    public static String url="http://10.6.12.35:8080/zhbj/common.json";
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mButton= (Button) findViewById(R.id.btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams("http://10.6.12.35:8080/zhbj/common.json");
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        String a=result.toString();
                        Gson gson=new Gson();
//                        java.lang.reflect.Type type = new TypeToken<TestBean>() {}.getType();
//                        TestBean testBean=gson.fromJson(a,type);
//                        Log.i("!!!!!!!!!",""+testBean.getRetcode());
//                        Log.i("11111111111111",""+testBean.getData().getAsd());

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                       Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFinished() {

                    }
                });

            }
        });

    }

}
