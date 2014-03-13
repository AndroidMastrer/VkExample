package com.example.vkexample.activity;

import com.example.vkexample.R;
import com.example.vkexample.VkExampleApplication;
import com.example.vkexample.vk.VkSession;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

public class BaseActivity extends Activity implements OnClickListener {
	protected VkSession mSession;
	private Toast mToast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentLayout();

		initSession();

		initView();
	}

	protected void initView() {
	}

	protected void setContentLayout() {
	}

	protected void initSession() {
		mSession = ((VkExampleApplication) getApplicationContext())
				.getVkSession();
	}

	private void showMessage(int resId) {
		if (mToast == null)
			mToast = Toast.makeText(this, resId, Toast.LENGTH_LONG);
		else
			mToast.setText(resId);
		mToast.show();
	}

	@Override
	public void onClick(View v) {
	}

}
