package com.example.dell.chihuobao.activity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dell.chihuobao.R;
import com.example.dell.chihuobao.bean.User;
import com.example.dell.chihuobao.util.MyApplication;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Calendar;
import java.util.HashMap;

@ContentView(R.layout.activity_user_update)
public class UserUpdateActivity extends BaseActivity {
    public final static String UPDATE_USER = "chb/shop/updateShop.do?";
    public final static String URL = MyApplication.getLocalhost()+"/";
    private Context context=UserUpdateActivity.this;
    private User user = MyApplication.getInstance().getUser();
    private HashMap map = user.getUser();


    @ViewInject(R.id.phone)
    private TextView phone;

    @ViewInject(R.id.minconsume)
    private TextView minconsume;

    @ViewInject(R.id.sendexpense)
    private TextView sendexpense;

    @ViewInject(R.id.email)
    private TextView email;

    @ViewInject(R.id.name)
    private TextView name;

    @ViewInject(R.id.businessstarttime)
    private TextView businessstarttime;

    @ViewInject(R.id.businessendtime)
    private TextView businessendtime;

    @ViewInject(R.id.serving)
    private Switch serving;

    @ViewInject(R.id.layout_phone)
    private LinearLayout layout_phone;
    @ViewInject(R.id.layout_minconsume)
    private LinearLayout layout_minconsume;
    @ViewInject(R.id.layout_sendexpense)
    private LinearLayout layout_sendexpense;
    @ViewInject(R.id.layout_email)
    private LinearLayout layout_email;
    @ViewInject(R.id.layout_name)
    private LinearLayout layout_name;
    @ViewInject(R.id.layout_businessstarttime)
    private LinearLayout layout_businessstarttime;
    @ViewInject(R.id.layout_businessendtime)
    private LinearLayout layout_businessendtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phone.setText(map.get("phone").toString());
        minconsume.setText((int)Double.parseDouble(map.get("minconsume").toString())+"");
        sendexpense.setText((int)Double.parseDouble(map.get("sendexpense").toString())+"");
        email.setText(map.get("email").toString());
        name.setText(map.get("name").toString());
        businessstarttime.setText(map.get("businessstarttime").toString());
        businessendtime.setText(map.get("businessendtime").toString());
        if ((int) Double.parseDouble(map.get("isServing").toString()) == 1) {
            serving.setChecked(true);
        }else {
            serving.setChecked(false);
        }

    }

    @Event(R.id.layout_photo)
    private void OnPhotoClick(View view) {
        startActivity(new Intent(context,UserModifyActivity.class));
    }
    @Event(R.id.layout_phone)
    private void OnPhoneClick(View view) {
        final EditText editText = new EditText(context);
        editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        editText.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        editText.setText(map.get("phone").toString());
        new AlertDialog.Builder(context).setTitle("修改电话").setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = editText.getText().toString().trim();
                        phone.setText(text);
                        map.put("phone", text);
                        updateUser(map);
                    }
                })
                .setNegativeButton("取消", null)
                .show();

    }
    @Event(R.id.layout_minconsume)
    private void OnMinconsumeClick(View view) {
        final EditText editText = new EditText(context);
        editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        editText.setText(map.get("minconsume").toString());
        new AlertDialog.Builder(context).setTitle("修改最低消费").setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text=editText.getText().toString().trim();
                        minconsume.setText(text);
                        map.put("minconsume", text);
                        updateUser(map);
                    }
                })
                .setNegativeButton("取消", null)
                .show();

    }

    @Event(R.id.layout_sendexpense)
    private void OnSendexpenseClick(View view) {
        final EditText editText = new EditText(context);
        editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        editText.setText(map.get("sendexpense").toString());
        new AlertDialog.Builder(context).setTitle("修改送餐费").setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text=editText.getText().toString().trim();
                        sendexpense.setText(text);
                        map.put("sendexpense", text);
                        updateUser(map);
                    }
                })
                .setNegativeButton("取消", null)
                .show();

    }
    @Event(R.id.layout_email)
    private void OnEmailClick(View view) {
        final EditText editText = new EditText(context);

        editText.setText(map.get("email").toString());
        new AlertDialog.Builder(context).setTitle("修改email").setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text=editText.getText().toString().trim();
                        email.setText(text);
                        map.put("email", text);
                        updateUser(map);
                    }
                })
                .setNegativeButton("取消", null)
                .show();

    }
    @Event(R.id.layout_name)
    private void OnNameClick(View view) {
        final EditText editText = new EditText(context);
        editText.setText(map.get("name").toString());
        new AlertDialog.Builder(context).setTitle("修改店铺名").setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text=editText.getText().toString().trim();
                        name.setText(text);
                        map.put("name", text);
                        updateUser(map);
                    }
                })
                .setNegativeButton("取消", null)
                .show();

    }
    @Event(R.id.layout_businessstarttime)
    private void OnBusinessstarttimeClick(View view) {
        final EditText editText = new EditText(context);

        Calendar c;
        c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);
        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String text=new StringBuilder()
                        .append(hourOfDay < 10 ? "0" + hourOfDay : hourOfDay).append(":")
                        .append(minute < 10 ? "0" + minute : minute).toString();
                businessstarttime.setText(text);
                map.put("businessstarttime", text);
                updateUser(map);
            }
        },hour,minute,true).show();

    }
    @Event(R.id.layout_businessendtime)
    private void OnBusinessendtimeClick(View view) {
        final EditText editText = new EditText(context);

        Calendar c;
        c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);
        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String text=new StringBuilder()
                        .append(hourOfDay < 10 ? "0" + hourOfDay : hourOfDay).append(":")
                        .append(minute < 10 ? "0" + minute : minute).toString();
                businessendtime.setText(text);
                map.put("businessendtime", text);
                updateUser(map);
            }
        },hour,minute,true).show();

    }
    @Event(R.id.serving)
    private void OnServingClick(View view) {
        if (serving.isChecked()) {
            map.put("isServing", 1);
            updateUser(map);
        }else{
            map.put("isServing", 2);
            updateUser(map);
        }
    }
    public void updateUser(final HashMap hashMap){
        RequestParams params = new RequestParams(URL+UPDATE_USER);
        params.addBodyParameter("id",(int)(Double.parseDouble(hashMap.get("id").toString())),null);
        params.addBodyParameter("username",hashMap.get("username").toString(),null);
        /*params.addBodyParameter("password", hashMap.get("password").toString(), null);*/
        params.addBodyParameter("phone", hashMap.get("phone"), null);
        params.addBodyParameter("address", hashMap.get("address"), null);

      /*  params.addBodyParameter("registertime", hashMap.get("registertime"), null);*/
        params.addBodyParameter("updatetime", hashMap.get("updatetime").toString(), null);
        /*params.addBodyParameter("shoptype", (int)(Double.parseDouble(hashMap.get("shoptype").toString())), null);*/
        params.addBodyParameter("minconsume", Double.parseDouble(hashMap.get("minconsume").toString()), null);
        params.addBodyParameter("sendexpense", Double.parseDouble(hashMap.get("sendexpense").toString()), null);
        params.addBodyParameter("email", hashMap.get("email").toString(), null);
       /* params.addBodyParameter("qrcode", hashMap.get("qrcode"), null);
        params.addBodyParameter("shopmessage", hashMap.get("shopmessage"), null);
        params.addBodyParameter("telephone", hashMap.get("telephone"), null);
        params.addBodyParameter("identify", hashMap.get("identify"), null);
        params.addBodyParameter("license", hashMap.get("license"), null);*/
        params.addBodyParameter("name", hashMap.get("name"), null);
        params.addBodyParameter("businessstarttime", hashMap.get("businessstarttime").toString(), null);
        params.addBodyParameter("businessendtime", hashMap.get("businessendtime").toString(), null);
        params.addBodyParameter("isServing", (int)(Double.parseDouble(hashMap.get("isServing").toString())), null);
       /* params.addBodyParameter("axisX", hashMap.get("axisX"), null);
        params.addBodyParameter("axisY", hashMap.get("axisY"), null);*/
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
                Toast.makeText(x.app(), "更新成功" , Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), "更新失败", Toast.LENGTH_SHORT).show();
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
