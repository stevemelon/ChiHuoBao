package com.example.dell.chihuobao.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dell.chihuobao.R;

public class UserFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		//引入我们的布局
		return inflater.inflate(R.layout.user, container, false);
	}

}
