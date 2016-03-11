package com.example.dell.chihuobao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.chihuobao.R;
import com.example.dell.chihuobao.activity.FeedBackActivity;


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
      /*  SettingsItemView settings_connect_manager= (SettingsItemView) view.findViewById(R.id.settings_connect_manager);
        settings_connect_manager.setOnSettingClickListener(new SettingsItemView.settingClickListener() {
            @Override
            public void rightClick() {
                Toast.makeText(getActivity(), "a", Toast.LENGTH_SHORT).show();
            }
        });*/
		return view;

	}

}
