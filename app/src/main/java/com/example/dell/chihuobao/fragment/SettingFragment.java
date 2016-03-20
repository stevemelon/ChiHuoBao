package com.example.dell.chihuobao.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dell.chihuobao.R;
import com.example.dell.chihuobao.activity.FeedBackActivity;
import com.example.dell.chihuobao.activity.LocationActivity;
import com.example.dell.chihuobao.activity.LoginActivity;
import com.example.dell.chihuobao.util.MyApplication;


public class SettingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.setting, container, false);
        TextView help = (TextView) view.findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
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
        TextView settings_logout= (TextView) view.findViewById(R.id.settings_logout);
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
      /*  SettingsItemView settings_connect_manager= (SettingsItemView) view.findViewById(R.id.settings_connect_manager);
        settings_connect_manager.setOnSettingClickListener(new SettingsItemView.settingClickListener() {
            @Override
            public void rightClick() {
                Toast.makeText(getActivity(), "a", Toast.LENGTH_SHORT).show();
            }
        });*/
        return view;

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
}