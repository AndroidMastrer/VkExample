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

public class MainActivity extends BaseActivity {
	private static final int VK_AUTH_REQUEST_CODE = 11;
	private EditText mLoginName;
	private EditText mLoginPassword;

	@Override
	protected void setContentLayout() {
		setContentView(R.layout.activity_main_layout);
	}

	@Override
	protected void initView() {
		Button loginBtn = (Button) findViewById(R.id.login_btn);
		loginBtn.setOnClickListener(this);

		mLoginName = (EditText) findViewById(R.id.login_name);
		mLoginPassword = (EditText) findViewById(R.id.login_password);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VK_AUTH_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				String token = data
						.getStringExtra(AuthActivity.OUT_EXTRA_TOKEN);
				String userId = data
						.getStringExtra(AuthActivity.OUT_EXTRA_USER_ID);
				if (token != null && userId != null)
					mSession.initSession(token, userId);
			}
		}
	}

	@Override
	protected void onResume() {
		if (checkSession()) {
			;
		}
	}

	public boolean checkSession() {
		mSession = ((VkExampleApplication) getApplication()).getVkSession();
		if (!mSession.isActive())
			startActivityForResult(new Intent(this, AuthActivity.class),
					VK_AUTH_REQUEST_CODE);
		return mSession.isActive();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
