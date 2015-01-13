package com.example.homework;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.example.homework.base.BaseFragment;
import com.example.homework.base.BaseModel;

public class FragmentMain extends BaseFragment {

	Context mContext;

	public FragmentMain() {
	}

	public static FragmentMain newInstance() {
		return new FragmentMain();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);

		v.findViewById(R.id.btnLoginLink).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						((MainActivity) getActivity()).launchLogin();

					}
				});
		v.findViewById(R.id.btnRegisterLink).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						((MainActivity) getActivity()).launchRegister();

					}
				});

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		SCREEN_TITLE = "Homework";
		mContext = getActivity();
		return inflater.inflate(R.layout.fragment_main, parent, false);
	}

	@Override
	public void onResponse(BaseModel model) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initUI(View view) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onAfterStart() {
		// TODO Auto-generated method stub
		
	}

}
