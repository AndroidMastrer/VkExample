package com.example.vkexample.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.vkexample.R;
import com.example.vkexample.VkExampleApplication;
import com.example.vkexample.model.vk.Post;
import com.example.vkexample.vk.api.WallGet;
import com.example.vkexample.vk.api.helper.VKRestApiHelper;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends BaseActivity {
	private static final int VK_AUTH_REQUEST_CODE = 11;
	private List<Post> mPosts = new ArrayList<Post>();

	@Override
	protected void setContentLayout() {
		setContentView(R.layout.activity_main_layout);
	}

	@Override
	protected void initView() {
		Button refreshBtn = (Button) findViewById(R.id.refresh_wallmessages);
		refreshBtn.setOnClickListener(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VK_AUTH_REQUEST_CODE) {
			if (resultCode == Activity.RESULT_OK) {
				String token = data
						.getStringExtra(AuthActivity.OUT_EXTRA_TOKEN);
				Long userId = data.getLongExtra(AuthActivity.OUT_EXTRA_USER_ID,
						0);
				if (token != null && userId != 0)
					mSession.initSession(token, userId);
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
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
		switch (v.getId()) {
		case R.id.refresh_wallmessages:
			((VkExampleApplication) getApplication()).getVkApiHelper().wallGet(
					mReceiver, 0, 20, mSession.getAccessToken());
			break;
		default:
			break;
		}
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle data) {
		if (data.getString(VKRestApiHelper.EXTRA_RESULT_ACTION).equals(
				WallGet.class.getSimpleName())) {
			mPosts = data.getParcelableArrayList(WallGet.EXTRA_WALL_POSTS);
		}
	}

}
