package com.example.vkexample.activity;

import com.example.vkexample.VkExampleApplication;
import com.example.vkexample.receiver.ServiceResultReceiver;
import com.example.vkexample.vk.VkSession;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.app.Activity;

public class BaseActivity extends Activity implements OnClickListener,
		ServiceResultReceiver.Receiver {
	protected VkSession mSession;
	private Toast mToast;
	protected ServiceResultReceiver mReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentLayout();

		initSession();

		initView();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mReceiver.setReceiver(null);
	}

	protected void initView() {
	}

	protected void setContentLayout() {
	}

	protected void initSession() {
		mSession = ((VkExampleApplication) getApplication())
				.getVkSession();
	}

	protected void showMessage(int resId) {
		if (mToast == null)
			mToast = Toast.makeText(this, resId, Toast.LENGTH_LONG);
		else
			mToast.setText(resId);
		mToast.show();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mReceiver = new ServiceResultReceiver(new Handler());
		mReceiver.setReceiver(this);
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle data) {
		switch (resultCode) {
		}
	}

}
