package com.example.vkexample;

import com.example.vkexample.model.vk.vkdb.VKDatabaseHelper;
import com.example.vkexample.vk.VkSession;
import com.example.vkexample.vk.api.helper.VKApiHelper;

import android.app.Application;

public class VkExampleApplication extends Application {

	private VkSession mVKSessionInstance;
	private VKApiHelper mVKApiHelper;

	public VkSession getVkSession() {
		if (mVKSessionInstance == null)
			mVKSessionInstance = new VkSession(getApplicationContext());
		return mVKSessionInstance;
	}

	public VKApiHelper getVkApiHelper() {
		if (mVKApiHelper == null)
			mVKApiHelper = new VKApiHelper(getApplicationContext());
		return mVKApiHelper;
	}

	public void onCreate() {
		VKDatabaseHelper.createDBHelper(getApplicationContext());
	};

	@Override
	public void onTerminate() {
		super.onTerminate();
		VKDatabaseHelper.destroyDatabaseHelper();
	}
}
